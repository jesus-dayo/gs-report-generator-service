package com.example.demo.model.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockBalance {

    @JsonProperty("market_value_bs")
    private BigDecimal marketValueBs;

    private String securityDescription;

    @JsonProperty("security_cd")
    private String securityCd;

    @JsonProperty("trade_ccy_cd")
    private String tradeCcyCd;

}
