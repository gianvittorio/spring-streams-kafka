package com.gianvittorio.kakfa.examples.otpvalidation.bindings;

import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentConfirmation;
import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentRequest;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface OTPListenerBinding {

    @Input("payment-request-channel")
    KStream<String, PaymentRequest> requestInputStream();

    @Input("payment-confirmation-channel")
    KStream<String, PaymentConfirmation> confirmationInputStream();
}
