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

public class TestRSIIndicator {

    @Test
    public void testRSIComputation() {
        // Create dummy time series
        TimeSeries series = new BaseTimeSeries("TestSeries", Arrays.asList(
            new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(4), 100, 105, 95, 102, 1000),
            new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(3), 102, 107, 98, 104, 1200),
            new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(2), 104, 108, 101, 107, 1500),
            new BaseBar(Duration.ofDays(1), ZonedDateTime.now().minusDays(1), 107, 110, 106, 109, 1600),
            new BaseBar(Duration.ofDays(1), ZonedDateTime.now(), 109, 112, 107, 111, 1800)
        ));

        TechnicalIndicator rsi = new RSIIndicatorImpl(3);
        Double[] rsiValues = rsi.compute(series);

        assertNotNull(rsiValues);
        assertEquals(series.getBarCount(), rsiValues.length);
        System.out.println("RSI values: " + Arrays.toString(rsiValues));
    }
}
