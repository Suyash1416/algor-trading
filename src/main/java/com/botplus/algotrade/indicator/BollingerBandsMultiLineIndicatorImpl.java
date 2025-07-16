package com.botplus.algotrade.indicator;

import com.botplus.algotrade.base.MultiLineIndicator;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsMiddleIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsLowerIndicator;
import org.ta4j.core.indicators.bollinger.BollingerBandsUpperIndicator;

import java.util.HashMap;
import java.util.Map;

public class BollingerBandsMultiLineIndicatorImpl implements MultiLineIndicator {

    private final int period;
    private final double multiplier;

    public BollingerBandsMultiLineIndicatorImpl(int period, double multiplier) {
        this.period = period;
        this.multiplier = multiplier;
    }

    @Override
    public String getName() {
        return "BollingerBands-" + period + "-" + multiplier;
    }

    @Override
    public Map<String, Double[]> computeAll(BarSeries series) {
        int barCount = series.getBarCount();
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma = new SMAIndicator(closePrice, period);
        StandardDeviationIndicator stdDev = new StandardDeviationIndicator(closePrice, period);

        BollingerBandsMiddleIndicator middle = new BollingerBandsMiddleIndicator(sma);
        BollingerBandsUpperIndicator upper = new BollingerBandsUpperIndicator(middle, stdDev);
        BollingerBandsLowerIndicator lower = new BollingerBandsLowerIndicator(middle, stdDev);

        Double[] upperBand = new Double[barCount];
        Double[] lowerBand = new Double[barCount];
        Double[] middleBand = new Double[barCount];
        Double[] width = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            upperBand[i] = upper.getValue(i).doubleValue();
            lowerBand[i] = lower.getValue(i).doubleValue();
            middleBand[i] = middle.getValue(i).doubleValue();
            width[i] = upperBand[i] - lowerBand[i];
        }

        Map<String, Double[]> result = new HashMap<>();
        result.put("UpperBand", upperBand);
        result.put("LowerBand", lowerBand);
        result.put("MiddleBand", middleBand);
        result.put("BandWidth", width);
        return result;
    }

    @Override
    public Map<String, Double> calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma = new SMAIndicator(closePrice, period);
        StandardDeviationIndicator stdDev = new StandardDeviationIndicator(closePrice, period);

        BollingerBandsMiddleIndicator middle = new BollingerBandsMiddleIndicator(sma);
        BollingerBandsUpperIndicator upper = new BollingerBandsUpperIndicator(middle, stdDev);
        BollingerBandsLowerIndicator lower = new BollingerBandsLowerIndicator(middle, stdDev);

        double upperVal = upper.getValue(endIndex).doubleValue();
        double lowerVal = lower.getValue(endIndex).doubleValue();
        double middleVal = middle.getValue(endIndex).doubleValue();
        double width = upperVal - lowerVal;

        Map<String, Double> latest = new HashMap<>();
        latest.put("UpperBand", upperVal);
        latest.put("LowerBand", lowerVal);
        latest.put("MiddleBand", middleVal);
        latest.put("BandWidth", width);
        return latest;
    }
}
