package com.bankapp.front.feign.cash;

import com.bankapp.front.feign.accounts.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cash", url = "${feign.cash}")
public interface CashFeignClient {

    @PostMapping("/api/cash/{login}")
    ResponseDto processCash(@PathVariable("login") String login, @RequestBody CashDto cashDto);

}
