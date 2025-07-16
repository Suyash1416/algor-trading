package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.keltner


import com.botplus.algotrade.base.TechnicalIndicator;

public class KeltnerChannelWidthIndicatorImpl implements TechnicalIndicator {

    private final int emaPeriod;
    private final int atrPeriod;
    private final double multiplier;

    public KeltnerChannelWidthIndicatorImpl(int emaPeriod, int atrPeriod, double multiplier) {
        this.emaPeriod = emaPeriod;
        this.atrPeriod = atrPeriod;
        this.multiplier = multiplier;
    }

    @Override
    public String getName() {
        return "KCWidth-" + emaPeriod + "-" + atrPeriod + "-" + multiplier;
    }

    @Override
    public Double[] compute(BarSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        EMAIndicator ema = new EMAIndicator(closePrice, emaPeriod);
        AverageTrueRangeIndicator atr = new AverageTrueRangeIndicator(series, atrPeriod);

        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            double width = 2 * multiplier * atr.getValue(i).doubleValue();
            result[i] = width;
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < Math.max(emaPeriod, atrPeriod)) return null;

        AverageTrueRangeIndicator atr = new AverageTrueRangeIndicator(series, atrPeriod);
        return 2 * multiplier * atr.getValue(endIndex).doubleValue();
    }
}

