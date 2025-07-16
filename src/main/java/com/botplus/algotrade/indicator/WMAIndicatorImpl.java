package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.WMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class WMAIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public WMAIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "WMA-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        WMAIndicator wma = new WMAIndicator(closePrice, period);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = wma.getValue(i).doubleValue();
        }
        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period - 1) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        WMAIndicator wma = new WMAIndicator(closePrice, period);
        return wma.getValue(endIndex).doubleValue();
    }
}