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
public class UserDto {

    String login;
    String password;
    String name;
    LocalDate birthdate;
    List<AccountDto> accounts = new ArrayList<>();

    public UserDto(String login, String password, String name, LocalDate birthdate) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
    }

}
