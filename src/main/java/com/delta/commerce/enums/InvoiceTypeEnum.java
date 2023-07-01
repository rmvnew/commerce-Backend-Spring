package com.delta.commerce.enums;

public enum InvoiceTypeEnum {

    INPUT("input"),
    OUTPUT("output");


    private final String name;

    InvoiceTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
