package com.gianvittorio.kakfa.examples.advertclicks.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdClick {

    @JsonProperty("InventoryID")
    private String inventoryID;

}
