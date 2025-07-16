package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.adx.MinusDIIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class NegativeDIIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public NegativeDIIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "NDI-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        MinusDIIndicator minusDI = new MinusDIIndicator(series, period);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = minusDI.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        MinusDIIndicator minusDI = new MinusDIIndicator(series, period);
        return minusDI.getValue(endIndex).doubleValue();
    }
}
