package com.gianvittorio.kakfa.examples.otpvalidation.services;

import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentConfirmation;
import com.gianvittorio.kakfa.examples.otpvalidation.model.PaymentRequest;
import com.gianvittorio.kakfa.examples.otpvalidation.model.TransactionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecordBuilder {

    public TransactionStatus getTramsactionStatus(PaymentRequest request, PaymentConfirmation confirmation) {
        String status = "Failure";

        if (request.getOTP().equals(confirmation.getOTP())) {
            status = "Success";
        }

        TransactionStatus transactionStatus = TransactionStatus.builder()
                .transactionID(request.getTransactionID())
                .status(status)
                .build();

        return transactionStatus;
    }
}
