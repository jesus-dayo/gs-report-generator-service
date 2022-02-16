package com.example.demo.service;

import com.example.demo.model.ReportData;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final ReportExcelExporter reportExcelExporter;

    public ByteArrayInputStream exportUserData(ReportData reportData) throws JsonProcessingException {
        return null;
    }

}
