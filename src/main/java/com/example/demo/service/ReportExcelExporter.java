package com.example.demo.service;

import com.example.demo.model.Body;
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
import java.util.Map;

public class ReportExcelExporter {
    public ByteArrayInputStream export(ReportData reportData) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet firstSheet = workbook.createSheet("new sheet");
            createHeaderRow(firstSheet, reportData);

            Body body = reportData.getBody();
            List<Column> columns = body.getColumns();
            List<Map<String, Object>> rows = body.getRows();

            createDataRows(firstSheet, columns, rows);

            return exportAsByteArrayInputStream(workbook);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createDataRows(Sheet firstSheet, List<Column> columns, List<Map<String, Object>> rows) {
        for (int i = 0; i < rows.size(); i++) {
            Row row = firstSheet.createRow(i + 1);
            for (int j = 0; j < columns.size(); j++) {
                row.createCell(j).setCellValue(rows.get(j).get(columns.get(j).getName()).toString());
            }
        }
//        Row row = firstSheet.createRow(1);
//        row.createCell(0).setCellValue(rows.get(0).get(columns.get(0).getName()).toString());
//        row.createCell(1).setCellValue(rows.get(1).get(columns.get(1).getName()).toString());
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
