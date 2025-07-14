package com.botplus.algotrade.utils;

import java.util.Map;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExcelExporter {

    public static void exportIndicatorsToExcel(Map<String, Double[]> indicatorDataMap) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Indicator Results");

        // Header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Index");

        int col = 1;
        for (String indicatorName : indicatorDataMap.keySet()) {
            header.createCell(col++).setCellValue(indicatorName);
        }

        int rowCount = indicatorDataMap.values().stream().findFirst().orElse(new Double[0]).length;

        // Data rows
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i);

            col = 1;
            for (Double[] values : indicatorDataMap.values()) {
                row.createCell(col++).setCellValue(values[i]);
            }
        }

        // Auto-size columns
        for (int i = 0; i <= indicatorDataMap.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // ✅ Generate timestamped filename in a fixed directory
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String folderPath = "output/Indicators/";
        new java.io.File(folderPath).mkdirs(); // create folder if not exist
        String filename = folderPath + "IndicatorResults_" + timestamp + ".xlsx";

        try (FileOutputStream fileOut = new FileOutputStream(filename)) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("✅ Excel file created: " + filename);
        } catch (Exception e) {
            System.err.println("❌ Failed to write Excel file: " + e.getMessage());
        }
    }
}
