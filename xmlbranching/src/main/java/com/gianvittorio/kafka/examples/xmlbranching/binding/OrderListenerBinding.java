package com.gianvittorio.kafka.examples.xmlbranching.binding;

import com.gianvittorio.kafka.examples.model.Order;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface OrderListenerBinding {

    @Input("xml-input-channel")
    KStream<String, String> xmlInputStream();

    @Output("india-orders-channel")
    KStream<String, Order> indiaOutputStream();

    @Output("abroad-orders-channel")
    KStream<String, Order> abroadOutputStream();

}
