package net.programmer.igoodie.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.programmer.igoodie.runtime.ArrayGoodie;
import net.programmer.igoodie.runtime.ConfigGoodie;
import net.programmer.igoodie.runtime.ObjectGoodie;
import net.programmer.igoodie.runtime.PrimitiveGoodie;

public final class GsonToGoodie {

    public static ConfigGoodie convert(JsonElement jsonElement) {
        if (jsonElement.isJsonObject())
            return convertObject(jsonElement.getAsJsonObject());
        if (jsonElement.isJsonArray())
            return convertArray(jsonElement.getAsJsonArray());
        if (jsonElement.isJsonPrimitive())
            return convertPrimitive(jsonElement.getAsJsonPrimitive());

        return null; // <-- No corresponding Goodie type exists
    }

    public static ObjectGoodie convertObject(JsonObject jsonObject) {
        ObjectGoodie goodieObject = new ObjectGoodie();
        for (String propertyName : jsonObject.keySet()) {
            ConfigGoodie goodie = convert(jsonObject.get(propertyName));
            goodieObject.put(propertyName, goodie);
        }
        return goodieObject;
    }

    public static ArrayGoodie convertArray(JsonArray jsonArray) {
        ArrayGoodie goodieArray = new ArrayGoodie();
        for (JsonElement jsonElement : jsonArray) {
            ConfigGoodie goodie = convert(jsonElement);
            goodieArray.add(goodie);
        }
        return goodieArray;
    }

    public static PrimitiveGoodie convertPrimitive(JsonPrimitive jsonPrimitive) {
        return PrimitiveGoodie.from(
                jsonPrimitive.isBoolean() ? jsonPrimitive.getAsBoolean()
                        : jsonPrimitive.isString() ? jsonPrimitive.getAsString()
                        : jsonPrimitive.isNumber() ? jsonPrimitive.getAsNumber()
                        : jsonPrimitive.getAsString()
        );
    }

}
