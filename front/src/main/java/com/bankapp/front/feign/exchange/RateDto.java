package com.bankapp.front.feign.exchange;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class RateDto {

    Long id;
    String title;
    String name;
    Integer value;

}
