package com.botplus.algotrade.indicator;

import java.util.Map;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class SMAIndicatorImpl implements TechnicalIndicator {

    private int period;  // no longer final to allow configuration

    // ✅ No-arg constructor for dynamic config
    public SMAIndicatorImpl() {
        // period will be set using configure()
    }

    // ✅ Optional direct constructor for static use
    public SMAIndicatorImpl(int period) {
        this.period = period;
    }

    @Override
    public String getName() {
        return "SMA-" + period;
    }

    @Override
    public Double[] compute(BarSeries series) {
        if (series == null || series.isEmpty()) {
            throw new IllegalArgumentException("Bar series must not be null or empty.");
        }
        if (period <= 0) {
            throw new IllegalStateException("SMA period must be configured and > 0.");
        }

        SMAIndicator sma = new SMAIndicator(new ClosePriceIndicator(series), period);
        
        Double[] result = new Double[series.getBarCount()];
        
        for (int i = 0; i < series.getBarCount(); i++) {
            result[i] = sma.getValue(i).doubleValue();
        }
        
        return result;
    }

    
   
}
