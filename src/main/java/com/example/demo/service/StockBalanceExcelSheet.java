package com.example.demo.service;

import com.example.demo.enums.CustomCellStyle;
import com.example.demo.model.Column;
import com.example.demo.model.complex.ClientDetails;
import com.example.demo.model.complex.ExcelDataWrapper;
import com.example.demo.model.complex.StockBalance;
import com.example.demo.model.complex.StockBalanceWrapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StockBalanceExcelSheet {

    void exportSecondSheet(StockBalanceWrapper stockBalanceWrapper, ExcelDataWrapper<ClientDetails> clientDetailsWrapper, Workbook workbook, Map<CustomCellStyle, CellStyle> styles) {
        Sheet sheet = workbook.createSheet("Stock Balance");

        List<Column> columns = stockBalanceWrapper.getColumns();
        List<StockBalance> rows = stockBalanceWrapper.getRows();

//        ClientDetails clientDetails = clientDetailsWrapper.getRows().get(0);
//
//        Row row = sheet.createRow(1);
//        CellUtil.createCell(row, 0, "ファンド");
//        CellUtil.createCell(row, 1, clientDetails.getClientName());
//
//        Row row2 = sheet.createRow(2);
//        CellUtil.createCell(row2, 0, "運用機関名称");
//        CellUtil.createCell(row2, 1, "ゴールドマン・サックス・アセット・マネジメント株式会社");
//
//        Row row3 = sheet.createRow(3);
//        CellUtil.createCell(row3, 0, "基準日");
//        CellUtil.createCell(row3, 1, clientDetails.getReportingMonth());

        final int STOCK_BALANCE_TABLE_HEADER_INDEX = 5;
        Row headerRow = sheet.createRow(STOCK_BALANCE_TABLE_HEADER_INDEX);
        final Map<Integer, String> stockBalanceHeaders = Map.of(
                0, "Code",
                1, "Company Name",
                2, "No of Shares",
                3, "Unit Price",
                4, "JPY Market Value",
                5, "Local Currency"
        );
        buildStockBalanceTableHeaders(headerRow, stockBalanceHeaders, styles.get(CustomCellStyle.CENTER_ALIGNED_WITH_BORDER));

        final int STOCK_BALANCE_TABLE_CONTENT_INDEX = 6;
        buildStockBalanceTableContent(styles, sheet, rows, STOCK_BALANCE_TABLE_CONTENT_INDEX);

        formatStockBalanceColumns(sheet);
    }

    private void buildStockBalanceTableContent(Map<CustomCellStyle, CellStyle> styles, Sheet sheet, List<StockBalance> rows, int STOCK_BALANCE_TABLE_CONTENT_INDEX) {
        for (int rowData = 0; rowData < rows.size(); rowData++) {
            Row row = sheet.createRow(STOCK_BALANCE_TABLE_CONTENT_INDEX + rowData);
            StockBalance stockBalance = rows.get(rowData);
            CellUtil.createCell(row, 0, stockBalance.getSecurityCd(), styles.get(CustomCellStyle.THIN_BLACK_BORDER));
            CellUtil.createCell(row, 1, stockBalance.getSecurityDescription(), styles.get(CustomCellStyle.THIN_BLACK_BORDER));
            CellUtil.createCell(row, 2, "", styles.get(CustomCellStyle.BLACK_BACKGROUND_WITH_BORDER));
            CellUtil.createCell(row, 3, "", styles.get(CustomCellStyle.BLACK_BACKGROUND_WITH_BORDER));
            CellUtil.createCell(row, 5, stockBalance.getTradeCcyCd(), styles.get(CustomCellStyle.THIN_BLACK_BORDER));

            CellStyle customCellStyle = styles.get(CustomCellStyle.THIN_BLACK_BORDER);
            customCellStyle.setDataFormat((short) 0x26);
            createCellWithDoubleValue(row, 4, stockBalance.getMarketValueBs().doubleValue(), customCellStyle);
        }
    }

    private void buildStockBalanceTableHeaders(Row row, Map<Integer, String> map, CellStyle cellStyle) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            CellUtil.createCell(row, entry.getKey(), entry.getValue(), cellStyle);
        }
    }

    private void formatStockBalanceColumns(Sheet sheet) {
        setColumnsWidth(sheet, 6);
        sheet.setColumnWidth(1, 256 * 50);
    }

    private void setColumnsWidth(Sheet sheet, int size) {
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
            sheet.setColumnWidth(columnIndex, 256 * 20);
        }
    }

    private Cell createCellWithDoubleValue(Row row, int column, double value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }

        return cell;
    }
}