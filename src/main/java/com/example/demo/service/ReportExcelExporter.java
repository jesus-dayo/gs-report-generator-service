package com.example.demo.service;

import com.example.demo.enums.CustomCellStyle;
import com.example.demo.model.Body;
import com.example.demo.model.Column;
import com.example.demo.model.ReportData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class ReportExcelExporter {

    private final StylesGenerator stylesGenerator;

    public ByteArrayInputStream export(ReportData reportData) {
        log.debug("start exporting excel data");
        try (Workbook workbook = new XSSFWorkbook()) {
            Map<CustomCellStyle, CellStyle> styles = stylesGenerator.prepareStyles(workbook);

            Sheet firstSheet = workbook.createSheet("new sheet");
            Body body = reportData.getBody();
            List<Column> columns = body.getColumns();
            List<Map<String, Object>> rows = body.getRows();

            setColumnsWidth(firstSheet, columns);
            createHeaderRow(firstSheet, columns, styles);


            createDataRows(workbook, firstSheet, columns, rows);
            return exportAsByteArrayInputStream(workbook);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setColumnsWidth(Sheet sheet, List<Column> columns) {
        for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
            sheet.setColumnWidth(columnIndex, 256 * 20);
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

    private void createHeaderRow(Sheet sheet, List<Column> columns, Map<CustomCellStyle, CellStyle> styles) {
        final int HEADER_ROW = 0;
        Row headerRow = sheet.createRow(HEADER_ROW);

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(column.getName());
            cell.setCellStyle(styles.get(CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER));
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
