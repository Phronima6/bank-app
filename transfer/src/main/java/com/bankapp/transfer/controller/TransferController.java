package com.bankapp.transfer.controller;

import com.bankapp.transfer.dto.ResponseDto;
import com.bankapp.transfer.dto.TransferDto;
import com.bankapp.transfer.service.TransferService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    TransferService transferService;

    @PostMapping("/{login}")
    public ResponseDto processTransfer(@PathVariable("login") String login, @RequestBody TransferDto transferDto) {
        return transferService.processTransfer(login, transferDto);
    }

}
