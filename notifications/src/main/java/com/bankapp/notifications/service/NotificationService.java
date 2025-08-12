package com.bankapp.notifications.service;

import com.bankapp.notifications.dto.NotificationCreateDto;
import com.bankapp.notifications.entity.Notification;
import com.bankapp.notifications.repository.NotificationsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class NotificationService {

    NotificationsRepository notificationsRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1_00, multiplier = 2, maxDelay = 8_000),
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlt",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "notifications", containerFactory = "customKafkaListenerContainerFactory")
    public void create(String createDto) {
        try {
            NotificationCreateDto notification = objectMapper.readValue(createDto, NotificationCreateDto.class);
            notificationsRepository.insertOnConflictDoNothing(new Notification(
                    notification.getSourceId(), notification.getLogin(), notification.getMessage()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<String> fetchAll(String login) {
        PageRequest page = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "id"));
        return notificationsRepository.findAllByLogin(login, page).map(Notification::getMessage).toList();
    }

    @DltHandler
    public void handleDltMessage(ConsumerRecord<?, ?> record) {
        System.out.println("Message landed in DLT: " + record.value());
    }

}