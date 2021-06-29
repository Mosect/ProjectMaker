package com.mosect.pm.sdk.json;

public interface JsonEngine {

    Json fromJson(String text);

    String toJson(Json json);
}
