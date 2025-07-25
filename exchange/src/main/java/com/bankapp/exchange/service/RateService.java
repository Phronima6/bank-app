package com.bankapp.exchange.service;

import com.bankapp.exchange.dto.RateDto;
import com.bankapp.exchange.dto.UpdateRandomCurrencyDto;
import com.bankapp.exchange.entity.Rate;
import com.bankapp.exchange.repository.RateRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Random;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class RateService {

    RateRepository rateRepository;
    static Map<String, int[]> REALISTIC_RANGES = Map.of(
        "USD", new int[]{7500, 8500},
        "CNY", new int[]{1000, 1100}
    );

    @Transactional(readOnly = true)
    public List<RateDto> fetchAll() {
        return rateRepository.findAll(Sort.by("id")).stream().map(currency -> {
            RateDto rateDto = new RateDto();
            rateDto.setId(currency.getId());
            rateDto.setTitle(currency.getTitle());
            rateDto.setName(currency.getName());
            rateDto.setValue(currency.getValue());
            return rateDto;
        }).toList();
    }

    @Transactional
    public void updateRandomCurrency(UpdateRandomCurrencyDto randomCurrencyDto) {
        List<Rate> rates = rateRepository.findAllByBaseFalse();
        if (rates.isEmpty()) {
            return;
        }
        Random random = new Random();
        int randomCurrencyIndex = random.nextInt(rates.size());
        Rate selectedRate = rates.get(randomCurrencyIndex);
        int newValue = getRealisticValue(selectedRate.getName(), randomCurrencyDto.getValue());
        selectedRate.setValue(newValue);
        rateRepository.save(selectedRate);
    }

    private int getRealisticValue(String currencyName, Integer requestedValue) {
        int[] range = REALISTIC_RANGES.get(currencyName);
        if (range == null) {
            return requestedValue != null ? requestedValue : 1;
        }
        int minValue = range[0];
        int maxValue = range[1];
        if (requestedValue != null && requestedValue >= minValue && requestedValue <= maxValue) {
            return requestedValue;
        }
        Random random = new Random();
        return minValue + random.nextInt(maxValue - minValue + 1);
    }

}