package TestIndicator;


import java.time.ZonedDateTime;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.indicator.ADXIndicatorImpl;
import com.botplus.algotrade.indicator.EMAIndicatorImpl;
import com.botplus.algotrade.indicator.IchimokuIndicatorImpl;
import com.botplus.algotrade.indicator.MACDIndicatorImpl;
import com.botplus.algotrade.indicator.NegativeDIIndicatorImpl;
import com.botplus.algotrade.indicator.PositiveDIIndicatorImpl;
import com.botplus.algotrade.indicator.SMAIndicatorImpl;
import com.botplus.algotrade.indicator.WMAIndicatorImpl;

public class TrendIndicatorsTest {

    public static void main(String[] args) {
        BarSeries series = generateSampleSeries();

        // Create indicators
        TechnicalIndicator sma = new SMAIndicatorImpl(14);
        TechnicalIndicator ema = new EMAIndicatorImpl(14);
        TechnicalIndicator wma = new WMAIndicatorImpl(14);
        TechnicalIndicator macd = new MACDIndicatorImpl(12, 26);
        TechnicalIndicator pdi = new PositiveDIIndicatorImpl(14);
        TechnicalIndicator ndi = new NegativeDIIndicatorImpl(14);
        TechnicalIndicator ichimoku = new IchimokuIndicatorImpl(9, 26, 52);
        TechnicalIndicator adx = new ADXIndicatorImpl(14);


        // Print indicator values
        printIndicator(sma, series);
        printIndicator(ema, series);
        printIndicator(wma, series);
        printIndicator(macd, series);
        printIndicator(pdi, series);
        printIndicator(ndi, series);
        printIndicator(ichimoku, series);
        printIndicator(adx, series);

    }

    private static void printIndicator(TechnicalIndicator indicator, BarSeries series) {
        System.out.println("Indicator: " + indicator.getName());
        Double[] values = indicator.compute(series);
        for (int i = 0; i < values.length; i++) {
            System.out.printf("Bar %2d: %.4f\n", i, values[i]);
        }
        System.out.println("Latest Value: " + indicator.calculateLatest(series));
        System.out.println("------------------------------------------------");
    }

    private static BarSeries generateSampleSeries() {
        BarSeries series = new BaseBarSeries("sample");
        ZonedDateTime endTime = ZonedDateTime.now();

        double[] prices = {
                100, 101, 102, 101, 100, 98, 99, 100, 102, 105,
                106, 107, 109, 108, 110, 112, 113, 111, 110, 108,
                107, 105, 104, 106, 108, 109, 111, 110, 108, 107
        };

        for (int i = 0; i < prices.length; i++) {
        	series.addBar(new BaseBar(
        		    java.time.Duration.ofDays(1),          // time period
        		    endTime.plusDays(i),                   // end time
        		    prices[i],                             // open
        		    prices[i],                             // high
        		    prices[i],                             // low
        		    prices[i],                             // close
        		    1000.0                                 // volume
        		));

        }
        
        
        

        return series;
    }
}
