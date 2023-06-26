package com.delta.commerce.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCustom {

    USER_EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 100, "E-mail Already exists!"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 101, "User Already exists!"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 102, "User not found!"),
    DISABLED_USER(HttpStatus.BAD_REQUEST, 103, "Disabled user!"),
    USER_UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, 104, "User update not allowed!"),
    ROLE_NOT_EXISTS(HttpStatus.NOT_FOUND, 151, "Role not exists!"),
    OK(HttpStatus.OK, 200, "Success"),
    CREATED(HttpStatus.CREATED, 201, "Created"),
    PROFILE_ALREADY_EXISTS(HttpStatus.CONFLICT, 300, "Profile Already exists!"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Not Found"),
    PRODUCT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 500, "Product Already exists!"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 600, "Category not found"),
    CLIENT_ALREADY_EXISTS(HttpStatus.CONFLICT, 700, "Client already exists!"),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, 701, "Client not found"),
    DOCUMENT_COMPANY_INVALID(HttpStatus.BAD_REQUEST, 1001, "Document company invalid"),
    DOCUMENT_CLIENT_INVALID(HttpStatus.BAD_REQUEST, 1002, "Document client invalid");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorCustom(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
