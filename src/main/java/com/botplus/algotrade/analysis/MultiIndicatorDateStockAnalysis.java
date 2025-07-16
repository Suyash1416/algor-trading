package com.botplus.algotrade.analysis;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ta4j.core.*;

import com.botplus.algotrade.base.MultiLineIndicator;
import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.engine.StockDataParser;
import com.botplus.algotrade.engine.StockDataRow;
import com.botplus.algotrade.indicator.*;

import java.io.*;
import java.time.Duration;
import java.time.ZoneId;
import java.util.*;

public class MultiIndicatorDateStockAnalysis {

    public static void main(String[] args) {
        String inputFilePath = "stocks/ADANIENTData.xlsx";
        String sheetName = "Daily";
        String outputFilePath = "analysis/ADANIENTData_Analyzed.xlsx";

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("❌ Sheet '" + sheetName + "' not found.");
                return;
            }

            // Prepare both single and multiline indicators
            List<Object> indicators = List.of(
                new SMAIndicatorImpl(7),
                new EMAIndicatorImpl(7),
                new WMAIndicatorImpl(5),
              //  new MACDIndicatorImpl(12, 26),
                new PositiveDIIndicatorImpl(14),
                new NegativeDIIndicatorImpl(14),
                new IchimokuIndicatorImpl(9, 26, 52),
                new RSIIndicatorImpl(7),
                new StochasticKIndicatorImpl(14),
                new CCIIndicatorImpl(20),
                new ROCIndicatorImpl(10),
                new WilliamsRIndicatorImpl(14),
           //     new BollingerBandWidthIndicatorImpl(20, 2),
                new StandardDeviationIndicatorImpl(20),
                new OBVIndicatorImpl(),
                new CMFIndicatorImpl(20),
                new AccumulationDistributionIndicatorImpl(),
                new ADXMultiLineIndicatorImpl(14),
                new MACDMultiLineIndicatorImpl(12, 26, 9),
                new IchimokuMultiLineIndicatorImpl(9, 26, 52),
                new BollingerBandsMultiLineIndicatorImpl(20,2.0),
                new StochasticOscillatorMultiLineIndicatorImpl(14,3)

            );

            BarSeries series = new BaseBarSeries("RowSeries");

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                StockDataRow data = StockDataParser.readRow(sheet, rowIndex);
                if (data == null) {
                    System.out.println("\n⚠️ Skipping row " + rowIndex + " due to null data.");
                    continue;
                }

                Bar bar = new BaseBar(
                    Duration.ofDays(1),
                    data.getDate().atStartOfDay(ZoneId.systemDefault()),
                    data.getOpen(),
                    data.getHigh(),
                    data.getLow(),
                    data.getClose(),
                    data.getVolume()
                );
                series.addBar(bar);

                System.out.print("\n📊 Calculating Indicators for: " + data.getDate());

                for (Object indicator : indicators) {
                    if (indicator instanceof TechnicalIndicator singleLine) {
                        String name = singleLine.getName();
                        Double value = singleLine.calculateLatest(series);
                        System.out.println(" → " + name + ": " + value);
                        StockDataParser.writeIndicator(sheet, data.getRowIndex(), name, value);

                    } else if (indicator instanceof MultiLineIndicator multiLine) {
                        Map<String, Double> values = multiLine.calculateLatest(series);

                        if (values != null) {
                            for (Map.Entry<String, Double> entry : values.entrySet()) {
                                String name = multiLine.getName() + "-" + entry.getKey();
                                System.out.println(" → " + name + ": " + entry.getValue());
                                StockDataParser.writeIndicator(sheet, data.getRowIndex(), name, entry.getValue());
                            }
                        } else {
                            System.out.println(" ⚠️ " + multiLine.getName() + " skipped (insufficient data)");
                        }
                    }
                }
            }

            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                workbook.write(fos);
                System.out.println("\n✅ Indicator calculation complete. Saved to: " + outputFilePath);
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
