package com.example.demo.model.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetOutflowDetails {

    private String assetCode;
    private String assetName;
    private int daysOutflow;
    private BigDecimal daysOutflowCalc;
    private String groupAccount;
    private BigDecimal outflow;

}
