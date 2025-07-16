package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CCIIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class CCIIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public CCIIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "CCI-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        CCIIndicator cci = new CCIIndicator(series, period);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = cci.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        CCIIndicator cci = new CCIIndicator(series, period);
        return cci.getValue(endIndex).doubleValue();
    }
}
