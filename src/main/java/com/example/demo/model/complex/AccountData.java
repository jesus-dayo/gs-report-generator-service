package com.example.demo.model.complex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountData {

    private AssetInflowDetails7 assetInflowDetails7;
    private AssetInflowDetails8 assetInflowDetails8;
    private AssetOutflowDetails assetOutflowDetails;

}
