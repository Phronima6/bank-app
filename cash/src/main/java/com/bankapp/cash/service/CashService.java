package com.bankapp.cash.service;

import com.bankapp.cash.dto.CashDto;
import com.bankapp.cash.dto.ResponseDto;
import com.bankapp.cash.feign.accounts.AccountsFeignClient;
import com.bankapp.cash.feign.blocker.BlockerFeignClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class CashService {

    AccountsFeignClient accountsFeignClient;
    BlockerFeignClient blockerFeignClient;

    public ResponseDto processCash(String login, CashDto cashDto) {
        boolean suspicious = blockerFeignClient.isSuspicious();
        if (!suspicious) {
            return accountsFeignClient.processCash(login, cashDto);
        } else {
            ResponseDto response = new ResponseDto();
            response.setHasErrors(true);
            response.getErrors().add("Подозрительная операция.");
            return response;
        }
    }

}
