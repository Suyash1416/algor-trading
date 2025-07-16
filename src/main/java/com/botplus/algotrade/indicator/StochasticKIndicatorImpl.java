package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class StochasticKIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public StochasticKIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "StochK-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        StochasticOscillatorKIndicator stochK = new StochasticOscillatorKIndicator(series, period);
        int barCount = series.getBarCount();

        Double[] result = new Double[barCount];
        for (int i = 0; i < barCount; i++) {
            result[i] = stochK.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        StochasticOscillatorKIndicator stochK = new StochasticOscillatorKIndicator(series, period);
        return stochK.getValue(endIndex).doubleValue();
    }
}
