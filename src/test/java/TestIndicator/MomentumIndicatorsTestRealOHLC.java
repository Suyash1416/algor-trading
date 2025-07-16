package TestIndicator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.indicator.CCIIndicatorImpl;
import com.botplus.algotrade.indicator.ROCIndicatorImpl;
import com.botplus.algotrade.indicator.RSIIndicatorImpl;
import com.botplus.algotrade.indicator.StochasticKIndicatorImpl;
import com.botplus.algotrade.indicator.WilliamsRIndicatorImpl;

public class MomentumIndicatorsTestRealOHLC {
    public static void main(String[] args) {
        BarSeries series = generateRealOHLCSeries();
        TechnicalIndicator[] indicators = {
            new RSIIndicatorImpl(14),
            new StochasticKIndicatorImpl(14),
            new CCIIndicatorImpl(20),
            new ROCIndicatorImpl(10),
            new WilliamsRIndicatorImpl(14)
        };
        for (TechnicalIndicator ind : indicators) {
            printIndicator(ind, series);
        }
    }

    private static BarSeries generateRealOHLCSeries() {
        BaseBarSeries series = new BaseBarSeries("TATAMOTORS");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String[] data = {
            "Jun 16, 2025,711.00,714.00,672.95,686.65",
            "Jun 17, 2025,686.10,686.15,672.00,674.75",
            "Jun 18, 2025,671.85,679.35,668.00,670.25",
            "Jun 19, 2025,670.25,676.30,666.65,672.30",
            "Jun 20, 2025,673.40,678.80,670.10,676.20",
            "Jun 23, 2025,670.00,676.00,666.10,671.25",
            "Jun 24, 2025,678.00,686.50,672.00,673.65",
            "Jun 25, 2025,676.00,679.00,673.45,674.50",
            "Jun 26, 2025,677.00,683.70,674.45,682.75",
            "Jun 27, 2025,685.00,691.45,683.00,686.90",
            "Jun 30, 2025,688.90,691.90,685.00,688.00",
            "Jul 01, 2025,691.10,693.85,680.40,683.80",
            "Jul 02, 2025,683.80,692.45,680.65,688.55",
            "Jul 03, 2025,693.85,696.95,688.50,690.40",
            "Jul 04, 2025,691.00,692.85,686.35,689.05",
            "Jul 07, 2025,689.05,691.95,683.35,688.85",
            "Jul 08, 2025,693.00,696.95,687.50,693.20",
            "Jul 09, 2025,688.95,695.50,688.50,692.80",
            "Jul 10, 2025,692.00,696.75,685.00,695.60",
            "Jul 11, 2025,692.95,698.40,677.05,681.80",
            "Jul 14, 2025,681.00,681.00,672.00,674.90",
          
        };
        for (String row : data) {
        	String[] parts = row.split(",");
        	String dateStr = parts[0].trim() + ", " + parts[1].trim(); // "Jun 16, 2025"
        	ZonedDateTime dt = ZonedDateTime.of(LocalDate.parse(dateStr, fmt).atStartOfDay(), ZoneOffset.UTC);

            series.addBar(new BaseBar(
            	    Duration.ofDays(1), dt,
                Double.parseDouble(parts[2]), // open
                Double.parseDouble(parts[3]), // high
                Double.parseDouble(parts[4]), // low
                Double.parseDouble(parts[5]), // close
                0
            ));
        }
        return series;
    }

    private static void printIndicator(TechnicalIndicator ind, BarSeries series) {
        System.out.println("=== " + ind.getName() + " ===");
        Double[] vals = ind.compute(series);
        for (int i = 0; i < vals.length; i++) {
            System.out.printf("%s: %.2f\n", series.getBar(i).getEndTime().toLocalDate(), vals[i]);
        }
        System.out.printf("Latest = %.2f\n\n", ind.calculateLatest(series));
    }
}
