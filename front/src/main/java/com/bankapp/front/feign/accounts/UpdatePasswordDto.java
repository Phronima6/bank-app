package com.bankapp.front.feign.accounts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class UpdatePasswordDto {

    String password;
    String confirmPassword;

}
