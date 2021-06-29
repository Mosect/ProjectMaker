package com.mosect.pm.sdk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

/**
 * Ext
 */
public abstract class PMExt {

    private String group;
    private String name;
    private String id;
    private boolean destroyed;
    private boolean initialed;
    protected final List<PMAttr> attrs = new ArrayList<>();
    private boolean attrsChanged;

    /**
     * 初始化Ext
     *
     * @param jarFile Ext的jar文件
     */
    public void init(File jarFile) throws PMException {
        if (initialed) {
            throw new IllegalStateException("Repeat initial");
        }
        ZipFile file;
        try {
            file = new ZipFile(jarFile);
        } catch (IOException e) {
            throw new PMException("Can't open jar file:" + jarFile, e, 1);
        }

        initialed = true;
    }

    /**
     * 判断是否初始化
     *
     * @return true，已经初始化；false，未初始化
     */
    public final boolean isInitialed() {
        return initialed;
    }

    /**
     * 销毁Ext
     */
    public void destroy() {
        if (destroyed) return;
        destroyed = true;
    }

    /**
     * 判断是否销毁
     *
     * @return true，销毁；false，未销毁
     */
    public final boolean isDestroyed() {
        return destroyed;
    }

    /**
     * 清楚缓存标志
     */
    public void clearCacheFlags() {
        attrsChanged = false;
    }

    /**
     * 更新属性，如果更改了属性，需要调用{@link #changedAttrs() changedAttrs}
     */
    public void updateAttrs() {
    }

    /**
     * 如果更改了属性，需要调用此方法
     */
    protected void changedAttrs() {
        attrsChanged = true;
    }

    /**
     * 判断属性是否发生更改
     *
     * @return true，属性发生更改；false，属性未发生更改
     */
    public boolean isAttrsChanged() {
        return attrsChanged;
    }

    /**
     * 获取Ext分组
     *
     * @return Ext分组
     */
    public final String getGroup() {
        return group;
    }

    /**
     * 获取Ext名称
     *
     * @return Ext名称
     */
    public final String getName() {
        return name;
    }

    /**
     * 获取Ext ID
     *
     * @return Ext ID
     */
    public final String getId() {
        return id;
    }
}
