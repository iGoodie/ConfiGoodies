package net.programmer.igoodie.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;

public class GoodieToGson {

    public static JsonElement convert(GoodieElement goodieElement) {
        if (goodieElement instanceof GoodieObject)
            return convertObject((GoodieObject) goodieElement);
        if (goodieElement instanceof GoodieArray)
            return convertArray((GoodieArray) goodieElement);
        if (goodieElement instanceof GoodiePrimitive)
            return convertPrimitive((GoodiePrimitive) goodieElement);

        return null; // <-- No corresponding Gson type exists
    }

    public static JsonObject convertObject(GoodieObject goodieObject) {
        JsonObject jsonObject = new JsonObject();
        for (String propertyName : goodieObject.keySet()) {
            JsonElement jsonElement = convert(goodieObject.get(propertyName));
            jsonObject.add(propertyName, jsonElement);
        }
        return jsonObject;
    }

    public static JsonArray convertArray(GoodieArray goodieArray) {
        JsonArray jsonArray = new JsonArray();
        for (GoodieElement goodieElement : goodieArray) {
            JsonElement jsonElement = convert(goodieElement);
            jsonArray.add(jsonElement);
        }
        return jsonArray;
    }

    public static JsonPrimitive convertPrimitive(GoodiePrimitive goodiePrimitive) {
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
