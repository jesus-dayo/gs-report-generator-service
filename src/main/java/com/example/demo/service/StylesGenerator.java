package com.example.demo.service;

import com.example.demo.enums.CustomCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StylesGenerator {

    public Map<CustomCellStyle, CellStyle> prepareStyles(Workbook workbook) {
        Font boldArial = createBoldArialFont(workbook);
        CellStyle greyCenteredBoldArialWithBorderStyle = createGreyCenteredBoldArialWithBorderStyle(workbook, boldArial);

        return Map.of(
                CustomCellStyle.GREY_CENTERED_BOLD_ARIAL_WITH_BORDER, greyCenteredBoldArialWithBorderStyle
        );
    }

    private CellStyle createGreyCenteredBoldArialWithBorderStyle(Workbook workbook, Font boldArial) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(boldArial);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private Font createBoldArialFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        return font;
    }

}
