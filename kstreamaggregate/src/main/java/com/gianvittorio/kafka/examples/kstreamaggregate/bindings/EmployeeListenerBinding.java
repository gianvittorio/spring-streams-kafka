package com.gianvittorio.kafka.examples.kstreamaggregate.bindings;

import com.gianvittorio.kafka.examples.model.Employee;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface EmployeeListenerBinding {

    @Input("employee-input-channel")
    KStream<String, Employee> employeeInputStream();
}
