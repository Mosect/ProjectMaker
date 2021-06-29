package com.mosect.pm.sdk.json;

public class JsonEntry {

    public final int indexKey;
    public final String strKey;
    public final Json value;

    public JsonEntry(int indexKey, String strKey, Json value) {
        this.indexKey = indexKey;
        this.strKey = strKey;
        this.value = value;
    }
}
