package com.example.demo.model.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDetails {

    @JsonProperty("DateMonth")
    private String dateMonth;

    @JsonProperty("DateYear")
    private String dateYear;

    @JsonProperty("TerminationDate")
    private String terminationDate;

    private String clientName;
    private String clientCode1;
    private String assetManagerCode1;
    private String reportingMonth;

}
