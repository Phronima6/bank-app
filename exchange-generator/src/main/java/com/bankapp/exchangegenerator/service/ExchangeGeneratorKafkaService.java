package com.bankapp.exchangegenerator.service;

import com.bankapp.exchangegenerator.dto.UpdateRandomCurrencyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class ExchangeGeneratorKafkaService {

    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    void updateRandomCurrency(UpdateRandomCurrencyDto createDto) {
        try {
            kafkaTemplate.send(
                    "exchange-generator",
                    UUID.randomUUID().toString(),
                    objectMapper.writeValueAsString(createDto)
            ).get();
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}