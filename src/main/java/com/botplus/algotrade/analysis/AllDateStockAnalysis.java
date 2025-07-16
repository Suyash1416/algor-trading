package com.botplus.algotrade.analysis;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ta4j.core.*;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.engine.StockDataParser;
import com.botplus.algotrade.engine.StockDataRow;
import com.botplus.algotrade.indicator.AccumulationDistributionIndicatorImpl;
import com.botplus.algotrade.indicator.BollingerBandWidthIndicatorImpl;
import com.botplus.algotrade.indicator.CCIIndicatorImpl;
import com.botplus.algotrade.indicator.CMFIndicatorImpl;
import com.botplus.algotrade.indicator.EMAIndicatorImpl;
import com.botplus.algotrade.indicator.IchimokuIndicatorImpl;
import com.botplus.algotrade.indicator.MACDIndicatorImpl;
import com.botplus.algotrade.indicator.NegativeDIIndicatorImpl;
import com.botplus.algotrade.indicator.OBVIndicatorImpl;
import com.botplus.algotrade.indicator.PositiveDIIndicatorImpl;
import com.botplus.algotrade.indicator.ROCIndicatorImpl;
import com.botplus.algotrade.indicator.RSIIndicatorImpl;
import com.botplus.algotrade.indicator.SMAIndicatorImpl;
import com.botplus.algotrade.indicator.StandardDeviationIndicatorImpl;
import com.botplus.algotrade.indicator.StochasticKIndicatorImpl;
import com.botplus.algotrade.indicator.WMAIndicatorImpl;
import com.botplus.algotrade.indicator.WilliamsRIndicatorImpl;

import java.io.*;
import java.time.Duration;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class AllDateStockAnalysis {

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

            // Prepare the indicators
            List<TechnicalIndicator> indicators = List.of(
                new SMAIndicatorImpl(7),
//              new SMAIndicatorImpl(10),
                new EMAIndicatorImpl(7),
                new WMAIndicatorImpl(5),
                new MACDIndicatorImpl(12,26),
 //              new ADXIndicatorImpl(14),
                new NegativeDIIndicatorImpl(14),
                new PositiveDIIndicatorImpl(14),
                new IchimokuIndicatorImpl(9,26,52),
                new RSIIndicatorImpl(7),
                new StochasticKIndicatorImpl(14),
                new CCIIndicatorImpl(20),
                new ROCIndicatorImpl(10),
                new WilliamsRIndicatorImpl(14),
                new BollingerBandWidthIndicatorImpl(20,2),
                new StandardDeviationIndicatorImpl(20),
                new OBVIndicatorImpl(),
                new CMFIndicatorImpl(20),
                new AccumulationDistributionIndicatorImpl()
            		);
            
           	BarSeries series = new BaseBarSeries("RowSeries");
            

            // Loop through each row starting from 1 (row 0 is header)
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                StockDataRow data = StockDataParser.readRow(sheet, rowIndex);
                
                if (data == null) 
	                {
					System.out.println("\n Data is null") ;
	                continue;
					}

                // Wrap current row in a single-bar TimeSeries
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
                
                System.out.print("\n Calculating Indicators for :: " + bar);

                for (TechnicalIndicator indicator : indicators) {


                    System.out.println("\nIndicator ::" + indicator.getName());
                    
                    Double value = indicator.calculateLatest(series);

					System.out.println(value);  // Output: 34.5,56.0,78.2
                    
                    StockDataParser.writeIndicator(sheet, data.getRowIndex(), indicator.getName(), value);

                }
            }

            // Save output Excel file
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                workbook.write(fos);
                System.out.println("✅ Indicator calculation complete. Saved to: " + outputFilePath);
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

	
}
