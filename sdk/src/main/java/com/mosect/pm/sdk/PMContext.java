package com.mosect.pm.sdk;

import com.mosect.pm.sdk.util.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PM上下文
 */
public final class PMContext {

    public final static Version VERSION = new Version("1.0.0");

    private static PMContext instance;

    public static PMContext getInstance() {
        if (null == instance) {
            synchronized (PMContext.class) {
                if (null == instance) {
                    instance = new PMContext();
                }
            }
        }
        return instance;
    }

    private final List<PMExt> extList = new ArrayList<>();
    private final Map<String, PMExt> idExtMap = new HashMap<>();

    private PMContext() {
    }

    public void addExt(PMExt ext) throws PMException {
        if (!ext.isInitialed()) {
            throw new PMException("Ext not initialed", 201);
        }
        PMExt old = idExtMap.get(ext.getId());
        if (null != old) {
            throw new PMException("Repeat ext id: " + ext.getId(), 202);
        }
        idExtMap.put(ext.getId(), ext);
        extList.add(ext);
    }

    public <T extends PMExt> T findExt(String id) {
        return (T) idExtMap.get(id);
    }

    public int getExtCount() {
        return extList.size();
    }

    public PMExt getExt(int index) {
        return extList.get(index);
    }

    public Iterable<PMExt> extIterator() {
        return extList;
    }
}
