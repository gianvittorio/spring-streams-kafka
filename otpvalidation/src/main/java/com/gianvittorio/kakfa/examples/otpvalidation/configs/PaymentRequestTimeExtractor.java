package com.gianvittorio.kakfa.examples.otpvalidation.configs;

import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRequestTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        PaymentRequest request = (PaymentRequest) consumerRecord.value();

        return (request.getCreatedTime() > 0) ? (request.getCreatedTime()) : (prevTime);
    }

    @Bean
    public TimestampExtractor requestTimeExtractor() {
        return new PaymentRequestTimeExtractor();
    }
}
