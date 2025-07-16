package com.botplus.algotrade.indicator;


import com.botplus.algotrade.base.MultiLineIndicator;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.ichimoku.*;
import java.util.HashMap;
import java.util.Map;

public class IchimokuMultiLineIndicatorImpl implements MultiLineIndicator {

    private final int conversionPeriod;
    private final int basePeriod;
    private final int spanPeriod;

    public IchimokuMultiLineIndicatorImpl(int conversionPeriod, int basePeriod, int spanPeriod) {
        this.conversionPeriod = conversionPeriod;
        this.basePeriod = basePeriod;
        this.spanPeriod = spanPeriod;
    }

    @Override
    public String getName() {
        return "Ichimoku-" + conversionPeriod + "-" + basePeriod + "-" + spanPeriod;
    }

    @Override
    public Map<String, Double[]> computeAll(BarSeries series) {
        int barCount = series.getBarCount();

        IchimokuTenkanSenIndicator tenkanSen = new IchimokuTenkanSenIndicator(series, conversionPeriod);
        IchimokuKijunSenIndicator kijunSen = new IchimokuKijunSenIndicator(series, basePeriod);
        IchimokuSenkouSpanAIndicator spanA = new IchimokuSenkouSpanAIndicator(series, conversionPeriod, basePeriod);
        IchimokuSenkouSpanBIndicator spanB = new IchimokuSenkouSpanBIndicator(series, spanPeriod);
        IchimokuChikouSpanIndicator chikouSpan = new IchimokuChikouSpanIndicator(series, basePeriod);

        Double[] tenkan = new Double[barCount];
        Double[] kijun = new Double[barCount];
        Double[] spanAValues = new Double[barCount];
        Double[] spanBValues = new Double[barCount];
        Double[] chikou = new Double[barCount];

        for (int i = 0; i < barCount; i++) {
            tenkan[i] = tenkanSen.getValue(i).doubleValue();
            kijun[i] = kijunSen.getValue(i).doubleValue();
            spanAValues[i] = spanA.getValue(i).doubleValue();
            spanBValues[i] = spanB.getValue(i).doubleValue();
            chikou[i] = chikouSpan.getValue(i).doubleValue();
        }

        Map<String, Double[]> result = new HashMap<>();
        result.put("TenkanSen", tenkan);
        result.put("KijunSen", kijun);
        result.put("SenkouSpanA", spanAValues);
        result.put("SenkouSpanB", spanBValues);
        result.put("ChikouSpan", chikou);
        return result;
    }

    @Override
    public Map<String, Double> calculateLatest(BarSeries series) {
        int endIndex = series.getBarCount() - 1;

        IchimokuTenkanSenIndicator tenkanSen = new IchimokuTenkanSenIndicator(series, conversionPeriod);
        IchimokuKijunSenIndicator kijunSen = new IchimokuKijunSenIndicator(series, basePeriod);
        IchimokuSenkouSpanAIndicator spanA = new IchimokuSenkouSpanAIndicator(series, conversionPeriod, basePeriod);
        IchimokuSenkouSpanBIndicator spanB = new IchimokuSenkouSpanBIndicator(series, spanPeriod);
        IchimokuChikouSpanIndicator chikouSpan = new IchimokuChikouSpanIndicator(series, basePeriod);

        Map<String, Double> latest = new HashMap<>();
        latest.put("TenkanSen", tenkanSen.getValue(endIndex).doubleValue());
        latest.put("KijunSen", kijunSen.getValue(endIndex).doubleValue());
        latest.put("SenkouSpanA", spanA.getValue(endIndex).doubleValue());
        latest.put("SenkouSpanB", spanB.getValue(endIndex).doubleValue());
        latest.put("ChikouSpan", chikouSpan.getValue(endIndex).doubleValue());
        return latest;
    }
}

