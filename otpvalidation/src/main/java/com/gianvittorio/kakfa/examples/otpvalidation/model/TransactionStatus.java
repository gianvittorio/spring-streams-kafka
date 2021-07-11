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
public class TransactionStatus {

    @JsonProperty("TransactionID")
    private String transactionID;

    @JsonProperty("Status")
    private String status;
}
