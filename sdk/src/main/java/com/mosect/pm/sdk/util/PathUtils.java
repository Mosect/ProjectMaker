package com.mosect.pm.sdk.util;

public class PathUtils {

    public static String[] splitPath(String path) {
        if (TextUtils.empty(path)) {
            throw new IllegalArgumentException("Empty path");
        }
        String[] list = path.split("/");
        for (String s : list) {
            if (TextUtils.empty(s)) {
                throw new IllegalArgumentException("Invalid path: " + path);
            }
        }
        return list;
    }
}
