package com.botplus.algotrade.base;

import java.util.Map;

import org.ta4j.core.BarSeries;




public interface TechnicalIndicator {

    /**
     * Returns the name of the indicator (e.g., "RSI-14", "SMA-5")
     */
    String getName();

    /**
     * Computes the entire indicator values for a given BarSeries.
     */
    Double[] compute(BarSeries series);

    /**
     * Computes the latest (most recent) indicator value from the series.
     * Shortcut for: compute(series)[series.getBarCount() - 1]
     */
    default Double calculateLatest(BarSeries series) {
        Double[] values = compute(series);
        return values.length > 0 ? values[values.length - 1] : null;
    }

}
