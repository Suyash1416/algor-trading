package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.adx.PlusDIIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class PositiveDIIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public PositiveDIIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "PDI-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        PlusDIIndicator plusDI = new PlusDIIndicator(series, period);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = plusDI.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        PlusDIIndicator plusDI = new PlusDIIndicator(series, period);
        return plusDI.getValue(endIndex).doubleValue();
    }
}
