package com.bankapp.exchange.messaging;

import com.bankapp.exchange.dto.UpdateRandomCurrencyDto;
import com.bankapp.exchange.service.RateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExchangeKafkaListener {

    RateService rateService;
    ObjectMapper objectMapper;

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1_00, multiplier = 2, maxDelay = 8_000),
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "exchange-generator", containerFactory = "customKafkaListenerContainerFactory")
    public void consumeRandomCurrencyUpdate(String randomCurrencyDto) {
        try {
            UpdateRandomCurrencyDto updateRandomCurrencyDto = objectMapper.readValue(randomCurrencyDto, UpdateRandomCurrencyDto.class);
            rateService.applyRandomCurrencyUpdate(updateRandomCurrencyDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void handleDltMessage(ConsumerRecord<?, ?> record) {
        System.out.println("Message landed in DLT: " + record.value());
    }

}