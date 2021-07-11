package com.gianvittorio.kakfa.examples.otpvalidation.services;

import com.gianvittorio.kakfa.examples.otpvalidation.bindings.OTPListenerBinding;
import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentConfirmation;
import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Service
@Slf4j
@EnableBinding(OTPListenerBinding.class)
@RequiredArgsConstructor
public class OTPValidationService {

    private final RecordBuilder recordBuilder;

    @StreamListener
    public void process(
            @Input("payment-request-channel") KStream<String, PaymentRequest> request,
            @Input("payment-confirmation-channel") KStream<String, PaymentConfirmation> confirmation
    ) {

        request.foreach(
                (key, value) -> log.info(
                        "Request Key: {}; Created Time: {}",
                        key,
                        Instant.ofEpochMilli(value.getCreatedTime()).atOffset(ZoneOffset.UTC)
                )
        );

        confirmation.foreach(
                (key, value) -> log.info(
                        "Confirmation Key: {}; Created Time: {}",
                        key,
                        Instant.ofEpochMilli(value.getCreatedTime()).atOffset(ZoneOffset.UTC)
                )
        );

        request.join(
                confirmation,
                recordBuilder::getTramsactionStatus,
                JoinWindows.of(Duration.ofMinutes(5)),
                StreamJoined.with(
                        Serdes.String(),
                        new JsonSerde<>(PaymentRequest.class),
                        new JsonSerde<>(PaymentConfirmation.class)
                )
        )
                .foreach((key, value) -> log.info("TransactionID: {}; Status: {}", key, value.getStatus()));
    }
}
