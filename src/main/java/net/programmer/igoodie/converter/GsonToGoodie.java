package net.programmer.igoodie.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.programmer.igoodie.runtime.GoodieArray;
import net.programmer.igoodie.runtime.GoodieElement;
import net.programmer.igoodie.runtime.GoodieObject;
import net.programmer.igoodie.runtime.GoodiePrimitive;

public final class GsonToGoodie {

    public static GoodieElement convert(JsonElement jsonElement) {
        if (jsonElement.isJsonObject())
            return convertObject(jsonElement.getAsJsonObject());
        if (jsonElement.isJsonArray())
            return convertArray(jsonElement.getAsJsonArray());
        if (jsonElement.isJsonPrimitive())
            return convertPrimitive(jsonElement.getAsJsonPrimitive());

        return null; // <-- No corresponding Goodie type exists
    }

    public static GoodieObject convertObject(JsonObject jsonObject) {
        GoodieObject goodieObject = new GoodieObject();
        for (String propertyName : jsonObject.keySet()) {
            GoodieElement goodieElement = convert(jsonObject.get(propertyName));
            goodieObject.put(propertyName, goodieElement);
        }
        return goodieObject;
    }

    public static GoodieArray convertArray(JsonArray jsonArray) {
        GoodieArray goodieArray = new GoodieArray();
        for (JsonElement jsonElement : jsonArray) {
            GoodieElement goodieElement = convert(jsonElement);
            goodieArray.add(goodieElement);
        }
        return goodieArray;
    }

    public static GoodiePrimitive convertPrimitive(JsonPrimitive jsonPrimitive) {
        return GoodiePrimitive.from(
                jsonPrimitive.isBoolean() ? jsonPrimitive.getAsBoolean()
                        : jsonPrimitive.isString() ? jsonPrimitive.getAsString()
                        : jsonPrimitive.isNumber() ? jsonPrimitive.getAsNumber()
                        : jsonPrimitive.getAsString()
        );
    }

}
