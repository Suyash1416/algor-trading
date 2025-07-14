package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class EMAIndicatorImpl extends ConfigurableIndicator {
    public EMAIndicatorImpl() {}

    @Override
    public String getName() {
        return "EMA-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        EMAIndicator ema = new EMAIndicator(new ClosePriceIndicator(series), period);
        Double[] result = new Double[series.getBarCount()];
        for (int i = 0; i < result.length; i++) {
            result[i] = ema.getValue(i).doubleValue();
        }
        return result;
    }
}

