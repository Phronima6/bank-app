package com.bankapp.front.controller;

import com.bankapp.front.feign.accounts.AccountsFeignClient;
import com.bankapp.front.service.UserInfoModelHelper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/")
public class MainController {

    AccountsFeignClient accountsFeignClient;
    UserInfoModelHelper userInfoModelHelper;

    @GetMapping()
    public String main(Model model, Authentication authentication) {
        userInfoModelHelper.addUserInfoToModel(model, authentication.getName());
        return "main";
    }

}
