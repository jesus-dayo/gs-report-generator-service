package com.example.demo.service;

import com.example.demo.model.Body;
import com.example.demo.model.Column;
import com.example.demo.model.ReportData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReportExcelExporter {

    public ByteArrayInputStream export(ReportData reportData) {
        log.debug("start exporting excel data");
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
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            Row row = createNewDataRow(firstSheet, rowIndex + 1);
            createRowCellData(columns, rows, row);
        }
    }

    private void createRowCellData(List<Column> columns, List<Map<String, Object>> rows, Row row) {
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i);
            String rowKey = columns.get(i).getName();
            cell.setCellValue(rows.get(i).get(rowKey).toString());
        }
    }

    private Row createNewDataRow(Sheet sheet, int index) {
        return sheet.createRow(index);
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

    private ByteArrayInputStream exportAsByteArrayInputStream(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
