package com.mosect.pm.sdk.json;

import com.mosect.pm.sdk.value.Struct;

public interface JsonEngine {

    Struct fromJson(String json);

    String toJson(Struct struct);
}
