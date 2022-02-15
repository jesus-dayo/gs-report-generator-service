package com.example.demo.service;

import com.example.demo.model.Column;
import com.example.demo.model.ReportData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ReportExcelExporter {
    public ByteArrayInputStream export(ReportData reportData) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet firstSheet = workbook.createSheet("new sheet");
            createHeaderRow(firstSheet, reportData);

            return exportAsByteArrayInputStream(workbook);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ByteArrayInputStream exportAsByteArrayInputStream(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void createHeaderRow(Sheet sheet, ReportData reportData) {
        final int HEADER_ROW = 0;
        Row headerRow = sheet.createRow(HEADER_ROW);

        List<Column> columns = reportData.getBody().getColumns();
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(column.getName());
        }
    }
}
