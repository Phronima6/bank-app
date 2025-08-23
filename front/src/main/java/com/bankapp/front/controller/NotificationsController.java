package com.bankapp.front.controller;

import com.bankapp.front.feign.notifications.NotificationsFeignClient;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/")
public class NotificationsController {

    NotificationsFeignClient notificationsFeignClient;

    @GetMapping(value = "/api/notifications", produces = "application/json")
    public List<String> fetchAll(Authentication authentication) {
        return notificationsFeignClient.fetchAll(authentication.getName());
    }

}
