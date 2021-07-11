package com.gianvittorio.kafka.examples.simpletest.services;

import com.gianvittorio.kafka.examples.simpletest.bindings.ListenerBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
@EnableBinding(ListenerBinding.class)
public class ListenerService {

    @StreamListener("process-in-0")
    @SendTo("process-out-0")
    public KStream<String, String> process(KStream<String, String> input) {
        input.foreach((key, value) -> log.info("Received Input: {}", value));

        return input.mapValues(value -> value.toUpperCase(Locale.ROOT));
    }
}
