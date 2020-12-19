package net.programmer.igoodie.format;

import com.google.gson.*;
import net.programmer.igoodie.converter.GoodieToGson;
import net.programmer.igoodie.converter.GsonToGoodie;
import net.programmer.igoodie.exception.ParseException;
import net.programmer.igoodie.runtime.GoodieObject;

public class GsonGoodieFormat extends GoodieFormat<JsonObject, GoodieObject> {

    @Override
    public GoodieObject writeToGoodie(JsonObject externalFormat) {
        return GsonToGoodie.convertObject(externalFormat);
    }

    @Override
    public JsonObject readFromGoodie(GoodieObject goodieObject) {
        return GoodieToGson.convertObject(goodieObject);
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
