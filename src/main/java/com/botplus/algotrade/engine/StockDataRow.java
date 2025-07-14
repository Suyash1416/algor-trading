package com.botplus.algotrade.engine;


import java.time.LocalDate;

public class StockDataRow {

    private final LocalDate date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;
    private final String stockCode;
    private final int rowIndex; // Excel row index for writing back

    public StockDataRow(LocalDate date, double open, double high, double low,
                        double close, double volume, String stockCode, int rowIndex) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.stockCode = stockCode;
        this.rowIndex = rowIndex;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public String getStockCode() {
        return stockCode;
    }

    public int getRowIndex() {
        return rowIndex;
    }
}
