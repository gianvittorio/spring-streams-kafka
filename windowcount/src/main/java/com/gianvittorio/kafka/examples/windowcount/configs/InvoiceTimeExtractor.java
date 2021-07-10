package com.gianvittorio.kafka.examples.windowcount.configs;

import com.gianvittorio.kafka.examples.windowcount.model.SimpleInvoice;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        SimpleInvoice invoice = (SimpleInvoice) consumerRecord.value();

        return (invoice.getCreatedTime() > 0) ? (invoice.getCreatedTime()) : (prevTime);
    }

    @Bean
    public TimestampExtractor timestampExtractor() {
        return new InvoiceTimeExtractor();
    }
}
