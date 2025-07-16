package com.botplus.algotrade.strategy;

import org.ta4j.core.BarSeries;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import com.botplus.algotrade.engine.StockDataParser;
import com.botplus.algotrade.engine.StockDataRow;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class RunStrategyEngine {

    public static void main(String[] args) throws Exception {
        String stockSymbol = "ADANIENT";
        String inputFilePath = "stocks/" + stockSymbol + "Data.xlsx";
        String sheetName = "Daily";

        BarSeries series = new BaseBarSeries(stockSymbol);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("❌ Sheet '" + sheetName + "' not found.");
                return;
            }

            // Load bars using StockDataParser
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                StockDataRow row = StockDataParser.readRow(sheet, rowIndex);
                if (row == null) continue;

                Bar bar = new org.ta4j.core.BaseBar(
                    Duration.ofDays(1),
                    row.getDate().atStartOfDay(ZoneId.systemDefault()),
                    row.getOpen(),
                    row.getHigh(),
                    row.getLow(),
                    row.getClose(),
                    row.getVolume()
                );
                series.addBar(bar);
            }

            System.out.println("✅ Loaded " + series.getBarCount() + " bars for " + stockSymbol);

            // Calculate indicators
            Map<String, Double[]> indicators = AllIndicatorsCalculator.calculateAllIndicators(series);

            // Load strategies from config
            List<StrategyDefinition> strategies = StrategyLoader.loadStrategies("config/strategies.json");

            // Evaluate each strategy
            for (StrategyDefinition strategy : strategies) {
            	
            	System.out.println("\n\n\n===========================================================📊 Evaluation started :: Strategy: " + strategy.getStrategyName() );
                
            	boolean result = StrategyEngine.evaluateStrategy(strategy, indicators, series.getBarCount() );
                
                System.out.println("📊 Strategy: " + strategy.getStrategyName() + " → " + (result ? "✅ TRIGGERED" : "❌ Not Triggered"));
            
            }

        } catch (Exception e) {
            System.err.println("❌ Error loading or processing data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
