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

        final int HEADER_ROW = 0;
        Row headerRow = firstSheet.getRow(HEADER_ROW);
        assertNotNull(headerRow);

        verifyHeaderColumns(columns, headerRow);

        Row firstDataRow = firstSheet.getRow(1);
        assertNotNull(firstDataRow);

        Cell firstDataRowCell = firstDataRow.getCell(0);

        List<Map<String, Object>> rows = body.getRows();
        assertEquals(rows.get(0).get(columns.get(0).getName()), firstDataRowCell.getStringCellValue());

        Cell secondDataRowCell = firstDataRow.getCell(1);
        assertEquals(rows.get(1).get(columns.get(1).getName()), secondDataRowCell.getStringCellValue());
    }

    private void verifyHeaderColumns(List<Column> headers, Row headerRow) {
        verifyColumn(headers.get(0), headerRow.getCell(0));
        verifyColumn(headers.get(1), headerRow.getCell(1));
        verifyColumn(headers.get(2), headerRow.getCell(2));
        verifyColumn(headers.get(3), headerRow.getCell(3));
        verifyColumn(headers.get(4), headerRow.getCell(4));
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
