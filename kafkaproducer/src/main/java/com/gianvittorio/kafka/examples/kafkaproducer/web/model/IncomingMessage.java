package com.gianvittorio.kafka.examples.kafkaproducer.web.model;

import lombok.Data;

@Data
public class IncomingMessage {

    private String topic;
    private String key;
    private String value;
}
