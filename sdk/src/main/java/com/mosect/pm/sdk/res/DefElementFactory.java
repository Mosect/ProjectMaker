package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;

public class DefElementFactory implements ElementFactory {

    DefElementFactory() {
    }

    @Override
    public Element createElement(String[] args, DataSet dataSet, DataEntry dataEntry) {
        switch (args[0]) {
            case "text":
                return new ElementText(dataSet, dataEntry, args);
            case "json":
                return new ElementJson(dataSet, dataEntry, args);
            case "properties":
                return new ElementProperties(dataSet, dataEntry, args);
            case "xml":
                return new ElementXml(dataSet, dataEntry, args);
        }
        return null;
    }
}
