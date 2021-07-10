package com.gianvittorio.kafka.examples.ktableaggregate.services;

import com.gianvittorio.kafka.examples.ktableaggregate.bindings.EmployeeListenerBinding;
import com.gianvittorio.kafka.examples.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(EmployeeListenerBinding.class)
@RequiredArgsConstructor
public class EmployeeStreamListener {

    private final RecordBuilder recordBuilder;

    @StreamListener("employee-input-channel")
    public void process(KStream<String, Employee> input) {

        input.map((key, value) -> KeyValue.pair(value.getId(), value))
                .peek((key, value) -> log.info("Key: {}, Value: {}"))
                .toTable()
                .groupBy((key, value) -> KeyValue.pair(value.getDepartment(), value))
                .aggregate(
                        recordBuilder::init,
                        (key, value, aggValue) -> recordBuilder.add(value, aggValue),
                        (key, value, aggValue) -> recordBuilder.subtract(value, aggValue)
                )
                .toStream()
                .foreach((key, value) -> log.info("Key: {}, Value: {}"));
    }
}
