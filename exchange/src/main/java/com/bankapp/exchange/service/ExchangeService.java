package com.bankapp.exchange.service;

import com.bankapp.exchange.entity.Rate;
import com.bankapp.exchange.repository.RateRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ExchangeService {

    RateRepository rateRepository;

    @Transactional(readOnly = true)
    public Long convert(String from, String to, Long value) {
        List<Rate> rates = rateRepository.findAll();
        Rate fromRate = rates.stream().filter(r -> r.getName().equals(from)).findFirst().orElseThrow();
        Rate toRate = rates.stream().filter(r -> r.getName().equals(to)).findFirst().orElseThrow();
        if (fromRate.isBase() && toRate.isBase()) {
            return value;
        }
        return value / fromRate.getValue() * toRate.getValue();
    }

}
