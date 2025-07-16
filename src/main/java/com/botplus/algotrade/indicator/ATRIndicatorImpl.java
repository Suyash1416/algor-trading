package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.AverageTrueRangeIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class ATRIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public ATRIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "ATR-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        AverageTrueRangeIndicator atr = new AverageTrueRangeIndicator(series, period);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = atr.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        AverageTrueRangeIndicator atr = new AverageTrueRangeIndicator(series, period);
        return atr.getValue(endIndex).doubleValue();
    }
}
