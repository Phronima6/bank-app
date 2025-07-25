package com.bankapp.transfer.service;

import com.bankapp.transfer.dto.TransferDto;
import com.bankapp.transfer.dto.ResponseDto;
import com.bankapp.transfer.feign.accounts.AccountsFeignClient;
import com.bankapp.transfer.feign.blocker.BlockerFeignClient;
import com.bankapp.transfer.feign.exchange.ExchangeFeignClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TransferService {

    AccountsFeignClient accountsFeignClient;
    ExchangeFeignClient exchangeFeignClient;
    BlockerFeignClient blockerFeignClient;

    public ResponseDto processTransfer(String login, TransferDto transferDto) {
        boolean suspicious = blockerFeignClient.isSuspicious();
        if (!suspicious) {
            Long convertedValue = exchangeFeignClient.convert(transferDto.getFromCurrency(), transferDto.getToCurrency(), transferDto.getValue() * 100);
            transferDto.setConvertedValue(convertedValue);
            return accountsFeignClient.processTransfer(login, transferDto);
        } else {
            ResponseDto response = new ResponseDto();
            response.setHasErrors(true);
            response.getErrors().add("Подозрительная операция.");
            return response;
        }
    }

}
