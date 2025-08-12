package com.bankapp.exchangegenerator.service;

import com.bankapp.exchangegenerator.dto.UpdateRandomCurrencyDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class UpdateRandomCurrencyService {

    ExchangeGeneratorKafkaService exchangeGeneratorKafkaService;
    static Map<String, Integer> BASE_RATES = Map.of(
        "USD", 8000,
        "CNY", 1100
    );

    static double MAX_FLUCTUATION_PERCENT = 3.0;

    @Scheduled(fixedDelay = 2000)
    public void update() {
        UpdateRandomCurrencyDto updateRandomCurrencyDto = new UpdateRandomCurrencyDto();
        Random random = new Random();
        String[] currencies = BASE_RATES.keySet().toArray(new String[0]);
        String selectedCurrency = currencies[random.nextInt(currencies.length)];
        int baseRate = BASE_RATES.get(selectedCurrency);
        double fluctuationPercent = (random.nextDouble() - 0.5) * 2 * MAX_FLUCTUATION_PERCENT;
        int fluctuation = (int) (baseRate * fluctuationPercent / 100);
        int newRate = baseRate + fluctuation;
        int minRate = (int) (baseRate * 0.9);
        int maxRate = (int) (baseRate * 1.1);
        newRate = Math.max(minRate, Math.min(maxRate, newRate));
        updateRandomCurrencyDto.setValue(newRate);
        exchangeGeneratorKafkaService.updateRandomCurrency(updateRandomCurrencyDto);
    }

}