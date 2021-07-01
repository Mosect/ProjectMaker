package com.mosect.pm.sdk.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mosect.pm.sdk.value.Struct;

import java.util.Map;

public class GsonEngine implements JsonEngine {

    private Gson gson;

    public GsonEngine() {
        this(new GsonBuilder().setPrettyPrinting().serializeNulls().create());
    }

    public GsonEngine(Gson gson) {
        if (null == gson) throw new IllegalArgumentException("Gson is null");
        this.gson = gson;
    }

    @Override
    public Struct fromJson(String json) {
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        return convert(jsonElement);
    }

    @Override
    public String toJson(Struct struct) {
        JsonElement element = convert(struct);
        return gson.toJson(element);
    }

    private JsonElement convert(Struct struct) {
        if (struct.isNull() && struct.isListNull() && !struct.isMapNull()) {
            // obj
            JsonObject object = new JsonObject();
            for (Map.Entry<String, Struct> entry : struct.entries()) {
                JsonElement v = convert(entry.getValue());
                object.add(entry.getKey(), v);
            }
            return object;
        } else if (struct.isNull() && !struct.isListNull() && struct.isMapNull()) {
            // array
            JsonArray array = new JsonArray();
            int count = struct.listSize();
            for (int i = 0; i < count; i++) {
                Struct v = struct.get(i);
                JsonElement ev = convert(v);
                array.add(ev);
            }
            return array;
        } else if (struct.isNull() && struct.isListNull() && struct.isMapNull()) {
            // null
            return JsonNull.INSTANCE;
        } else if (struct.isListNull() && struct.isMapNull() && !struct.isNull()) {
            return gson.toJsonTree(struct.v());
        }
        throw new IllegalArgumentException("Unsupported multi type struct");
    }

    private Struct convert(JsonElement element) {
        if (null != element) {
            if (element.isJsonObject()) {
                JsonObject obj = element.getAsJsonObject();
                Struct result = new Struct();
                result.emptyMap();
                for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                    Struct value = convert(entry.getValue());
                    result.put(entry.getKey(), value);
                }
                return result;
            } else if (element.isJsonArray()) {
                JsonArray array = element.getAsJsonArray();
                Struct result = new Struct();
                result.emptyList();
                int count = array.size();
                for (int i = 0; i < count; i++) {
                    Struct value = convert(array.get(i));
                    result.add(value);
                }
                return result;
            } else if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                Struct result = new Struct();
                if (primitive.isString()) {
                    result.set(primitive.getAsString());
                } else if (primitive.isBoolean()) {
                    result.set(primitive.getAsBoolean());
                } else if (primitive.isNumber()) {
                    result.set(primitive.getAsNumber());
                }
                return result;
            }
        }
        return new Struct();
    }
}
