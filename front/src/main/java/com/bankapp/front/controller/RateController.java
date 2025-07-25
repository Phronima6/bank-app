package com.bankapp.front.controller;

import com.bankapp.front.feign.exchange.ExchangeFeignClient;
import com.bankapp.front.feign.exchange.RateDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/rates")
public class RateController {

    ExchangeFeignClient exchangeFeignClient;

    @GetMapping
    public List<RateDto> fetchAll() {
        return exchangeFeignClient.fetchAll();
    }

}
