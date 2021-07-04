package com.gianvittorio.kafka.examples.kafkaproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(final String topic, final String key, final String value) {

        log.info("Producing message: key: {}, value: {} to topic: {}", key, value, topic);

        kafkaTemplate.send(topic, key, value);
    }
}
