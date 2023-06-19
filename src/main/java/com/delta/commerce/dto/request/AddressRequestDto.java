package com.delta.commerce.dto.request;

import lombok.Data;

@Data
public class AddressRequestDto {

    private String zipcode;
    private String state;
    private String city;
    private String district;
    private String street;
    private String homeNumber;


}
