package com.bankapp.front.feign.cash;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class CashDto {

    String currency;
    Long value;
    String action;

}
