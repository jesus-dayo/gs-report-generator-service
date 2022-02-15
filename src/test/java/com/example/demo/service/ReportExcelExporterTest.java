package com.example.demo.service;

import com.example.demo.model.Column;
import com.example.demo.model.ReportData;
import com.example.demo.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        ReportData reportData = createReportData();
        ReportExcelExporter reportExcelExporter = new ReportExcelExporter();
        ByteArrayInputStream data = reportExcelExporter.export(reportData);
        assertNotNull(data);

        Workbook workbook = new XSSFWorkbook(data);
        Sheet firstSheet = verifySheetData(workbook);

        List<Column> headers = reportData.getBody().getColumns();

        final int HEADER_ROW = 0;
        Row headerRow = firstSheet.getRow(HEADER_ROW);
        assertNotNull(headerRow);

        verifyColumn(headers.get(0), headerRow.getCell(0));
        verifyColumn(headers.get(1), headerRow.getCell(1));
    }

    private Sheet verifySheetData(Workbook workbook) {
        Sheet firstSheet = workbook.getSheetAt(0);

        assertNotNull(firstSheet);
        assertEquals("new sheet", firstSheet.getSheetName());
        return firstSheet;
    }

    private ReportData createReportData() throws JsonProcessingException {
        String content = FileUtil.readResourceFileAsString("data/Simple.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(content, ReportData.class);
    }

    private void verifyColumn(Column column, Cell cell) {
        assertEquals(column.getName(), cell.getStringCellValue());
    }

}
