package com.example.demo.service;

import com.example.demo.model.ReportData;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ReportService {
    public ByteArrayInputStream exportUserData(ReportData reportData) {
        return new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8));
    }
}
