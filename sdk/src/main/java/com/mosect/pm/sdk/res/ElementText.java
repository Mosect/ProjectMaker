package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;
import com.mosect.pm.sdk.util.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ElementText extends Element {

    private String text;

    public ElementText(DataSet dataSet, DataEntry dataEntry, String[] args) {
        super(dataSet, dataEntry, args);
    }

    @Override
    public void init() throws IOException {
        read();
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
            String text;
            if (getArgs().length > 1) {
                text = temp.toString(getArgs()[1]);
            } else {
                text = temp.toString();
            }
            setText(text);
        }
    }

    @Override
    protected void onWrite(OutputStream outputStream) throws IOException {
        if (TextUtils.notEmpty(text)) {
            byte[] data;
            if (getArgs().length > 1) {
                data = getText().getBytes(getArgs()[1]);
            } else {
                data = getText().getBytes();
            }
            outputStream.write(data);
            outputStream.flush();
        }
    }
}
