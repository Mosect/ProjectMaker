package com.mosect.pm.sdk.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Struct {

    private Object value;
    private Map<String, Struct> map;
    private List<Struct> list;

    public Struct() {
    }

    public Struct(String value) {
        set(value);
    }

    public Struct(Number value) {
        set(value);
    }

    public Struct(boolean value) {
        set(value);
    }

    public int listSize() {
        return null == list ? 0 : list.size();
    }

    public void nullList() {
        list = null;
    }

    public boolean isListNull() {
        return null == list;
    }

    public void emptyList() {
        if (null == list) list = new ArrayList<>();
        else list.clear();
    }

    public int mapSize() {
        return null == map ? 0 : map.size();
    }

    public void nullMap() {
        map = null;
    }

    public void emptyMap() {
        if (null == map) map = new HashMap<>();
        else map.clear();
    }

    public boolean isMapNull() {
        return null == map;
    }

    public Struct get(int index) {
        if (index < 0 || index >= listSize()) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        return list.get(index);
    }

    public void set(int index, Struct value) {
        if (index < 0 || index >= listSize()) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        if (null == value) throw new IllegalArgumentException("Value is null");
        if (null == list) list = new ArrayList<>();
        list.set(index, value);
    }

    public void add(int index, Struct value) {
        if (index < 0 || index > listSize()) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        if (null == value) throw new IllegalArgumentException("Value is null");
        if (null == list) list = new ArrayList<>();
        list.add(index, value);
    }

    public void add(Struct value) {
        add(listSize(), value);
    }

    public Struct get(String key) {
        return null == map ? null : map.get(key);
    }

    public void put(String key, Struct value) {
        if (null == key) throw new IllegalArgumentException("Key is null");
        if (null == value) throw new IllegalArgumentException("Value is null");
        if (null == map) map = new HashMap<>();
        map.put(key, value);
    }

    public Set<String> keys() {
        return null == map ? Collections.emptySet() : map.keySet();
    }

    public Collection<Struct> values() {
        return null == map ? Collections.emptyList() : map.values();
    }

    public Set<Map.Entry<String, Struct>> entries() {
        return null == map ? Collections.emptySet() : map.entrySet();
    }

    public Struct at(int index) {
        if (index < 0) throw new IllegalArgumentException("Invalid index: " + index);
        if (null == list) list = new ArrayList<>(index + 1);
        while (listSize() <= index) {
            add(new Struct());
        }
        return get(index);
    }

    public Struct at(String key) {
        if (null == key) throw new IllegalArgumentException("Key is null");
        if (null == map) map = new HashMap<>();
        Struct value = get(key);
        if (null == value) {
            value = new Struct();
            map.put(key, value);
        }
        return value;
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public boolean isInteger() {
        return value instanceof Integer;
    }

    public boolean isLong() {
        return value instanceof Long;
    }

    public boolean isFloat() {
        return value instanceof Float;
    }

    public boolean isDouble() {
        return value instanceof Double;
    }

    public boolean isBool() {
        return value instanceof Boolean;
    }

    public boolean isNull() {
        return null == value;
    }

    public Object v() {
        return value;
    }

    public Number n() {
        return (Number) value;
    }

    public int i() {
        return ((Number) value).intValue();
    }

    public long l() {
        return ((Number) value).longValue();
    }

    public float f() {
        return ((Number) value).floatValue();
    }

    public double d() {
        return ((Number) value).doubleValue();
    }

    public boolean b() {
        return (boolean) value;
    }

    public String str() {
        return (String) value;
    }

    public void set(int value) {
        this.value = value;
    }

    public void set(long value) {
        this.value = value;
    }

    public void set(float value) {
        this.value = value;
    }

    public void set(double value) {
        this.value = value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public void set(String value) {
        this.value = value;
    }

    public void set(Number value) {
        this.value = value;
    }

    public void setNull() {
        this.value = null;
    }
}
