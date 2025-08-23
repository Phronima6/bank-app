package com.bankapp.exchange.controller;

import com.bankapp.exchange.dto.RateDto;
import com.bankapp.exchange.service.RateService;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/rates")
public class RateController {

    RateService rateService;

    @GetMapping
    public List<RateDto> fetchAll() {
        return rateService.fetchAll();
    }

}