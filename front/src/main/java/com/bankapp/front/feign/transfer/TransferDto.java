package com.bankapp.front.feign.transfer;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class TransferDto {

    String fromCurrency;
    String toCurrency;
    String toLogin;
    Long value;

}
