package com.mosect.pm.sdk.util;

public class TextUtils {

    public static boolean empty(CharSequence cs) {
        return null == cs || cs.length() <= 0;
    }

    public static boolean notEmpty(CharSequence cs) {
        return null != cs && cs.length() > 0;
    }
}
