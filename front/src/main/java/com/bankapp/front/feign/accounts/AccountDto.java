package com.bankapp.front.feign.accounts;

import com.bankapp.front.feign.exchange.RateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AccountDto {

    boolean exists;
    Long balance;
    RateDto currency;

}
