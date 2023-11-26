package com.example;

public enum SubjectTypeEnum {
    AVIA("AVIA"),
    HOTEL("HOTEL");

    private final String type;

    SubjectTypeEnum(String type){
        this.type = type;
    }

    public String get() {
        return type;
    }
}
