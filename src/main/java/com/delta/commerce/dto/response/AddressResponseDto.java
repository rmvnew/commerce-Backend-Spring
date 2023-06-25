package com.delta.commerce.dto.response;

import lombok.Data;
@Data
public class AddressResponseDto {

    private String zipcode;
    private String state;
    private String city;
    private String district;
    private String street;
    private String homeNumber;
}
