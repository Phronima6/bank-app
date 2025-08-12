package com.bankapp.accounts.service;

import com.bankapp.accounts.entity.NotificationsOutbox;
import com.bankapp.accounts.feign.notifications.NotificationCreateDto;
import com.bankapp.accounts.repository.NotificationsOutboxRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class NotificationsOutboxService {

    static Logger log = LoggerFactory.getLogger(NotificationsOutboxService.class);
    NotificationsOutboxRepository notificationsOutboxRepository;
    NotificationKafkaService notificationKafkaService;

    @Transactional
    public void createNotification(String login, String message) {
        NotificationsOutbox notification = new NotificationsOutbox();
        notification.setLogin(login);
        notification.setMessage(message);
        notificationsOutboxRepository.save(notification);
    }

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void sendNotification() {
        try {
            notificationsOutboxRepository.findAllByReceivedFalse().forEach(notification -> {
                NotificationCreateDto dto = new NotificationCreateDto();
                dto.setSourceId(notification.getId());
                dto.setLogin(notification.getLogin());
                dto.setMessage(notification.getMessage());
                notificationKafkaService.create(dto);
                notification.setReceived(true);
                notificationsOutboxRepository.save(notification);
            });
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

}