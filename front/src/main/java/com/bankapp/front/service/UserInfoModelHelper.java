package com.bankapp.front.service;

import com.bankapp.front.feign.accounts.AccountsFeignClient;
import com.bankapp.front.feign.accounts.UserDto;
import com.bankapp.front.feign.exchange.ExchangeFeignClient;
import com.bankapp.front.feign.exchange.RateDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UserInfoModelHelper {

    AccountsFeignClient accountsFeignClient;
    ExchangeFeignClient exchangeFeignClient;

    public void addUserInfoToModel(Model model, String login) {
        model.addAttribute("login", login);
        UserDto user = accountsFeignClient.fetchUserByLogin(login);
        List<RateDto> rates = exchangeFeignClient.fetchAll();
        model.addAttribute("name", user.getName());
        model.addAttribute("birthdate", user.getBirthdate());
        model.addAttribute("accounts", user.getAccounts());
        model.addAttribute("currency", rates);
        model.addAttribute("users", accountsFeignClient.fetchAllUsers(login));
    }

}
