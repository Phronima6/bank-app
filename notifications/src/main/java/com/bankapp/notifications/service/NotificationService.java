package com.bankapp.notifications.service;

import com.bankapp.notifications.dto.NotificationCreateDto;
import com.bankapp.notifications.entity.Notification;
import com.bankapp.notifications.repository.NotificationsRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class NotificationService {

    NotificationsRepository notificationsRepository;

    @Transactional
    public void create(NotificationCreateDto createDto) {
        notificationsRepository.insertOnConflictDoNothing(new Notification(
                createDto.getSourceId(), createDto.getLogin(), createDto.getMessage()
        ));
    }

    @Transactional(readOnly = true)
    public List<String> fetchAll(String login) {
        PageRequest page = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "id"));
        return notificationsRepository.findAllByLogin(login, page).map(Notification::getMessage).toList();
    }

}
