package com.mosect.pm.sdk;

public class PMException extends Exception {

    public final static int CODE_UNKNOWN = 0;

    private int code;

    public PMException(int code) {
        this.code = code;
    }

    public PMException(String s, int code) {
        super(s);
        this.code = code;
    }

    public PMException(String s, Throwable throwable, int code) {
        super(s, throwable);
        this.code = code;
    }

    public PMException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public PMException(String s, Throwable throwable, boolean b, boolean b1, int code) {
        super(s, throwable, b, b1);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
