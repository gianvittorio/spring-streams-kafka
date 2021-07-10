package com.gianvittorio.kakfa.examples.sessionwindow.services;

import com.gianvittorio.kakfa.examples.sessionwindow.bindings.ClickListenerBinding;
import lombok.extern.slf4j.Slf4j;
import models.UserClick;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Service
@Slf4j
@EnableBinding(ClickListenerBinding.class)
public class ClickListenerService {

    @StreamListener("click-input-channel")
    public void process(KStream<String, UserClick> input) {

        input.peek((k, v) -> log.info("Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                .groupByKey()
                .windowedBy(SessionWindows.with(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .foreach((k, v) -> log.info(
                        "UserID: " + k.key() +
                                " Window start: " +
                                Instant.ofEpochMilli(k.window().start())
                                        .atOffset(ZoneOffset.UTC) +
                                " Window end: " +
                                Instant.ofEpochMilli(k.window().end())
                                        .atOffset(ZoneOffset.UTC) +
                                " Count: " + v +
                                " Window#: " + k.window().hashCode()
                ));

    }
}
