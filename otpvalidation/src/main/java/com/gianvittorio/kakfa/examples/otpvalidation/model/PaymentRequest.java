package com.gianvittorio.kakfa.examples.otpvalidation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @JsonProperty("TransactionID")
    private String transactionID;

    @JsonProperty("CreatedTime")
    private Long createdTime;

    @JsonProperty("SourceAccountID")
    private String sourceAccountID;

    @JsonProperty("TargetAccountID")
    private String targetAccountID;

    @JsonProperty("Amount")
    private Double amount;

    @JsonProperty("OTP")
    private String OTP;
}
