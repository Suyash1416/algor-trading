package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.volume.AccumulationDistributionIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class AccumulationDistributionIndicatorImpl implements TechnicalIndicator {

    @Override
    public String getName() {
        return "ADLine";
    }

    @Override
    public Double[] compute(BarSeries series) {
        AccumulationDistributionIndicator ad = new AccumulationDistributionIndicator(series);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = ad.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < 1) return null;

        AccumulationDistributionIndicator ad = new AccumulationDistributionIndicator(series);
        return ad.getValue(endIndex).doubleValue();
    }
}
