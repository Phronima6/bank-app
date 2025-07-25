package com.bankapp.notifications.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Getter
@Setter
@Table(name = "notifications", schema = "notifications")
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "source_id", nullable = false)
    Long sourceId;
    @Column(name = "login", nullable = false)
    String login;
    @Column(name = "message", nullable = false)
    String message;

    public Notification(Long sourceId, String login, String message) {
        this.sourceId = sourceId;
        this.login = login;
        this.message = message;
    }

}
