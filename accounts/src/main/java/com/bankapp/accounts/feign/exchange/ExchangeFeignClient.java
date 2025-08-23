package com.bankapp.accounts.feign.exchange;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "exchange", url = "${feign.exchange}")
public interface ExchangeFeignClient {

    @GetMapping("/api/rates")
    List<RateDto> fetchAll();

}
