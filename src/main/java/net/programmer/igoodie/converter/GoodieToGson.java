package net.programmer.igoodie.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.programmer.igoodie.runtime.ArrayGoodie;
import net.programmer.igoodie.runtime.ConfigGoodie;
import net.programmer.igoodie.runtime.ObjectGoodie;
import net.programmer.igoodie.runtime.PrimitiveGoodie;

public class GoodieToGson {

    public static JsonElement convert(ConfigGoodie goodie) {
        if (goodie instanceof ObjectGoodie)
            return convertObject((ObjectGoodie) goodie);
        if (goodie instanceof ArrayGoodie)
            return convertArray((ArrayGoodie) goodie);
        if (goodie instanceof PrimitiveGoodie)
            return convertPrimitive((PrimitiveGoodie) goodie);

        return null; // <-- No corresponding Gson type exists
    }

    public static JsonObject convertObject(ObjectGoodie goodieObject) {
        JsonObject jsonObject = new JsonObject();
        for (String propertyName : goodieObject.keySet()) {
            JsonElement jsonElement = convert(goodieObject.get(propertyName));
            jsonObject.add(propertyName, jsonElement);
        }
        return jsonObject;
    }

    public static JsonArray convertArray(ArrayGoodie goodieArray) {
        JsonArray jsonArray = new JsonArray();
        for (ConfigGoodie goodie : goodieArray) {
            JsonElement jsonElement = convert(goodie);
            jsonArray.add(jsonElement);
        }
        return jsonArray;
    }

    public static JsonPrimitive convertPrimitive(PrimitiveGoodie goodiePrimitive) {
        Class<?> type = goodiePrimitive.getType();
        if (Number.class.isAssignableFrom(type))
            return new JsonPrimitive(goodiePrimitive.getNumberValue());
        if (String.class.isAssignableFrom(type))
            return new JsonPrimitive(goodiePrimitive.getStringValue());
        if (Boolean.class.isAssignableFrom(type))
            return new JsonPrimitive(goodiePrimitive.getBooleanValue());
        return new JsonPrimitive(goodiePrimitive.getStringValue());
    }

}
