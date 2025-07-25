package com.bankapp.notifications.controller;

import com.bankapp.notifications.dto.NotificationCreateDto;
import com.bankapp.notifications.service.NotificationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    NotificationService notificationService;

    @PostMapping
    public void create(@RequestBody NotificationCreateDto createDto) {
        notificationService.create(createDto);
    }

    @GetMapping("/{login}")
    public List<String> fetchAll(@PathVariable("login") String login) {
        return notificationService.fetchAll(login);
    }

}
