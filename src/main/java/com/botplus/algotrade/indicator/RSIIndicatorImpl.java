package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class RSIIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public RSIIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "RSI-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        RSIIndicator rsi = new RSIIndicator(closePrice, period);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = rsi.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        RSIIndicator rsi = new RSIIndicator(closePrice, period);
        return rsi.getValue(endIndex).doubleValue();
    }
}
