package com.gianvittorio.kafka.examples.exactlyoncefanout.binding;

import com.gianvittorio.kafka.examples.avroposgen.model.PosInvoice;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface PosListenerBinding {

    @Input("pos-input-channel")
    KStream<String, PosInvoice> posInputStream();
}
