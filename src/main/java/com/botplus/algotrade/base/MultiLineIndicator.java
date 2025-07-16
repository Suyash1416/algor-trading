package com.botplus.algotrade.base;


import org.ta4j.core.BarSeries;

import java.util.Map;

public interface MultiLineIndicator {
    /**
     * Returns the name of the indicator.
     */
    String getName();

    /**
     * Computes all relevant output lines for the given series.
     */
    Map<String, Double[]> computeAll(BarSeries series);

    /**
     * Returns the latest value of each line.
     */
    Map<String, Double> calculateLatest(BarSeries series);
}
