package com.botplus.algotrade.engine;


import com.botplus.algotrade.base.*;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;

public class ExcelStockDataProvider implements StockDataProvider {

    private final String filePath;
    private final String sheetName;

    public ExcelStockDataProvider(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
    }

    @Override
    public List<StockDataRow> loadStockData() {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheet(sheetName);
            return StockDataParser.parse(sheet);
        } catch (Exception e) {
            throw new RuntimeException("Error loading Excel file", e);
        }
    }
}
