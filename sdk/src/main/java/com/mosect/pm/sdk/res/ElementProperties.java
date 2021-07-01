package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

public class ElementProperties extends ElementText {

    private final Properties properties;

    public ElementProperties(DataSet dataSet, DataEntry dataEntry, String[] args) {
        super(dataSet, dataEntry, args);
        properties = new Properties();
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setText(String text) {
        try (StringReader reader = new StringReader(text)) {
            try {
                properties.load(reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        super.setText(text);
    }

    public void syncText() {
        StringWriter writer = new StringWriter();
        try {
            properties.store(writer, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.setText(writer.toString());
    }
}
