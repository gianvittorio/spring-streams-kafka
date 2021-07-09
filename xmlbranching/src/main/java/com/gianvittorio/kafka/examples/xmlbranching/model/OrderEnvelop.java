package com.gianvittorio.kafka.examples.xmlbranching.model;

import com.gianvittorio.kafka.examples.model.Order;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class OrderEnvelop {

    private String xmlOrderKey;
    private String xmlOrderValue;

    private String orderTag;
    private Order validOrder;
}
