package com.delta.commerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenModel {

    private String token;
    private String expiration;
    private String name;

}
