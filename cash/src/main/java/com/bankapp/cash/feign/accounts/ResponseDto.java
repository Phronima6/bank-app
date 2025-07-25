package com.bankapp.cash.feign.accounts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ResponseDto {

    boolean hasErrors;
    final List<String> errors = new ArrayList<>();

}
