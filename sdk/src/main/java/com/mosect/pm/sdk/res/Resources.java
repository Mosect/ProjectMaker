package com.mosect.pm.sdk.res;

import com.mosect.pm.sdk.data.DataEntry;
import com.mosect.pm.sdk.data.DataSet;
import com.mosect.pm.sdk.util.TextUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resources implements Closeable {

    private DataSet dataSet;
    private DataSet resDataSet;
    private boolean canCloseSet;
    private final List<ElementFactory> elementFactories = new ArrayList<>();
    private final Map<String, Element> cache = new HashMap<>();

    public Resources(DataSet dataSet, String path, boolean canCloseSet) {
        this.dataSet = dataSet;
        if (TextUtils.empty(path)) {
            this.resDataSet = dataSet;
        } else {
            this.resDataSet = dataSet.getSet(path);
        }
        this.canCloseSet = canCloseSet;
        addFactory(new DefElementFactory());
    }

    public Resources(Resources parent, String path) {
        this(parent.dataSet, path, false);
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

    /**
     * 获取资源元素
     *
     * @param type 类型
     * @param path 路径
     * @param <T>  元素类型
     * @return 元素对象，返回null，表示此路径不存在元素
     */
    public <T extends Element> T get(String type, String path) {
        return get(type, path, false);
    }

    /**
     * 获取资源元素
     *
     * @param type  类型
     * @param path  路径
     * @param force 是否强制获取
     * @param <T>   元素类型
     * @return 元素对象，返回null，表示此路径不存在元素
     */
    public <T extends Element> T get(String type, String path, boolean force) {
        String[] args = type.split(";");
        Element element = cache.get(path);
        if (null != element) {
            if (Arrays.equals(args, element.getArgs())) {
                // 类型一致
                return (T) element;
            } else {
                // 类型不一致
                if (force) {
                    // 强制打开，需要关闭旧element
                    try {
                        element.write();
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                } else {
                    // 不强制打开，但是又存在类型不一致，抛出异常
                    throw new IllegalStateException("Already exists cache element with type: " + Arrays.toString(element.getArgs()));
                }
            }
        }

        // 忽略旧缓存
        DataEntry dataEntry = resDataSet.getEntry(path);
        if (null != dataEntry) {
            for (ElementFactory f : elementFactories) {
                element = f.createElement(args, dataSet, dataEntry);
                if (null != element) {
                    try {
                        element.init();
                        cache.put(path, element);
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                    return (T) element;
                }
            }
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
        return null;
    }
}
