package com.mosect.pm.sdk;

import com.mosect.pm.sdk.util.Version;

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

    private PMContext() {
    }
}
