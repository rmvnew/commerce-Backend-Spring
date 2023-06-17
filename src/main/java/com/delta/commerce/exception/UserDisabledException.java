package com.delta.commerce.exception;

import org.springframework.security.authentication.DisabledException;

public class UserDisabledException extends DisabledException {
    private ErrorCustom errorCustom;

    public UserDisabledException(ErrorCustom errorCustom) {
        super(errorCustom.getMessage());
        this.errorCustom = errorCustom;
    }

    public ErrorCustom getErrorCustom() {
        return this.errorCustom;
    }
}

