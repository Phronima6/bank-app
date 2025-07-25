package com.bankapp.notifications.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class NotificationCreateDto {

    Long sourceId;
    String login;
    String message;

}
