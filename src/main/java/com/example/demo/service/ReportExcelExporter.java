package com.example.demo.service;

import com.example.demo.model.ReportData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportExcelExporter {
    public ByteArrayInputStream export(ReportData reportData) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet firstSheet = workbook.createSheet("new sheet");
            createHeaderRow(firstSheet);

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

    private void createHeaderRow(Sheet firstSheet) {
        final int HEADER_ROW = 0;
        Row headerRow = firstSheet.createRow(HEADER_ROW);
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("positionDate");
    }
}
