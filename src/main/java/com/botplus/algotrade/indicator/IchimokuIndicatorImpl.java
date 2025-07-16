package com.botplus.algotrade.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.ichimoku.IchimokuKijunSenIndicator;
import org.ta4j.core.indicators.ichimoku.IchimokuSenkouSpanAIndicator;
import org.ta4j.core.indicators.ichimoku.IchimokuSenkouSpanBIndicator;
import org.ta4j.core.indicators.ichimoku.IchimokuTenkanSenIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;

public class IchimokuIndicatorImpl implements TechnicalIndicator {

    private final int tenkanPeriod;
    private final int kijunPeriod;
    private final int senkouSpanBPeriod;

    public IchimokuIndicatorImpl(int tenkanPeriod, int kijunPeriod, int senkouSpanBPeriod) {
        this.tenkanPeriod = tenkanPeriod;
        this.kijunPeriod = kijunPeriod;
        this.senkouSpanBPeriod = senkouSpanBPeriod;
    }

    @Override
    public String getName() {
        return "Ichimoku-" + tenkanPeriod + "-" + kijunPeriod + "-" + senkouSpanBPeriod;
    }

    @Override
    public Double[] compute(BarSeries series) {
        int barCount = series.getBarCount();
        Double[] result = new Double[barCount];

        IchimokuTenkanSenIndicator tenkan = new IchimokuTenkanSenIndicator(series, tenkanPeriod);
        IchimokuKijunSenIndicator kijun = new IchimokuKijunSenIndicator(series, kijunPeriod);
        IchimokuSenkouSpanAIndicator spanA = new IchimokuSenkouSpanAIndicator(series, tenkanPeriod, kijunPeriod);
        IchimokuSenkouSpanBIndicator spanB = new IchimokuSenkouSpanBIndicator(series, senkouSpanBPeriod);

        // We will store a summary value: difference between Span A and Span B for signal strength
        for (int i = 0; i < barCount; i++) {
            double a = spanA.getValue(i).doubleValue();
            double b = spanB.getValue(i).doubleValue();
            result[i] = a - b;  // Positive => bullish cloud, Negative => bearish
        }

        return result;
    }

    @Override
    public Double calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;
        if (endIndex < Math.max(tenkanPeriod, Math.max(kijunPeriod, senkouSpanBPeriod))) return null;

        IchimokuTenkanSenIndicator tenkan = new IchimokuTenkanSenIndicator(series, tenkanPeriod);
        IchimokuKijunSenIndicator kijun = new IchimokuKijunSenIndicator(series, kijunPeriod);
        IchimokuSenkouSpanAIndicator spanA = new IchimokuSenkouSpanAIndicator(series, tenkanPeriod, kijunPeriod);
        IchimokuSenkouSpanBIndicator spanB = new IchimokuSenkouSpanBIndicator(series, senkouSpanBPeriod);

        double a = spanA.getValue(endIndex).doubleValue();
        double b = spanB.getValue(endIndex).doubleValue();

        return a - b;  // Cloud spread
    }
}
