package com.delta.commerce.enums;

public enum HistoricDescriptionEnum {


    WORK_ORDER_CREATE("Ordem de serviço criada");


    private final String description;

    HistoricDescriptionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
