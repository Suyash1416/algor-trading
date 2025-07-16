package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class StandardDeviationIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public StandardDeviationIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "StDev-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        StandardDeviationIndicator stdev = new StandardDeviationIndicator(closePrice, period);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = stdev.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        StandardDeviationIndicator stdev = new StandardDeviationIndicator(closePrice, period);
        return stdev.getValue(endIndex).doubleValue();
    }
}
