package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class EMAIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public EMAIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "EMA-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator ema = new EMAIndicator(closePrice, period);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = ema.getValue(i).doubleValue();
        }
        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period - 1) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator ema = new EMAIndicator(closePrice, period);
        return ema.getValue(endIndex).doubleValue();
    }
}
