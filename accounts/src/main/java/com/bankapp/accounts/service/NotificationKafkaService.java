package com.bankapp.accounts.service;

import com.bankapp.accounts.feign.notifications.NotificationCreateDto;
import com.bankapp.accounts.messaging.MessageProducer;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotificationKafkaService {

    MessageProducer<Object> messageProducer;

    public void create(NotificationCreateDto createDto) {
        messageProducer.send("notifications", UUID.randomUUID().toString(), createDto);
    }

}