package com.delta.commerce.enums;

public enum InvoiceTypeEnum {

    INPUT("INPUT"),
    OUTPUT("OUTPUT");


    private final String name;

    InvoiceTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
