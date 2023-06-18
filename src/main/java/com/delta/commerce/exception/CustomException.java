package com.delta.commerce.exception;

public class CustomException extends RuntimeException {
    private ErrorCustom errorCustom;

    public CustomException(ErrorCustom errorCustom) {
        System.out.println("Error >>"+errorCustom.getMessage());
        this.errorCustom = errorCustom;
    }

    public ErrorModel getStatus() {
        return new ErrorModel(
                errorCustom.getHttpStatus(),
                errorCustom.getCode(),
                errorCustom.getMessage());
    }

}
