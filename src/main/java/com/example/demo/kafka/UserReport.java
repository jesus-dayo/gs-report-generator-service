package com.example.demo.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserReport {

    private String templateId;
    private String requestedBy;
    private List<UserData> data;

}
