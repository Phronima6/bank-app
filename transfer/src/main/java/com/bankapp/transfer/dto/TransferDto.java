package com.bankapp.transfer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferDto {

    String fromCurrency;
    String toCurrency;
    String toLogin;
    Long value;
    Long convertedValue;

}
