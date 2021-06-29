package com.mosect.pm.sdk.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Json {

    private JsonEngine engine;
    private JsonType type = JsonType.NULL;
    private Object value;

    public Json(JsonEngine engine) {
        this.engine = engine;
    }

    public Json() {
        this(new GSONEngine());
    }

    public JsonType getType() {
        return type;
    }

    public List<JsonEntry> entries() {
        int count = size();
        if (count > 0) {
            List<JsonEntry> list = new ArrayList<>(count);
            if (type == JsonType.OBJ) {
                Map<String, Json> object = (Map<String, Json>) value;
                Set<Map.Entry<String, Json>> entrySet = object.entrySet();
                for (Map.Entry<String, Json> entry : entrySet) {
                    JsonEntry je = new JsonEntry(-1, entry.getKey(), entry.getValue());
                    list.add(je);
                }
            } else if (type == JsonType.ARRAY) {
                List<Json> array = (List<Json>) value;
                for (int i = 0; i < array.size(); i++) {
                    JsonEntry je = new JsonEntry(i, null, array.get(i));
                    list.add(je);
                }
            }
            return list;
        }
        return Collections.emptyList();
    }

    public List<Json> values() {
        int count = size();
        if (count > 0) {
            List<Json> list = new ArrayList<>(count);
            if (type == JsonType.OBJ) {
                Map<String, Json> object = (Map<String, Json>) value;
                list.addAll(object.values());
            } else if (type == JsonType.ARRAY) {
                List<Json> array = (List<Json>) value;
                list.addAll(array);
            }
            return list;
        }
        return Collections.emptyList();
    }

    public List<String> keys() {
        int count = size();
        if (count > 0 && type == JsonType.OBJ) {
            Map<String, Json> object = (Map<String, Json>) value;
            return new ArrayList<>(object.keySet());
        }
        return Collections.emptyList();
    }

    public Json get(String key) {
        if (type == JsonType.OBJ) {
            Map<String, Json> object = (Map<String, Json>) value;
            return object.get(key);
        }
        return null;
    }

    public Json get(int index) {
        if (type == JsonType.ARRAY) {
            List<Json> array = (List<Json>) value;
            return array.get(index);
        }
        return null;
    }

    public void set(Json src) {
        if (null == src) {
            setNull();
        } else {
            type = src.type;
            if (type == JsonType.OBJ) {
                Map<String, Json> obj = (Map<String, Json>) src.value;
                value = new HashMap<>(obj);
            } else if (type == JsonType.ARRAY) {
                List<Json> array = (List<Json>) src.value;
                value = new ArrayList<>(array);
            } else {
                value = src.value;
            }
        }
    }

    public void set(Boolean value) {
        if (null == value) {
            setNull();
        } else {
            type = JsonType.BOOLEAN;
            this.value = value;
        }
    }

    public void set(Number value) {
        if (null == value) {
            setNull();
        } else {
            if (value instanceof Integer || value instanceof Long ||
                    value instanceof Short || value instanceof Byte) {
                type = JsonType.INTEGER;
            } else if (value instanceof Double || value instanceof Float) {
                type = JsonType.FLOAT;
            } else {
                throw new IllegalArgumentException("Unsupported number value type: " + value.getClass());
            }
            this.value = value;
        }
    }

    public void set(String value) {
        if (null == value) {
            setNull();
        } else {
            type = JsonType.STRING;
            this.value = value;
        }
    }

    public void setNull() {
        type = JsonType.NULL;
        value = null;
    }

    public void setArray() {
        type = JsonType.ARRAY;
        value = new ArrayList<>();
    }

    public void setObj() {
        type = JsonType.OBJ;
        value = new HashMap<>();
    }

    public Json at(String key) {
        if (type != JsonType.OBJ) {
            setObj();
        }
        Map<String, Json> obj = (Map<String, Json>) value;
        Json v = obj.get(key);
        if (null == v) {
            v = new Json(engine);
            obj.put(key, v);
        }
        return v;
    }

    public Json at(int index) {
        if (type != JsonType.ARRAY) {
            setArray();
        }
        List<Json> array = (List<Json>) value;
        while (array.size() <= index) {
            array.add(new Json(engine));
        }
        return array.get(index);
    }

    public boolean b(boolean def) {
        if (type == JsonType.BOOLEAN) {
            return (boolean) value;
        }
        return def;
    }

    public Boolean b() {
        if (type == JsonType.BOOLEAN) {
            return (Boolean) value;
        }
        return null;
    }

    public int i(int def) {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).intValue();
        }
        return def;
    }

    public long l(long def) {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).longValue();
        }
        return def;
    }

    public float f(float def) {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).floatValue();
        }
        return def;
    }

    public double d(double def) {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).doubleValue();
        }
        return def;
    }

    public Integer i() {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).intValue();
        }
        return null;
    }

    public Long l() {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).longValue();
        }
        return null;
    }

    public Float f() {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).floatValue();
        }
        return null;
    }

    public Double d() {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value).doubleValue();
        }
        return null;
    }

    public Number n() {
        if (type == JsonType.FLOAT || type == JsonType.INTEGER) {
            return ((Number) value);
        }
        return null;
    }

    public String str() {
        if (type == JsonType.STRING) {
            return (String) value;
        }
        return null;
    }

    public int size() {
        if (type == JsonType.OBJ) {
            Map<String, Json> obj = (Map<String, Json>) value;
            return obj.size();
        } else if (type == JsonType.ARRAY) {
            List<Json> array = (List<Json>) value;
            return array.size();
        }
        return 0;
    }
}
