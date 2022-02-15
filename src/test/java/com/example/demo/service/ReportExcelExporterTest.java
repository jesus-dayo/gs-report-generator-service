package com.example.demo.service;

import com.example.demo.model.Column;
import com.example.demo.model.ReportData;
import com.example.demo.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportExcelExporterTest {

    @Test
    public void exportUserData_whenValidData_returnsExcelFile() throws IOException {
        ReportExcelExporter reportExcelExporter = new ReportExcelExporter();
        String content = FileUtil.readResourceFileAsString("data/Simple.json");
        ObjectMapper objectMapper = new ObjectMapper();
        ReportData reportData = objectMapper.readValue(content, ReportData.class);
        assertNotNull(reportData);
        System.out.println("reportData = " + reportData);

        ByteArrayInputStream data = reportExcelExporter.export(reportData);
        assertNotNull(data);

        Workbook workbook = new XSSFWorkbook(data);
        Sheet firstSheet = workbook.getSheetAt(0);

        assertNotNull(firstSheet);
        assertEquals("new sheet", firstSheet.getSheetName());

        final int HEADER_ROW = 0;
        Row headerRow = firstSheet.getRow(HEADER_ROW);
        assertNotNull(headerRow);
        Cell cell0 = headerRow.getCell(0);
        List<Column> headers = reportData.getBody().getColumns();
        Column column = headers.get(0);
        assertEquals(column.getName(), cell0.getStringCellValue());
    }

}
