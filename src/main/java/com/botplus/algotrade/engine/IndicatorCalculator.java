package com.botplus.algotrade.engine;

import com.botplus.algotrade.base.*;
import org.ta4j.core.*;
import java.time.*;
import java.util.*;



public class IndicatorCalculator {

    /**
     * Converts a list of StockDataRow into a BarSeries (TimeSeries).
     */
    public static BarSeries convertToSeries(List<StockDataRow> rows) {
        BarSeries series = new BaseBarSeries("Stock Series");

        for (StockDataRow row : rows) {
            Bar bar = new BaseBar(
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

        return series;
    }

    /**
     * Applies all technical indicators to the given series and returns their results.
     */
    public static List<IndicatorResult> computeIndicators(BarSeries series, List<TechnicalIndicator> indicators) {
        List<IndicatorResult> results = new ArrayList<>();

        for (TechnicalIndicator indicator : indicators) {
            Double[] values = indicator.compute(series);
            
            results.add(new IndicatorResult(indicator.getName(), values));
        }

        return results;
    }
}
