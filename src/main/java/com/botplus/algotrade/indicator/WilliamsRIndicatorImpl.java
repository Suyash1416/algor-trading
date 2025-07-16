package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.WilliamsRIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class WilliamsRIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public WilliamsRIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "W%R-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        WilliamsRIndicator wpr = new WilliamsRIndicator(series, period);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = wpr.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        WilliamsRIndicator wpr = new WilliamsRIndicator(series, period);
        return wpr.getValue(endIndex).doubleValue();
    }
}
