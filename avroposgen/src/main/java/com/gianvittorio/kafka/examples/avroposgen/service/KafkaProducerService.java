package com.gianvittorio.kafka.examples.avroposgen.service;

import com.gianvittorio.kafka.examples.model.PosInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    @Value("${application.configs.topic.name}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, PosInvoice> kafkaTemplate;

    public void sendMessage(PosInvoice invoice) {
        log.info("Producing Invoice No: {}", invoice);

        kafkaTemplate.send(TOPIC_NAME, invoice.getStoreID(), invoice);
    }
}
