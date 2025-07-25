package com.bankapp.front.controller;

import com.bankapp.front.feign.accounts.ResponseDto;
import com.bankapp.front.feign.cash.CashDto;
import com.bankapp.front.feign.cash.CashFeignClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/")
public class CashController {

    CashFeignClient cashFeignClient;

    @PostMapping("/user/cash")
    public String processCash(@ModelAttribute CashDto cashDto,
                              RedirectAttributes model,
                              Authentication authentication) {
        ResponseDto response = cashFeignClient.processCash(authentication.getName(), cashDto);
        if (response.isHasErrors()) {
            model.addFlashAttribute("cashErrors", response.getErrors());
        }
        return "redirect:/";
    }

}
