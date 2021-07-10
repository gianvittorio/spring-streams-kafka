package com.gianvittorio.kafka.examples.rewards.binding;

import com.gianvittorio.kafka.examples.model.Notification;
import com.gianvittorio.kafka.examples.model.PosInvoice;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface PosListenerBinding {

    @Input("invoice-input-channel")
    KStream<String, PosInvoice> invoiceInputStream();

    @Output("notification-output-channel")
    KStream<String, Notification> notificationOutputStream();
}
