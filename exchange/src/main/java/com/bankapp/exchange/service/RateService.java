package com.bankapp.exchange.service;

import com.bankapp.exchange.config.ExchangeRateProperties;
import com.bankapp.exchange.dto.RateDto;
import com.bankapp.exchange.dto.UpdateRandomCurrencyDto;
import com.bankapp.exchange.entity.Rate;
import com.bankapp.exchange.repository.RateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class RateService {

    RateRepository rateRepository;
    ExchangeRateProperties exchangeRateProperties;
    ObjectMapper objectMapper = new ObjectMapper();

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
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1_00, multiplier = 2, maxDelay = 8_000),
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "exchange-generator", containerFactory = "customKafkaListenerContainerFactory")
    public void updateRandomCurrency(String randomCurrencyDto) {
        try {
            UpdateRandomCurrencyDto updateRandomCurrencyDto = objectMapper.readValue(randomCurrencyDto, UpdateRandomCurrencyDto.class);
            List<Rate> rates = rateRepository.findAllByBaseFalse();
            if (rates.isEmpty()) {
                return;
            }
            Random random = new Random();
            int randomCurrencyIndex = random.nextInt(rates.size());
            Rate selectedRate = rates.get(randomCurrencyIndex);
            int newValue = getRealisticValue(selectedRate.getName(), updateRandomCurrencyDto.getValue());
            selectedRate.setValue(newValue);
            rateRepository.save(selectedRate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void handleDltMessage(ConsumerRecord<?, ?> record) {
        System.out.println("Message landed in DLT: " + record.value());
    }

    private int getRealisticValue(String currencyName, Integer requestedValue) {
        ExchangeRateProperties.RateRange range = exchangeRateProperties.getRateRanges().get(currencyName);
        if (range == null) {
            return requestedValue != null ? requestedValue : 1;
        }
        int minValue = range.getMin();
        int maxValue = range.getMax();
        if (requestedValue != null && requestedValue >= minValue && requestedValue <= maxValue) {
            return requestedValue;
        }
        Random random = new Random();
        return minValue + random.nextInt(maxValue - minValue + 1);
    }

}