package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.volume.OnBalanceVolumeIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class OBVIndicatorImpl implements TechnicalIndicator {

    @Override
    public String getName() {
        return "OBV";
    }

    @Override
    public Double[] compute(BarSeries series) {
        OnBalanceVolumeIndicator obv = new OnBalanceVolumeIndicator(series);
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            result[i] = obv.getValue(i).doubleValue();
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < 1) return null;

        OnBalanceVolumeIndicator obv = new OnBalanceVolumeIndicator(series);
        return obv.getValue(endIndex).doubleValue();
    }
}
