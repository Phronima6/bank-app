package com.bankapp.exchange.controller;

import com.bankapp.exchange.service.ExchangeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    ExchangeService exchangeService;

    @GetMapping
    public Long convert(@RequestParam("from") String from,
                        @RequestParam("to") String to,
                        @RequestParam("value") Long value) {
        return exchangeService.convert(from, to, value);
    }

}
