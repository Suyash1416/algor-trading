package com.botplus.algotrade;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.indicator.IndicatorFactory;
import com.botplus.algotrade.utils.ExcelExporter;


public class TestSMAFixed {

    public static void main(String[] args) {
        List<Bar> bars = List.of(
            Bar.of(Duration.ofDays(1), ZonedDateTime.now().minusDays(4), 100, 105, 95, 102, 1000),
            Bar.of(Duration.ofDays(1), ZonedDateTime.now().minusDays(3), 102, 107, 98, 104, 1200),
            Bar.of(Duration.ofDays(1), ZonedDateTime.now().minusDays(2), 104, 108, 101, 107, 1500),
            Bar.of(Duration.ofDays(1), ZonedDateTime.now().minusDays(1), 107, 110, 106, 109, 1600),
            Bar.of(Duration.ofDays(1), ZonedDateTime.now(), 109, 112, 107, 111, 1800)
        );

        BarSeries series = new BaseBarSeries("TestSeries");
        series.setBars(bars);

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma = new SMAIndicator(closePrice, 3);

        for (int i = 0; i < series.getBarCount(); i++) {
            System.out.println("SMA[" + i + "] = " + sma.getValue(i));
        }
    }
}
