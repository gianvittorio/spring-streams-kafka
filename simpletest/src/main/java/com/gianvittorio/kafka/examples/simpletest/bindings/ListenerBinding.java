package com.gianvittorio.kafka.examples.simpletest.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface ListenerBinding {

    @Input("process-in-0")
    KStream<String, String> inputStream();

    @Input("process-out-0")
    KStream<String, String> outStream();
}
