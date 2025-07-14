package com.botplus.algotrade.base;

import java.util.Map;

import org.ta4j.core.BarSeries;

public interface TechnicalIndicator {
    String getName();
    
    Double[] compute(BarSeries series);
        void configure(Map<String, Object> parameters);

}
