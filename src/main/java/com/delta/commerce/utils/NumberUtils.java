package com.delta.commerce.utils;

import com.delta.commerce.exception.CustomException;
import com.delta.commerce.exception.ErrorCustom;
import org.springframework.http.HttpStatus;

public class NumberUtils {


    private static NumberUtils numberUtils;

    public static NumberUtils getInstance(){
        if(numberUtils == null){
            numberUtils = new NumberUtils();
        }
        return numberUtils;
    }


    public String getInvoiceValidNumber(String invoiceNumber) {

        if (invoiceNumber.length() >= 1) {

            while (invoiceNumber.length() < 10) {
                invoiceNumber = "0" + invoiceNumber;
            }
        } else {
            throw  new CustomException(ErrorCustom.INVOICE_NUMBER_INVALID);
        }

        return invoiceNumber;
    }

}
