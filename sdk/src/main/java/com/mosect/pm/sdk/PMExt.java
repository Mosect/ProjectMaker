package com.mosect.pm.sdk;

import com.mosect.pm.sdk.util.TextUtils;

import java.util.Properties;

/**
 * Ext
 */
public abstract class PMExt {

    private String group;
    private String name;
    private String id;

    /**
     * 初始化Ext
     *
     * @param properties ext配置属性
     */
    public final void init(Properties properties) throws PMException {
        id = properties.getProperty("id", null);
        if (TextUtils.empty(id)) {
            throw new PMException("Ext id not found", 1);
        }
        group = properties.getProperty("group", null);
        name = properties.getProperty("name", null);
        onInit();
    }

    public final void destroy() {
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

    protected void onInit() {
    }
}
