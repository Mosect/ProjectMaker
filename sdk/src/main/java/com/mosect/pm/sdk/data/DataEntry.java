package com.mosect.pm.sdk.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class DataEntry {

    private DataEntry parent;
    private String name;
    private String path;

    public DataEntry(DataEntry parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    /**
     * 获取父数据集
     *
     * @return 父数据集
     */
    public DataEntry getParent() {
        return parent;
    }

    /**
     * 获取数据集名称
     *
     * @return 数据集名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取路径
     *
     * @return 路径
     */
    public String getPath() {
        if (null == path) {
            StringBuilder builder = new StringBuilder();
            makePath(builder);
            path = builder.toString();
        }
        return path;
    }

    /**
     * 构建路径
     *
     * @param builder 输出路径
     */
    protected void makePath(StringBuilder builder) {
        if (null != parent) {
            parent.makePath(builder);
        }
        builder.append('/');
        builder.append(name);
    }

    public abstract InputStream getInput() throws IOException;

    public abstract OutputStream getOutput() throws IOException;
}
