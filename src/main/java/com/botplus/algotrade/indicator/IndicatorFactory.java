package com.botplus.algotrade.indicator;

import java.util.Map;

import com.botplus.algotrade.base.TechnicalIndicator;

public class IndicatorFactory {
    public static TechnicalIndicator create(String type, Map<String, Object> config) {
        TechnicalIndicator indicator;

        switch (type.toUpperCase()) {
            case "SMA":
                indicator = new SMAIndicatorImpl();
                break;
            case "EMA":
                indicator = new EMAIndicatorImpl();
                break;
            case "RSI":
                indicator = new RSIIndicatorImpl();
                break;
            default:
                throw new IllegalArgumentException("Unknown indicator type: " + type);
        }

        indicator.configure(config);
        return indicator;
    }
}
