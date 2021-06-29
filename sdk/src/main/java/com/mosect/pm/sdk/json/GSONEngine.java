package com.mosect.pm.sdk.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.Map;

public class GSONEngine implements JsonEngine {

    private Gson gson = new Gson();

    private Json convert(JsonElement element) {
        Json result = new Json(this);
        if (element.isJsonObject()) {
            result.setObj();
            JsonObject object = element.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Json v = convert(entry.getValue());
                result.at(entry.getKey()).set(v);
            }
        } else if (element.isJsonArray()) {
            result.setArray();
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonElement child = array.get(i);
                Json cv = convert(child);
                result.at(i).set(cv);
            }
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                Number number = primitive.getAsNumber();
                result.set(number);
            } else if (primitive.isBoolean()) {
                result.set(primitive.getAsBoolean());
            } else if (primitive.isString()) {
                result.set(primitive.getAsString());
            }
        }
        return result;
    }

    private JsonElement convert(Json json) {
        switch (json.getType()) {
            case OBJ: {
                JsonObject object = new JsonObject();
                List<JsonEntry> entries = json.entries();
                for (JsonEntry entry : entries) {
                    JsonElement v = convert(entry.value);
                    object.add(entry.strKey, v);
                }
                return object;
            }
            case ARRAY: {
                JsonArray array = new JsonArray();
                List<JsonEntry> entries = json.entries();
                for (JsonEntry entry : entries) {
                    JsonElement v = convert(entry.value);
                    array.add(v);
                }
                return array;
            }
            case BOOLEAN:
                return new JsonPrimitive(json.b());
            case STRING:
                return new JsonPrimitive(json.str());
            case INTEGER:
            case FLOAT:
                return new JsonPrimitive(json.n());
            case NULL:
            default:
                return JsonNull.INSTANCE;
        }
    }

    @Override
    public Json fromJson(String text) {
        JsonElement element = gson.fromJson(text, JsonElement.class);
        if (null == element) {
            element = JsonNull.INSTANCE;
        }
        return convert(element);
    }

    @Override
    public String toJson(Json json) {
        JsonElement element = convert(json);
        return gson.toJson(element);
    }
}
