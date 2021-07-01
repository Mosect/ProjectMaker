package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Element {

    private DataSet dataSet;
    private DataEntry dataEntry;
    private String[] args;

    public Element(DataSet dataSet, DataEntry dataEntry, String[] args) {
        this.dataSet = dataSet;
        this.dataEntry = dataEntry;
        this.args = args;
    }

    public void init() throws IOException {
    }

    public String[] getArgs() {
        return args;
    }

    public void read() throws IOException {
        try (InputStream inputStream = dataSet.getInput(dataEntry.getPath())) {
            onRead(inputStream);
        }
    }

    public void write() throws IOException {
        try (OutputStream outputStream = dataSet.getOutput(dataEntry.getPath())) {
            onWrite(outputStream);
        }
    }

    protected abstract void onRead(InputStream inputStream) throws IOException;

    protected abstract void onWrite(OutputStream outputStream) throws IOException;
}
