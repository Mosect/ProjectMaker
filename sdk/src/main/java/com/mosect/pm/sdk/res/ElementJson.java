package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;
import com.mosect.pm.sdk.json.Json;
import com.mosect.pm.sdk.value.Struct;

import java.io.IOException;
import java.io.OutputStream;

public class ElementJson extends ElementText {

    private Struct struct;

    public ElementJson(DataSet dataSet, DataEntry dataEntry, String[] args) {
        super(dataSet, dataEntry, args);
        setStruct(new Struct());
    }

    public Struct getStruct() {
        return struct;
    }

    public void setStruct(Struct struct) {
        if (null == struct) {
            throw new IllegalArgumentException("Struct is null");
        }
        this.struct = struct;
        super.setText(Json.getInstance().toJson(struct));
    }

    @Override
    public void setText(String text) {
        struct = Json.getInstance().fromJson(text);
        super.setText(text);
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws IOException {
        super.setText(Json.getInstance().toJson(struct));
        super.onWrite(outputStream);
    }
}
