package com.mosect.pm.sdk;

import com.mosect.pm.sdk.util.TextUtils;
import com.mosect.pm.sdk.value.Struct;

public class PMConfig {

    private Struct data;

    public PMConfig() {
        this(null);
    }

    public PMConfig(Struct data) {
        if (null == data) {
            this.data = new Struct();
            this.data.emptyMap();
        } else {
            this.data = data;
            if (data.isMapNull()) {
                data.emptyMap();
            }
        }
    }

    public Struct getValue(Struct attrStruct) {
        String group = attrStruct.get("group").str();
        if (TextUtils.empty(group)) {

        }
        return null;
    }
}
