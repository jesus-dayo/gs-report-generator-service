package com.example.demo.service;

import com.example.demo.model.Body;
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
import java.util.Map;

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
        Sheet firstSheet = workbook.getSheetAt(0);
        verifySheetData(firstSheet);

        Body body = reportData.getBody();
        List<Column> columns = body.getColumns();
        List<Map<String, Object>> rows = body.getRows();

        final int HEADER_ROW = 0;
        Row headerRow = firstSheet.getRow(HEADER_ROW);
        assertNotNull(headerRow);

        verifyHeaderColumns(columns, headerRow);
        verifyDataForEachRows(columns, rows, firstSheet);
    }

    private void verifyDataForEachRows(List<Column> columns, List<Map<String, Object>> rows, Sheet sheet) {
        for (int i = 0; i < rows.size(); i++) {
            Row row = sheet.getRow(i + 1);
            Map<String, Object> rowRawData = rows.get(i);
            for (int j = 0; j < columns.size(); j++) {
                verifyRow(columns.get(j), rowRawData, row.getCell(j));
            }
        }
    }

    private void verifyRow(Column column, Map<String, Object> row, Cell cell) {
        if (column.getType().equalsIgnoreCase("double") || column.getType().equalsIgnoreCase("decimal")) {
            assertEquals(
                    Double.parseDouble(row.get(column.getName()).toString()),
                    Double.parseDouble(cell.getStringCellValue()),
                    "encounter error with column key " + column.getName()
            );
        } else {
            assertEquals(
                    row.get(column.getName()) == null ? "" : row.get(column.getName()),
                    cell.getStringCellValue(),
                    "encounter error with column key " + column.getName()
            );
        }
    }

    private void verifyHeaderColumns(List<Column> columns, Row headerRow) {
        for (int i = 0; i < columns.size(); i++) {
            verifyColumn(columns.get(i), headerRow.getCell(i));
        }
    }

    private void verifySheetData(Sheet sheet) {
        assertNotNull(sheet);
        assertEquals("new sheet", sheet.getSheetName());
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
