package net.programmer.igoodie.objectify;

import net.programmer.igoodie.annotation.Goodie;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;

import java.lang.reflect.Field;

public class GoodieObjectifier {

    public <T> T fillConfig(GoodieObject goodieObject, T configObject) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = configObject.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            Goodie[] annotations = field.getAnnotationsByType(Goodie.class);
            if (annotations.length != 0) {
                field.setAccessible(true);
                GoodieElement correspondingGoodie = goodieObject.get(field.getName());

                if (correspondingGoodie instanceof GoodiePrimitive) {
                    fillField(field, configObject, ((GoodiePrimitive) correspondingGoodie));

                } else if (correspondingGoodie instanceof GoodieObject) {
                    Object fieldValue = field.get(configObject);
                    if (fieldValue == null) field.set(configObject, field.getType().newInstance());
                    fillConfig((GoodieObject) correspondingGoodie, field.get(configObject));
                }
            }
        }
        return configObject;
    }

    private void fillField(Field field, Object object, GoodiePrimitive goodie) throws IllegalAccessException {
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
    }

}
