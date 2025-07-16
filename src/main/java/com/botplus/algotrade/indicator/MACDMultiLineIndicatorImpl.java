
package com.botplus.algotrade.indicator;

import com.botplus.algotrade.base.MultiLineIndicator;
import com.botplus.algotrade.base.TechnicalIndicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.util.HashMap;
import java.util.Map;

public class MACDMultiLineIndicatorImpl implements MultiLineIndicator {

    private final int shortPeriod;
    private final int longPeriod;
    private final int signalPeriod;

    public MACDMultiLineIndicatorImpl(int shortPeriod, int longPeriod, int signalPeriod) {
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
    }

    @Override
    public String getName() {
        return "MACD-" + shortPeriod + "-" + longPeriod + "-" + signalPeriod;
    }

    @Override
    public Map<String, Double[]> computeAll(BarSeries series) {
        int barCount = series.getBarCount();

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        MACDIndicator macd = new MACDIndicator(closePrice, shortPeriod, longPeriod);
        EMAIndicator signal = new EMAIndicator(macd, signalPeriod);

        Double[] macdLine = new Double[barCount];
        Double[] signalLine = new Double[barCount];
        Double[] histogram = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            macdLine[i] = macd.getValue(i).doubleValue();
            signalLine[i] = signal.getValue(i).doubleValue();
            histogram[i] = macdLine[i] - signalLine[i];
        }

        Map<String, Double[]> result = new HashMap<>();
        result.put("MACD", macdLine);
        result.put("Signal", signalLine);
        result.put("Histogram", histogram);
        return result;
    }

    @Override
    public Map<String, Double> calculateLatest(BarSeries series) {
        Map<String, Double> latest = new HashMap<>();

        int endIndex = series.getBarCount() - 1;
        if (endIndex < longPeriod + signalPeriod - 1) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        MACDIndicator macd = new MACDIndicator(closePrice, shortPeriod, longPeriod);
        EMAIndicator signal = new EMAIndicator(macd, signalPeriod);

        double macdValue = macd.getValue(endIndex).doubleValue();
        double signalValue = signal.getValue(endIndex).doubleValue();
        double histogram = macdValue - signalValue;

        latest.put("MACD", macdValue);
        latest.put("Signal", signalValue);
        latest.put("Histogram", histogram);
        return latest;
    }
}
