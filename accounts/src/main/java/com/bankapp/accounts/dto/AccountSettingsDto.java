package com.bankapp.accounts.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AccountSettingsDto {

    String name;
    LocalDate birthdate;
    List<String> accounts = new ArrayList<>();

}
