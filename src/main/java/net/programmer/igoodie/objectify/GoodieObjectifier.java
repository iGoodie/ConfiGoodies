package net.programmer.igoodie.objectify;

import net.programmer.igoodie.annotation.Goodie;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;

import java.lang.reflect.Field;

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
                    System.out.println("No goodie for " + field.getName());
                    if (field.getType().isPrimitive() || field.getType() == String.class) {
                        GoodiePrimitive defaultValue = GoodiePrimitive.from(getDefaultValue(field.getType()));
                        fillField(field, configObject, defaultValue);
                        goodieObject.put(field.getName(), defaultValue);
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

                } else if (correspondingGoodie instanceof GoodieObject) {
                    Object fieldValue = field.get(configObject);
                    if (fieldValue == null) field.set(configObject, field.getType().newInstance());
                    modified |= fillConfig((GoodieObject) correspondingGoodie, field.get(configObject));
                }
            }
        }

        return modified;
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

}
