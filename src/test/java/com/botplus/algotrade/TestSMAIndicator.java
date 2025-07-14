package com.botplus.algotrade;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.indicator.IndicatorFactory;
import com.botplus.algotrade.utils.ExcelExporter;

public class TestSMAIndicator {
    public static void main(String[] args) {
        BarSeries series = new BaseBarSeries("TestSeries");

        series.addBar(new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(4), 100, 105, 95, 102, 1000));
        series.addBar(new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(3), 102, 107, 98, 104, 1200));
        series.addBar(new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(2), 104, 108, 101, 107, 1500));
        series.addBar(new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(1), 107, 110, 106, 109, 1600));
        series.addBar(new BaseBar(Duration.ofDays(1), ZonedDateTime.now(), 109, 112, 107, 111, 1800));

        Map<String, Object> config = new HashMap<>();
        config.put("period", 3);

        List<String> indicatorsToRun = Arrays.asList("SMA", "EMA", "RSI");

        Map<String, Double[]> resultsMap = new LinkedHashMap<>();

        for (String name : indicatorsToRun) {
            TechnicalIndicator indicator = IndicatorFactory.create(name, config);
            Double[] values = indicator.compute(series);
            resultsMap.put(indicator.getName(), values);
        }

        ExcelExporter.exportIndicatorsToExcel(resultsMap);
        System.out.println("Export all results to Excel in one file");
    }
}
