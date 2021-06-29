package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class Resources implements Closeable {

    private DataSet dataSet;
    private boolean canCloseSet;
    private final List<ElementFactory> elementFactories = new ArrayList<>();

    public Resources(DataSet dataSet, boolean canCloseSet) {
        this.dataSet = dataSet;
        this.canCloseSet = canCloseSet;
    }

    @Override
    public void close() {
        if (canCloseSet) {
            dataSet.close();
        }
    }

    public void addFactory(ElementFactory factory) {
        elementFactories.add(factory);
    }

    public <T extends Element> T get(String type, String path) {
        DataEntry dataEntry = dataSet.getEntry(path);
        if (null != dataEntry) {
            for (ElementFactory f : elementFactories) {
                String[] args = type.split(";");
                Element element = f.createElement(args, dataSet, dataEntry);
                if (null != element) {
                    return (T) element;
                }
            }
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
        return null;
    }
}
