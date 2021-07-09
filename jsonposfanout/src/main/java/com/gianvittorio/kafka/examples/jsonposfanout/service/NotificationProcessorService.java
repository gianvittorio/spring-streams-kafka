package com.gianvittorio.kafka.examples.jsonposfanout.service;

import com.gianvittorio.kafka.examples.jsonposfanout.binding.PosListenerBinding;
import com.gianvittorio.kafka.examples.jsonposfanout.model.Notification;
import com.gianvittorio.kafka.examples.jsonposfanout.model.PosInvoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(PosListenerBinding.class)
@RequiredArgsConstructor
public class NotificationProcessorService {

    private final RecordBuilder recordBuilder;

    @StreamListener("notification-input-channel")
    @SendTo("notification-output-channel")
    public KStream<String, Notification> process(KStream<String, PosInvoice> input) {

        KStream<String, Notification> notificationKStream = input.filter(
                (key, value) -> value.getCustomerType()
                        .equalsIgnoreCase("PRIME")
        )
                .mapValues(recordBuilder::getNotification);

        notificationKStream.foreach(
                (key, value) -> log.info("Notification: - Key: {}, Value: {}", key, value)
        );

        return notificationKStream;
    }
}
