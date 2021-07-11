package com.gianvittorio.kakfa.examples.otpvalidation.configs;

import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentConfirmation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfirmationTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        PaymentConfirmation confirmation = (PaymentConfirmation) consumerRecord.value();

        return (confirmation.getCreatedTime() > 0) ? (confirmation.getCreatedTime()) : (prevTime);
    }

    @Bean
    public TimestampExtractor confirmationTimeExtractor() {
        return new PaymentConfirmationTimeExtractor();
    }
}
