package com.botplus.algotrade.strategy;


import java.util.HashMap;
import java.util.Map;

import org.ta4j.core.BarSeries;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.indicator.*;

public class AllIndicatorsCalculator {

    /**
     * Accepts a BarSeries of 365 OHLCV bars and returns a map of
     * indicator name to the full array of indicator values.
     */
    public static Map<String, Double[]> calculateAllIndicators(BarSeries series) {
        Map<String, Double[]> resultMap = new HashMap<>();

        // ===== Trend Indicators =====
        TechnicalIndicator[] trendIndicators = {
            new SMAIndicatorImpl(50),
            new SMAIndicatorImpl(200),
            new EMAIndicatorImpl(14),
            new WMAIndicatorImpl(14),
            new IchimokuIndicatorImpl(9,26,52)
        };

        // ===== Momentum Indicators =====
        TechnicalIndicator[] momentumIndicators = {
            new RSIIndicatorImpl(14),
            new ROCIndicatorImpl(12),
            new CCIIndicatorImpl(20),
            new WilliamsRIndicatorImpl(14),
        };

        // ===== Volatility Indicators =====
        TechnicalIndicator[] volatilityIndicators = {
            new ATRIndicatorImpl(14),
            new BollingerBandWidthIndicatorImpl(20,1.3),
            new StandardDeviationIndicatorImpl(20)
            };

        // ===== Volume Indicators =====
        TechnicalIndicator[] volumeIndicators = {
            new OBVIndicatorImpl(),
            new CMFIndicatorImpl(20),
            new AccumulationDistributionIndicatorImpl()
        };

        TechnicalIndicator[][] categories = {
            trendIndicators,
            momentumIndicators,
            volatilityIndicators,
            volumeIndicators
        };

        for (TechnicalIndicator[] category : categories) {
            for (TechnicalIndicator indicator : category) {
                Double[] values = indicator.compute(series);
                resultMap.put(indicator.getName(), values);
            }
        }

        return resultMap;
    }
}
