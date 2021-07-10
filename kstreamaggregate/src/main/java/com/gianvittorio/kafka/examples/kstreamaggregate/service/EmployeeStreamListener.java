package com.gianvittorio.kafka.examples.kstreamaggregate.service;

import com.gianvittorio.kafka.examples.kstreamaggregate.bindings.EmployeeListenerBinding;
import com.gianvittorio.kafka.examples.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding(EmployeeListenerBinding.class)
public class EmployeeStreamListener {

    private final RecordBuilder recordBuilder;

    @StreamListener("employee-input-channel")
    public void process(KStream<String, Employee> input) {

        input.peek((key, value) -> log.info("Key: {}, Value: {}", key, value))
                .groupBy((key, value) -> value.getDepartment())
                .aggregate(
                        recordBuilder::init,
                        (key, value, aggValue) -> recordBuilder.aggregate(value, aggValue)
                )
        .toStream()
        .foreach((key, value) -> log.info("Key: {}, Value: {}", key, value));
    }
}
