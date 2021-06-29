package com.mosect.pm.sdk.data;

import com.mosect.pm.sdk.util.PathUtils;

import java.util.Arrays;
import java.util.Objects;

public class DataEntry {

    private String path;
    private String name;
    private String[] fragments;
    private boolean set;

    public DataEntry(String path) {
        this.path = path;
        fragments = PathUtils.splitPath(path);
        name = fragments[fragments.length - 1];
        set = path.endsWith("/");
    }

    private DataEntry(String path, String name, String[] fragments, boolean set) {
        this.path = path;
        this.name = name;
        this.fragments = fragments;
        this.set = set;
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DataEntry) {
            DataEntry other = (DataEntry) o;
            return Objects.equals(other.path, path);
        }
        return false;
    }

    public DataEntry getParent() {
        if (fragments.length > 1) {
            int length = fragments.length;
            for (int i = 0; i < fragments.length - 1; i++) {
                length += fragments[i].length();
            }
            String pp = path.substring(0, length);
            String pn = fragments[fragments.length - 1];
            String[] pfs = Arrays.copyOf(fragments, fragments.length - 1);
            return new DataEntry(pp, pn, pfs, true);
        }
        return null;
    }

    public int getFragmentCount() {
        return fragments.length;
    }

    public String getFragment(int index) {
        return fragments[index];
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public boolean isSet() {
        return set;
    }
}
