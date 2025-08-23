package com.bankapp.transfer.feign.accounts;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseDto {

    boolean hasErrors;
    final List<String> errors = new ArrayList<>();

}
