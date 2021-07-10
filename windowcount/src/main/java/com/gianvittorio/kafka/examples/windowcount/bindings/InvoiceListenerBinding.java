package com.gianvittorio.kafka.examples.windowcount.bindings;

import com.gianvittorio.kafka.examples.windowcount.model.SimpleInvoice;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface InvoiceListenerBinding {

    @Input("Invoice-input-channel")
    KStream<String, SimpleInvoice> invoiceInputStream();
}
