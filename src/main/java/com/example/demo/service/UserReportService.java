package com.example.demo.service;

import com.example.demo.kafka.UserReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class UserReportService {

    public void generateReport(UserReport userReport) {
        log.info("generate report for {}", userReport);

        UserExcelExporter userExcelExporter = new UserExcelExporter();
        ByteArrayInputStream byteArrayInputStream = userExcelExporter.export(userReport);

        log.info("hure {}", byteArrayInputStream);
        String outputFileName = "hello1.xls";
        try {
            IOUtils.copy(byteArrayInputStream, new FileOutputStream(outputFileName));
        } catch (IOException e) {
            log.error("fail to save to file", e);
        }
    }

}
