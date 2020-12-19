package net.programmer.igoodie.format;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import net.programmer.igoodie.converter.GoodieToGson;
import net.programmer.igoodie.converter.GsonToGoodie;
import net.programmer.igoodie.exception.ParseException;
import net.programmer.igoodie.runtime.ObjectGoodie;

public class GsonGoodieFormat extends GoodieFormat<JsonObject, ObjectGoodie> {

    @Override
    public ObjectGoodie serializeGoodie(JsonObject externalFormat) {
        return GsonToGoodie.convertObject(externalFormat);
    }

    @Override
    public JsonObject deserializeGoodie(ObjectGoodie goodie) {
        return GoodieToGson.convertObject(goodie);
    }

    @Override
    public JsonObject readFromString(String text) {
        JsonElement jsonElement = JsonParser.parseString(text);
        if (!jsonElement.isJsonObject())
            throw new ParseException();
        return jsonElement.getAsJsonObject();
    }

    @Override
    public String writeToString(JsonObject externalFormat) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(externalFormat);
    }

}
