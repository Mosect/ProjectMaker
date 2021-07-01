package com.mosect.pm.sdk.json;

import com.mosect.pm.sdk.value.Struct;

public class Json implements JsonEngine {

    private static Json instance;

    public static Json getInstance() {
        if (null == instance) {
            synchronized (Json.class) {
                if (null == instance) {
                    instance = new Json(new GsonEngine());
                }
            }
        }
        return instance;
    }

    private JsonEngine engine;

    public Json(JsonEngine engine) {
        if (null == engine) {
            throw new IllegalArgumentException("engine is null");
        }
        this.engine = engine;
    }

    @Override
    public Struct fromJson(String json) {
        return engine.fromJson(json);
    }

    @Override
    public String toJson(Struct struct) {
        return engine.toJson(struct);
    }
}
