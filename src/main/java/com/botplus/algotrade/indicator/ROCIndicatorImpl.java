package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.ROCIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class ROCIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public ROCIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "ROC-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        ROCIndicator roc = new ROCIndicator(closePrice, period);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = roc.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        ROCIndicator roc = new ROCIndicator(closePrice, period);
        return roc.getValue(endIndex).doubleValue();
    }
}
