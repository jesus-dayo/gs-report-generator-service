package com.example.demo.service;

import com.example.demo.model.Body;
import com.example.demo.model.Column;
import com.example.demo.model.ReportData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

            createDataRows(workbook, firstSheet, columns, rows);
            return exportAsByteArrayInputStream(workbook);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createDataRows(Workbook workbook, Sheet firstSheet, List<Column> columns, List<Map<String, Object>> rows) {
        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            Row row = createNewDataRow(firstSheet, rowIndex + 1);
            createRowCellData(workbook, columns, rows.get(rowIndex), row);
        }
    }

    private void createRowCellData(Workbook workbook, List<Column> columns, Map<String, Object> rowRawData, Row row) {
        for (int i = 0; i < columns.size(); i++) {
            Cell cell = row.createCell(i);
            Column column = columns.get(i);
            Object dataValue = rowRawData.get(column.getName());
            String type = column.getType();
            if (type.equalsIgnoreCase("double") || type.equalsIgnoreCase("decimal")) {
                if (dataValue == null) {
                    continue;
                }

                BigDecimal value = new BigDecimal(dataValue.toString());
                if (value.doubleValue() == 0) {
                    cell.setCellValue("");
                    continue;
                }

                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setDataFormat((short) 0x27);

                cell.setCellValue(value.doubleValue());
                cell.setCellStyle(cellStyle);

            } else {
                String value = dataValue == null ? "" : dataValue.toString();
                cell.setCellValue(value);
            }
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

        String outputFileName = "hello1.xls";
        try {
            IOUtils.copy(new ByteArrayInputStream(outputStream.toByteArray()), new FileOutputStream(outputFileName));
        } catch (IOException e) {
            log.error("fail to save to file", e);
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
