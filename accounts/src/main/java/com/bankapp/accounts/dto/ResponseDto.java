package com.bankapp.accounts.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ResponseDto {

    boolean hasErrors;
    final List<String> errors = new ArrayList<>();

}
