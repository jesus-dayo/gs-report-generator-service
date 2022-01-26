package com.example.demo.service;

import com.example.demo.kafka.UserData;
import com.example.demo.kafka.UserReport;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class UserExcelExporter {

    public ByteArrayInputStream export(UserReport userReport) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("UserReport");

            createHeader(workbook, sheet);

//            Cell cell = row.createCell(0);
//            cell.setCellValue("First Name");
//            cell.setCellStyle(headerCellStyle);
//
//            cell = row.createCell(1);
//            cell.setCellValue("Last Name");
//            cell.setCellStyle(headerCellStyle);
//
//            cell = row.createCell(2);
//            cell.setCellValue("Email");
//            cell.setCellStyle(headerCellStyle);
//
//            cell = row.createCell(3);
//            cell.setCellValue("Phone Number");
//            cell.setCellStyle(headerCellStyle);
//
//            cell = row.createCell(4);
//            cell.setCellValue("Address");
//            cell.setCellStyle(headerCellStyle);

            // Creating data rows for each contact
//            for(int i = 0; i < contacts.size(); i++) {
//                Row dataRow = sheet.createRow(i + 1);
//                dataRow.createCell(0).setCellValue(contacts.get(i).getFirstName());
//                dataRow.createCell(1).setCellValue(contacts.get(i).getLastName());
//                dataRow.createCell(2).setCellValue(contacts.get(i).getEmail());
//                dataRow.createCell(3).setCellValue(contacts.get(i).getPhoneNumber());
//                dataRow.createCell(4).setCellValue(contacts.get(i).getAddress());
//            }

            createBody(userReport, sheet);

            // Making size of column auto resize to fit with data
            sheet.autoSizeColumn(0);
//            sheet.autoSizeColumn(1);
//            sheet.autoSizeColumn(2);
//            sheet.autoSizeColumn(3);
//            sheet.autoSizeColumn(4);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            log.error("Error during export Excel file", ex);
            return null;
        }
    }

    private void createBody(UserReport userReport, Sheet sheet) {
        List<UserData> data = userReport.getData();
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(data.get(i).getTransactionId());
        }
    }

    private void createHeader(Workbook workbook, Sheet sheet) {
        Row headerRow = sheet.createRow(0);

        // Define header cell style
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Creating header cells
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Transaction Id");
        cell.setCellStyle(headerCellStyle);
        Font newFont = cell.getSheet().getWorkbook().createFont();
        newFont.setBold(true);
        newFont.setFontHeightInPoints((short) 20);

        CellUtil.setFont(cell, newFont);
    }

}
