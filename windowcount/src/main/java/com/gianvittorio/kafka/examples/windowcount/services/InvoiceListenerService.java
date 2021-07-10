package com.gianvittorio.kafka.examples.windowcount.services;

import com.gianvittorio.kafka.examples.windowcount.bindings.InvoiceListenerBinding;
import com.gianvittorio.kafka.examples.windowcount.model.SimpleInvoice;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Slf4j
@Service
@EnableBinding(InvoiceListenerBinding.class)
public class InvoiceListenerService {

    @StreamListener("invoice-input-channel")
    public void process(KStream<String, SimpleInvoice> input) {

        input.peek((k, v) -> log.info("Key = " + k + " Created Time = "
                + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
                .count()
                .toStream()
                .foreach((k, v) -> log.info(
                        "StoreID: " + k.key() +
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
