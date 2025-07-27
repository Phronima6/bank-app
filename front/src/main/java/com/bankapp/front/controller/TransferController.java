package com.bankapp.front.controller;

import com.bankapp.front.feign.accounts.ResponseDto;
import com.bankapp.front.feign.transfer.TransferDto;
import com.bankapp.front.feign.transfer.TransferFeignClient;
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
public class TransferController {

    TransferFeignClient transferFeignClient;

    @PostMapping("/user/transfer")
    public String processCash(@ModelAttribute TransferDto transferDto,
                              RedirectAttributes model,
                              Authentication authentication) {
        ResponseDto response = transferFeignClient.processTransfer(authentication.getName(), transferDto);
        if (response.isHasErrors()) {
            model.addFlashAttribute("transferErrors", response.getErrors());
        }
        return "redirect:/";
    }

}
