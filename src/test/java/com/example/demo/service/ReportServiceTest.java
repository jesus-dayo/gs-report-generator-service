package com.example.demo.service;

import com.example.demo.model.ReportData;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportServiceTest {

//    @SneakyThrows
//    @Test
//    public void exportUserData_whenValidData_returnsExcelFile() {
//        ReportService reportService = new ReportService();
//        ByteArrayInputStream data = reportService.exportUserData(new ReportData());
//        assertNotNull(data);
//
//        HSSFWorkbook workbook = new HSSFWorkbook(data);
//        HSSFSheet firstSheet = workbook.getSheetAt(0);
//
//        assertNotNull(firstSheet);
//    }

}
