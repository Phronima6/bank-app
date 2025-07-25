package com.bankapp.front.feign.accounts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UserDto {

    String login;
    String password;
    String name;
    LocalDate birthdate;
    List<AccountDto> accounts = new ArrayList<>();

}
