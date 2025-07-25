package com.bankapp.transfer.feign.accounts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto {

    boolean hasErrors;
    final List<String> errors = new ArrayList<>();

}
