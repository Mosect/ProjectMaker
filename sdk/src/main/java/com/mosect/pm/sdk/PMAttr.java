package com.mosect.pm.sdk;

import com.mosect.pm.sdk.value.Struct;

public abstract class PMAttr<T extends PMAttr> {

    private String group;
    private String id;
    private String name;
    private String type;
    private final Struct struct = new Struct();

    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public T setType(String type) {
        this.type = type;
        return (T) this;
    }

    public T setId(String id) {
        this.id = id;
        return (T) this;
    }

    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public T setGroup(String group) {
        this.group = group;
        return (T) this;
    }

    public Struct getStruct() {
        return struct;
    }

    public Struct handleInput(Struct input) {
        return input;
    }

    public void init() {
        struct.emptyMap();
        struct.at("id").set(id);
        struct.at("group").set(group);
        struct.at("name").set(name);
        struct.at("type").set(type);
    }
}
