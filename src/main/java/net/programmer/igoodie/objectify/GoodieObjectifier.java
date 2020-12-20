package net.programmer.igoodie.objectify;

import net.programmer.igoodie.annotation.Goodie;
import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GoodieObjectifier {

    public <T> boolean fillConfig(GoodieObject goodieObject, T configObject) throws IllegalAccessException, InstantiationException {
        boolean modified = false;

        Class<?> clazz = configObject.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            Goodie[] annotations = field.getAnnotationsByType(Goodie.class);
            if (annotations.length != 0) {
                field.setAccessible(true);
                GoodieElement correspondingGoodie = goodieObject.get(field.getName());

                if (correspondingGoodie == null) {
                    if (field.getType().isPrimitive() || field.getType() == String.class) {
                        GoodiePrimitive defaultValue = GoodiePrimitive.from(getDefaultValue(field.getType()));
                        fillField(field, configObject, defaultValue);
                        goodieObject.put(field.getName(), defaultValue);
                        modified = true;

                    } else if (List.class.isAssignableFrom(field.getType())) {
                        GoodieArray defaultArray = (GoodieArray) goodieObject.getOrDefault(field.getName(), new GoodieArray());
                        Object fieldValue = field.get(configObject) == null ? new LinkedList<>() : field.get(configObject);
                        fillField(field, configObject, defaultArray);
                        goodieObject.put(field.getName(), defaultArray);
                        modified = true;

                    } else {
                        GoodieObject defaultObject = (GoodieObject) goodieObject.getOrDefault(field.getName(), new GoodieObject());
                        Object fieldValue = field.get(configObject) == null ? field.getType().newInstance() : field.get(configObject);
                        fillConfig(defaultObject, fieldValue);
                        goodieObject.put(field.getName(), defaultObject);
                        modified = true;
                    }

                } else if (correspondingGoodie instanceof GoodiePrimitive) {
                    modified |= fillField(field, configObject, ((GoodiePrimitive) correspondingGoodie));

                } else if (correspondingGoodie instanceof GoodieArray) {
                    modified |= fillField(field, configObject, (GoodieArray) correspondingGoodie);

                } else if (correspondingGoodie instanceof GoodieObject) {
                    Object fieldValue = field.get(configObject);
                    if (fieldValue == null) field.set(configObject, field.getType().newInstance());
                    modified |= fillConfig((GoodieObject) correspondingGoodie, field.get(configObject));
                }
            }
        }

        return modified;
    }

    private boolean fillField(Field field, Object object, GoodieArray goodieArray) throws IllegalAccessException {
        try {
            field.setAccessible(true);

            boolean modified = false;

            Class<?> listType = Class.forName(field.getGenericType().getTypeName().split("<", 2)[1].replace(">", ""));
            LinkedList<Object> linkedList = new LinkedList<>();

            for (int i = goodieArray.size() - 1; i >= 0; i--) {
                GoodieElement goodieElement = goodieArray.get(i);
                if (goodieElement instanceof GoodiePrimitive) {
                    Object value = ((GoodiePrimitive) goodieElement).getValue();
                    if (listType.isInstance(value))
                        linkedList.add(value);
                    else {
                        goodieArray.remove(i);
                        modified = true;
                    }

                } else if (goodieElement instanceof GoodieObject) {
                    Object newValue = listType.newInstance();
                    modified |= fillConfig((GoodieObject) goodieElement, newValue);
                    linkedList.add(newValue);
                }
            }

            Collections.reverse(linkedList);

            field.set(object, linkedList);
            return modified;

        } catch (IllegalArgumentException | ClassNotFoundException | InstantiationException e) {
            System.out.println("Invalid type for " + field.getName());
            field.set(getDefaultValue(field.getType()), object); // XXX
            return true;
        }
    }

    private boolean fillField(Field field, Object object, GoodiePrimitive goodie) throws IllegalAccessException {
        try {
            field.setAccessible(true);

            if (!Number.class.equals(goodie.getType())) {
                field.set(object, goodie.getRawValue());
            } else {
                Number number = goodie.getNumberValue();

                if (field.getType() == int.class) {
                    field.set(object, number.intValue());
                } else if (field.getType() == long.class) {
                    field.set(object, number.longValue());
                } else if (field.getType() == float.class) {
                    field.set(object, number.floatValue());
                } else if (field.getType() == double.class) {
                    field.set(object, number.doubleValue());
                } else if (field.getType() == short.class) {
                    field.set(object, number.shortValue());
                } else if (field.getType() == byte.class) {
                    field.set(object, number.byteValue());
                }
            }

            return false;

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid type for " + field.getName());
            field.set(getDefaultValue(field.getType()), object);
            return true;
        }
    }

    private <T> Object getDefaultValue(Class<T> type) {
        if (type == int.class)
            return 0;
        if (type == long.class)
            return 0L;
        if (type == float.class)
            return 0F;
        if (type == double.class)
            return 0D;
        if (type == short.class)
            return 0;
        if (type == byte.class)
            return 0;
        if (type == boolean.class)
            return false;
        if (type == char.class)
            return 0;
        if (type == String.class)
            return "";
        return null;
    }

    private void defaultConstructorCheck() {
        // TODO: Implement and call when .newInstance calls are made
    }

}
