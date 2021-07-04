package com.gianvittorio.kafka.examples.kafkaproducer.web.controller;

import com.gianvittorio.kafka.examples.kafkaproducer.service.KafkaProducerService;
import com.gianvittorio.kafka.examples.kafkaproducer.web.model.IncomingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaMessageRestController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping(path = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String sendMessageToKafka(@RequestBody IncomingMessage incomingMessage) {

        kafkaProducerService.sendMessage(incomingMessage.getTopic(), incomingMessage.getKey(), incomingMessage.getValue());

        return String.format("Success - Message for key %s is sent to Kafka Topic: %s", incomingMessage.getKey(), incomingMessage.getTopic());
    }
}
