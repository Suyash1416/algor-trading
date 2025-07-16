package com.botplus.algotrade.engine;


import org.apache.poi.ss.usermodel.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StockDataParser {

    private static final Map<String, Integer> columnCache = new HashMap<>();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Reads a single row of OHLCV data from the Excel sheet.
     */
    public static StockDataRow readRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        
        if (row == null) 
        	{
        	System.out.println("Row is null");
        	return null;
        	}

        try {
            LocalDate date = row.getCell(findColumnIndex(sheet, "Date"))
                    .getLocalDateTimeCellValue().toLocalDate();
            String code = row.getCell(findColumnIndex(sheet, "SYMBOL")).getStringCellValue();
            double open = parseDouble(row.getCell(findColumnIndex(sheet, "OPEN")));
            double high = parseDouble(row.getCell(findColumnIndex(sheet, "HIGH")));
            double low = parseDouble(row.getCell(findColumnIndex(sheet, "LOW")));
            double close = parseDouble(row.getCell(findColumnIndex(sheet, "CLOSE")));
            double volume = parseDouble(row.getCell(findColumnIndex(sheet, "NET_TRDQTY")));

            return new StockDataRow(date, open, high, low, close, volume, code, rowIndex);
        } catch (Exception e) {
        	e.printStackTrace();
            return null; // skip row if data is invalid
        }
    }

    /**
     * Writes a single indicator value to the correct column for the given row.
     * Creates header and column if it doesn't exist.
     */
    public static void writeIndicator(Sheet sheet, int rowIndex, String columnName, Double value) {
        Row header = sheet.getRow(0);
        if (header == null) {
            header = sheet.createRow(0);
        }

        Integer colIndex = columnCache.get(columnName);

        // If the column isn't cached, search header or create it
        if (colIndex == null) {
            colIndex = -1;
            for (Cell cell : header) {
                if (cell.getCellType() == CellType.STRING &&
                    cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                    colIndex = cell.getColumnIndex();
                    break;
                }
            }

            // Not found — create new column
            if (colIndex == -1) {
                colIndex = (int) (header.getLastCellNum() < 0 ? 0 : header.getLastCellNum());
                Cell newCell = header.createCell(colIndex);
                newCell.setCellValue(columnName);
            }

            columnCache.put(columnName, colIndex);
        }

        Row row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);

        Cell cell = row.createCell(colIndex);
        if (value != null && !value.isNaN()) {
            cell.setCellValue(value);
        }
    }


    private static double parseDouble(Cell cell) {
        if (cell == null) return 0.0;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String str = cell.getStringCellValue().replace(",", "").trim();
                return Double.parseDouble(str);
            }
        } catch (Exception e) {
            return 0.0;
        }
        return 0.0;
    }

    private static int findColumnIndex(Sheet sheet, String columnName) {
        Row header = sheet.getRow(0);
        if (header == null) throw new IllegalStateException("Header row is missing.");

        for (Cell cell : header) {
            if (cell.getCellType() == CellType.STRING &&
                cell.getStringCellValue().trim().equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        throw new IllegalArgumentException("Column not found: " + columnName);
    }
}
