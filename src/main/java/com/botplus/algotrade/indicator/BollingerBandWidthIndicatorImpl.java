package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class BollingerBandWidthIndicatorImpl implements TechnicalIndicator {

    private final int period;
    private final double multiplier;

    public BollingerBandWidthIndicatorImpl(int period, double multiplier) {
        this.period = period;
        this.multiplier = multiplier;
    }

    @Override
    public String getName() {
        return "BBWidth-" + period + "-" + multiplier;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma = new SMAIndicator(closePrice, period);
        StandardDeviationIndicator stdDev = new StandardDeviationIndicator(closePrice, period);

        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            if (i < period - 1) {
                result[i] = null; // Not enough data yet
            } else {
                double middle = sma.getValue(i).doubleValue();
                double deviation = stdDev.getValue(i).doubleValue();
                double upper = middle + multiplier * deviation;
                double lower = middle - multiplier * deviation;
                result[i] = upper - lower;
            }
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < period) return null;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma = new SMAIndicator(closePrice, period);
        StandardDeviationIndicator stdDev = new StandardDeviationIndicator(closePrice, period);

        double middle = sma.getValue(endIndex).doubleValue();
        double deviation = stdDev.getValue(endIndex).doubleValue();
        double upper = middle + multiplier * deviation;
        double lower = middle - multiplier * deviation;

        return upper - lower;
    }
}
