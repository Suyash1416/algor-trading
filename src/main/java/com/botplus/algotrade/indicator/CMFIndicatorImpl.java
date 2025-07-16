package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.volume.ChaikinMoneyFlowIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class CMFIndicatorImpl implements TechnicalIndicator {

    private final int period;

    public CMFIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "CMF-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ChaikinMoneyFlowIndicator cmf = new ChaikinMoneyFlowIndicator(series, period);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = cmf.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        ChaikinMoneyFlowIndicator cmf = new ChaikinMoneyFlowIndicator(series, period);
        return cmf.getValue(endIndex).doubleValue();
    }
}
