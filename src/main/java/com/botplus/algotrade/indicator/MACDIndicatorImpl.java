package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class MACDIndicatorImpl implements TechnicalIndicator {

    private final int shortPeriod;
    private final int longPeriod;

    public MACDIndicatorImpl(int shortPeriod, int longPeriod) {
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
    }

    @Override
    public String getName() {
        return "MACD-" + shortPeriod + "-" + longPeriod;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        MACDIndicator macd = new MACDIndicator(closePrice, shortPeriod, longPeriod);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = macd.getValue(i).doubleValue();
        }
        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < longPeriod) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        MACDIndicator macd = new MACDIndicator(closePrice, shortPeriod, longPeriod);
        return macd.getValue(endIndex).doubleValue();
    }
}
