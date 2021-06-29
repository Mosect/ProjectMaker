package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;
import com.mosect.pm.sdk.util.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TextElement extends Element {

    private String text;

    public TextElement(DataSet dataSet, DataEntry dataEntry, String[] args) {
        super(dataSet, dataEntry, args);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onRead(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream temp = new ByteArrayOutputStream()) {
            int len;
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) > 0) {
                temp.write(buffer, 0, len);
            }
            if (getArgs().length > 1) {
                text = temp.toString(getArgs()[1]);
            } else {
                text = temp.toString();
            }
        }
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws IOException {
        if (TextUtils.notEmpty(text)) {
            byte[] data;
            if (getArgs().length > 1) {
                data = text.getBytes(getArgs()[1]);
            } else {
                data = text.getBytes();
            }
            outputStream.write(data);
            outputStream.flush();
        }
    }
}
