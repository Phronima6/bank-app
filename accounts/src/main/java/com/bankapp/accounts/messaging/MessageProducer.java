package com.bankapp.accounts.messaging;

public interface MessageProducer<T> {

    void send(String topic, String key, T message);

}