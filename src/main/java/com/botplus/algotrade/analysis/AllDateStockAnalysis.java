package com.botplus.algotrade.analysis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.ZoneId;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.engine.StockDataParser;
import com.botplus.algotrade.engine.StockDataRow;
import com.botplus.algotrade.indicator.SMAIndicatorImpl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ta4j.core.*;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class AllDateStockAnalysis {

    public static void main(String[] args) {
        String inputFilePath = "stocks/ADANIENTData.xlsx";
        String sheetName = "Daily";

        // Step 1: Create backup
        createTimestampedBackup(inputFilePath);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("❌ Sheet '" + sheetName + "' not found.");
                return;
            }

            // Step 2: Define indicators
            List<TechnicalIndicator> indicators = List.of(
                new SMAIndicatorImpl(5),
                new SMAIndicatorImpl(10)
               
            );

            // Step 3: Row-wise processing
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                StockDataRow data = StockDataParser.readRow(sheet, rowIndex);
                if (data == null) continue;

                Bar bar = new BaseBar(
                    Duration.ofDays(1),
                    data.getDate().atStartOfDay(ZoneId.systemDefault()),
                    data.getOpen(),
                    data.getHigh(),
                    data.getLow(),
                    data.getClose(),
                    data.getVolume()
                );

                BarSeries series = new BaseBarSeries("RowSeries");
                series.addBar(bar);

                for (TechnicalIndicator indicator : indicators) {
                    Double[] values = indicator.compute(series);
                    if (values.length > 0) {
                        StockDataParser.writeIndicator(sheet, data.getRowIndex(), indicator.getName(), values[0]);
                    }
                }
            }

            // Step 4: Overwrite the original Excel file
            try (FileOutputStream fos = new FileOutputStream(inputFilePath)) {
                workbook.write(fos);
                System.out.println("✅ Indicators updated in: " + inputFilePath);
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTimestampedBackup(String inputFilePath) {
        try {
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                System.err.println("⚠️ Input file not found: " + inputFilePath);
                return;
            }

            String baseName = inputFile.getName();  // e.g., ADANIENTData.xlsx
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String backupDir = "backup/stocks/";

            Files.createDirectories(Paths.get(backupDir));

            String backupFilePath = backupDir + timestamp + "_" + baseName;
            Files.copy(inputFile.toPath(), Paths.get(backupFilePath), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("📁 Backup created: " + backupFilePath);

        } catch (IOException e) {
            System.err.println("⚠️ Failed to create backup: " + e.getMessage());
        }
    }
}
