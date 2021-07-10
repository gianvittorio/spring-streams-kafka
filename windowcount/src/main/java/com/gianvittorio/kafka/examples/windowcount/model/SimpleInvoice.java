package com.gianvittorio.kafka.examples.windowcount.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SimpleInvoice {

    @JsonProperty("InvoiceNumber")
    private String invoiceNumber;

    @JsonProperty("CreatedTime")
    private Long createdTime;

    @JsonProperty("StoreID")
    private String storeId;

    @JsonProperty("TotalAmount")
    private Double totalAmount;
}
