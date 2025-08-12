package com.bankapp.accounts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bankapp.accounts.feign.notifications.NotificationCreateDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationKafkaService {

    KafkaTemplate<String, String> notificationsKafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    public void create(NotificationCreateDto createDto) {
        try {
            notificationsKafkaTemplate.send(
                    "notifications",
                    UUID.randomUUID().toString(),
                    objectMapper.writeValueAsString(createDto)
            ).get();
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}