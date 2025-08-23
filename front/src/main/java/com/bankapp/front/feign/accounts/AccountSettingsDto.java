package com.bankapp.front.feign.accounts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AccountSettingsDto {

    String name;
    LocalDate birthdate;
    List<String> accounts = new ArrayList<>();

}
