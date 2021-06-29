package com.mosect.pm.sdk.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipDataSet extends DataSet {

    private ZipFile file;
    private boolean canCloseFile;
    private String prefixPath;

    private ZipDataSet(ZipFile file, boolean canCloseFile, String prefixPath) {
        this.file = file;
        this.canCloseFile = canCloseFile;
        this.prefixPath = prefixPath;
    }

    public ZipDataSet(ZipFile file) {
        this(file, true, "");
    }

    @Override
    public DataEntry getEntry(String path) {
        ZipEntry zipEntry = file.getEntry(prefixPath + path);
        if (null != zipEntry) {
            return new DataEntry(zipEntry.getName());
        }
        return null;
    }

    @Override
    public DataSet getSet(String path) {
        DataEntry entry = new DataEntry(path);
        if (entry.isSet()) {
            ZipEntry zipEntry = file.getEntry(prefixPath + path);
            if (null != zipEntry) {
                return new ZipDataSet(file, false, path);
            }
        }
        return null;
    }

    @Override
    protected void create(String path) throws IOException {
        throw new IOException("Readonly");
    }

    @Override
    public InputStream getInput(String path) throws IOException {
        ZipEntry zipEntry = file.getEntry(prefixPath + path);
        if (null == zipEntry) {
            throw new IOException("Entry not found: " + path);
        }
        return file.getInputStream(zipEntry);
    }

    @Override
    public OutputStream getOutput(String path) throws IOException {
        throw new IOException("Readonly");
    }

    @Override
    public void close() {
        super.close();
        if (canCloseFile) {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
