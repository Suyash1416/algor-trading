package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class RSIIndicatorImpl extends ConfigurableIndicator {
    public RSIIndicatorImpl() {}

    @Override
    public String getName() {
        return "RSI-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        RSIIndicator rsi = new RSIIndicator(new ClosePriceIndicator(series), period);
        Double[] result = new Double[series.getBarCount()];
        for (int i = 0; i < result.length; i++) {
            result[i] = rsi.getValue(i).doubleValue();
        }
        return result;
    }
}

