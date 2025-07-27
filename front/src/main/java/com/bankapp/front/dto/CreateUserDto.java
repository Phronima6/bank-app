package com.bankapp.front.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class CreateUserDto {

    String login;
    String password;
    String confirmPassword;
    String name;
    LocalDate birthdate;

}
