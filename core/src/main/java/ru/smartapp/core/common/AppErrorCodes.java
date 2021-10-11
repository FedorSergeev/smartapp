package ru.smartapp.core.common;

public enum AppErrorCodes {
    ERROR(-1);

    private Long code;

    AppErrorCodes(int i) {
        code = (long) i;
    }

    public Long getCode() {
        return code;
    }
}
