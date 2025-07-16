package com.botplus.algotrade.indicator;


import com.botplus.algotrade.base.MultiLineIndicator;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;


import org.ta4j.core.indicators.helpers.TRIndicator;
import org.ta4j.core.indicators.keltner.KeltnerChannelMiddleIndicator;
import org.ta4j.core.indicators.keltner.KeltnerChannelUpperIndicator;
import org.ta4j.core.indicators.keltner.KeltnerChannelLowerIndicator;
import org.ta4j.core.num.Num;

import java.util.HashMap;
import java.util.Map;

public class KeltnerChannelMultiLineIndicatorImpl implements MultiLineIndicator {

    private final int emaPeriod;
    private final int atrPeriod;
    private final double multiplier;

    public KeltnerChannelMultiLineIndicatorImpl(int emaPeriod, int atrPeriod, double multiplier) {
        this.emaPeriod = emaPeriod;
        this.atrPeriod = atrPeriod;
        this.multiplier = multiplier;
    }

    @Override
    public String getName() {
        return "KeltnerChannel-" + emaPeriod + "-" + atrPeriod + "-" + multiplier;
    }

    @Override
    public Map<String, Double[]> computeAll(BarSeries series) {
        int barCount = series.getBarCount();

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator ema = new EMAIndicator(closePrice, emaPeriod);
        AverageTrueRangeIndicator atr = new AverageTrueRangeIndicator(series, atrPeriod);
        Num mult = series.numOf(multiplier);

        KeltnerChannelMiddleIndicator middle = new KeltnerChannelMiddleIndicator(ema);
        KeltnerChannelUpperIndicator upper = new KeltnerChannelUpperIndicator(ema, atr, mult);
        KeltnerChannelLowerIndicator lower = new KeltnerChannelLowerIndicator(ema, atr, mult);

        Double[] midVals = new Double[barCount];
        Double[] upperVals = new Double[barCount];
        Double[] lowerVals = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            midVals[i] = middle.getValue(i).doubleValue();
            upperVals[i] = upper.getValue(i).doubleValue();
            lowerVals[i] = lower.getValue(i).doubleValue();
        }

        Map<String, Double[]> result = new HashMap<>();
        result.put("Middle", midVals);
        result.put("Upper", upperVals);
        result.put("Lower", lowerVals);
        return result;
    }

    @Override
    public Map<String, Double> calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator ema = new EMAIndicator(closePrice, emaPeriod);
        AverageTrueRangeIndicator atr = new AverageTrueRangeIndicator(series, atrPeriod);
        Num mult = series.numOf(multiplier);

        
        KeltnerChannelMiddleIndicator middle = new KeltnerChannelMiddleIndicator(ema);
        KeltnerChannelUpperIndicator upper = new KeltnerChannelUpperIndicator(ema, atr, mult);
        KeltnerChannelLowerIndicator lower = new KeltnerChannelLowerIndicator(ema, atr, mult);

        
        Map<String, Double> latest = new HashMap<>();
        latest.put("Middle", middle.getValue(endIndex).doubleValue());
        latest.put("Upper", upper.getValue(endIndex).doubleValue());
        latest.put("Lower", lower.getValue(endIndex).doubleValue());
        return latest;
    }
}
