package com.delta.commerce.enums;

public enum HistoricDescriptionEnum {


    WORK_ORDER_CREATE("Ordem de serviço criada"),

    CUSTOMER_PRODUCT_CREATE("Produto do cliente cadastrado"),
    CUSTOMER_PRODUCT_UPDATE("Produto do cliente atualizado"),
    CUSTOMER_PRODUCT_DESACTIVED("Produto do cliente desativado"),

    PRODUCT_CREATE("Produto cadastrado"),
    PRODUCT_UPDATE("Produto atualizado"),
    PRODUCT_DESACTIVED("Produto desativado"),

    INVOICE_CREATE("Nota cadastrado"),
    INVOICE_UPDATE("Nota atualizada"),
    INVOICE_DESACTIVED("Nota desativado"),

    PROFILE_CREATE("Perfil cadastrado"),
    PROFILE_UPDATE("Perfil atualizada"),
    PROFILE_DESACTIVED("Perfil desativado"),
    PROFILE_DELETE("Perfil deletado"),

    CLIENT_CREATE("Cliente cadastrado"),
    CLIENT_UPDATE("Cliente atualizado"),
    CLIENT_DESACTIVED("Cliente desativado");


    private final String description;

    HistoricDescriptionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
