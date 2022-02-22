package com.example.demo.model.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetInflowDetails8 {

    private String assetCode;
    private String assetName;
    private BigDecimal compositionRate;
    private BigDecimal currentMonthNAV;
    private BigDecimal previousMonthNAV;
    private BigDecimal exposure;
    private String groupAccount;

}
