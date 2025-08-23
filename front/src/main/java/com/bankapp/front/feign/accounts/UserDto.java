package com.bankapp.front.feign.accounts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
