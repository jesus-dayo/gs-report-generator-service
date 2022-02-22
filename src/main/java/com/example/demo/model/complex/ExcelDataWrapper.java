package com.example.demo.model.complex;

import com.example.demo.model.Column;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelDataWrapper <T> {

    private List<Column> columns;
    private List<T> rows;

}
