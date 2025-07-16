package com.botplus.algotrade.indicator;

import com.botplus.algotrade.base.MultiLineIndicator;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.num.Num;

import java.util.HashMap;
import java.util.Map;

public class StochasticOscillatorMultiLineIndicatorImpl implements MultiLineIndicator {

    private final int kPeriod;
    private final int dPeriod;

    public StochasticOscillatorMultiLineIndicatorImpl(int kPeriod, int dPeriod) {
        this.kPeriod = kPeriod;
        this.dPeriod = dPeriod;
    }

    @Override
    public String getName() {
        return "StochasticOscillator-" + kPeriod + "-" + dPeriod;
    }

    @Override
    public Map<String, Double[]> computeAll(BarSeries series) {
        int barCount = series.getBarCount();

        StochasticOscillatorKIndicator kIndicator = new StochasticOscillatorKIndicator(series, kPeriod);
        SMAIndicator dIndicator = new SMAIndicator(kIndicator, dPeriod); // Manually smooth %K

        Double[] kValues = new Double[barCount];
        Double[] dValues = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            Num k = kIndicator.getValue(i);
            Num d = dIndicator.getValue(i);
            kValues[i] = k.doubleValue();
            dValues[i] = d.doubleValue();
        }

        Map<String, Double[]> result = new HashMap<>();
        result.put("%K", kValues);
        result.put("%D", dValues);
        return result;
    }

    @Override
    public Map<String, Double> calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;

        StochasticOscillatorKIndicator kIndicator = new StochasticOscillatorKIndicator(series, kPeriod);
        SMAIndicator dIndicator = new SMAIndicator(kIndicator, dPeriod); // Manually smooth %K

        Map<String, Double> latest = new HashMap<>();
        latest.put("%K", kIndicator.getValue(endIndex).doubleValue());
        latest.put("%D", dIndicator.getValue(endIndex).doubleValue());
        return latest;
    }
}
