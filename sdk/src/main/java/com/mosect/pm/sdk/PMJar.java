package com.mosect.pm.sdk;

import com.mosect.pm.sdk.data.ZipDataSet;
import com.mosect.pm.sdk.res.ElementProperties;
import com.mosect.pm.sdk.res.Resources;
import com.mosect.pm.sdk.util.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipFile;

public class PMJar implements Closeable {

    public static PMJar install(File jarFile) throws PMException {
        ZipFile file;
        try {
            file = new ZipFile(jarFile);
        } catch (Exception e) {
            throw new PMException("Can't open jar file:" + jarFile, e, 1);
        }

        Resources resources;
        try {
            ZipDataSet dataSet = new ZipDataSet(file);
            resources = new Resources(dataSet, "ext-data", false);
        } catch (Exception e) {
            throw new PMException("Can't create resource:" + jarFile, e, 2);
        }

        ElementProperties ep = resources.get("properties;utf-8", "META-INF/ext.properties");
        if (null == ep) {
            throw new PMException("META-INF/ext.properties not found in jar file: " + jarFile, 3);
        }
        String name = ep.getProperties().getProperty("name", null);
        String group = ep.getProperties().getProperty("group", null);
        String id = ep.getProperties().getProperty("id", null);
        String extClassName = ep.getProperties().getProperty("ext-class-name", null);
        if (TextUtils.empty(id)) {
            throw new PMException("META-INF/ext.properties: empty id", 4);
        }
        if (TextUtils.empty(name)) {
            throw new PMException("META-INF/ext.properties: empty name", 4);
        }
        if (TextUtils.empty(extClassName)) {
            throw new PMException("META-INF/ext.properties: empty ext-class-name", 4);
        }

        // 加载jar包
        Class<?> extClass;
        try {
            URL url = jarFile.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            extClass = classLoader.loadClass(extClassName);
        } catch (Exception e) {
            throw new PMException("Load jar classes failed: " + jarFile, 5);
        }

        return new PMJar(id, name, group, resources, extClass);
    }

    private String id;
    private String name;
    private String group;
    private Resources resources;
    private Class<?> extClass;

    private PMJar(String id, String name, String group, Resources resources, Class<?> extClass) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.resources = resources;
        this.extClass = extClass;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public Resources getResources() {
        return resources;
    }

    public Class<?> getExtClass() {
        return extClass;
    }

    @Override
    public void close() {
        resources.close();
        extClass = null;
    }
}
