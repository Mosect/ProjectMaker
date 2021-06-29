package com.mosect.pm.sdk.data;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class DataSet implements Closeable {

    public abstract DataEntry getEntry(String path);

    public abstract DataSet getSet(String path);

    public DataEntry createIfNotExists(String path) throws IOException {
        DataEntry entry = getEntry(path);
        if (null == entry) {
            create(path);
            entry = new DataEntry(path);
        }
        return entry;
    }

    protected abstract void create(String path) throws IOException;

    public abstract InputStream getInput(String path) throws IOException;

    public abstract OutputStream getOutput(String path) throws IOException;

    @Override
    public void close() {
    }
}
