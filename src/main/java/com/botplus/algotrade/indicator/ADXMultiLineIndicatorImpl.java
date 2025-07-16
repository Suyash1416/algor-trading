package com.botplus.algotrade.indicator;


import com.botplus.algotrade.base.MultiLineIndicator;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.adx.PlusDIIndicator;
import org.ta4j.core.indicators.adx.MinusDIIndicator;

import java.util.HashMap;
import java.util.Map;

public class ADXMultiLineIndicatorImpl implements MultiLineIndicator {

    private final int period;

    public ADXMultiLineIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "ADX-" + period;
    }

    @Override
    public Map<String, Double[]> computeAll(BarSeries series) {
        int barCount = series.getBarCount();

        ADXIndicator adx = new ADXIndicator(series, period);
        PlusDIIndicator plusDI = new PlusDIIndicator(series, period);
        MinusDIIndicator minusDI = new MinusDIIndicator(series, period);

        Double[] adxValues = new Double[barCount];
        Double[] plusValues = new Double[barCount];
        Double[] minusValues = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            adxValues[i] = adx.getValue(i).doubleValue();
            plusValues[i] = plusDI.getValue(i).doubleValue();
            minusValues[i] = minusDI.getValue(i).doubleValue();
        }

        Map<String, Double[]> result = new HashMap<>();
        result.put("ADX", adxValues);
        result.put("+DI", plusValues);
        result.put("-DI", minusValues);

        return result;
    }

    @Override
    public Map<String, Double> calculateLatest(BarSeries series) {
        Map<String, Double> latest = new HashMap<>();

        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) 
        	return latest;

        ADXIndicator adx = new ADXIndicator(series, period);
        PlusDIIndicator plusDI = new PlusDIIndicator(series, period);
        MinusDIIndicator minusDI = new MinusDIIndicator(series, period);

        latest.put("ADX", adx.getValue(endIndex).doubleValue());
        latest.put("+DI", plusDI.getValue(endIndex).doubleValue());
        latest.put("-DI", minusDI.getValue(endIndex).doubleValue());

        return latest;
    }
}
