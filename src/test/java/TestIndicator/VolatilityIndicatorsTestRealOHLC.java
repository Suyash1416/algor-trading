package TestIndicator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;

import com.botplus.algotrade.base.TechnicalIndicator;
import com.botplus.algotrade.indicator.ATRIndicatorImpl;
import com.botplus.algotrade.indicator.BollingerBandWidthIndicatorImpl;
import com.botplus.algotrade.indicator.KeltnerChannelWidthIndicatorImpl;
import com.botplus.algotrade.indicator.StandardDeviationIndicatorImpl;

public class VolatilityIndicatorsTestRealOHLC {
    public static void main(String[] args) {
        BarSeries series = generateRealOHLCSeries();
        TechnicalIndicator[] indicators = {
       //     new ATRIndicatorImpl(14),
            new BollingerBandWidthIndicatorImpl(20, 2.0),
            new StandardDeviationIndicatorImpl(20),
       //     new KeltnerChannelWidthIndicatorImpl(20, 14, 2.0)
        };
        for (TechnicalIndicator ind : indicators) {
            printIndicator(ind, series);
        }
    }

    private static BarSeries generateRealOHLCSeries() {
        BaseBarSeries series = new BaseBarSeries("TATAMOTORS_VOL");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        String[] data = {
            "Jun 03, 2025,703.85,718.35,702.30,703.85",
            "Jun 04, 2025,709.15,715.45,698.40,709.15",
            "Jun 05, 2025,710.15,714.45,704.00,710.15",
            "Jun 06, 2025,711.00,715.90,701.20,711.00",
            "Jun 09, 2025,717.80,726.00,713.10,717.80",
            "Jun 10, 2025,732.25,734.75,715.35,732.25",
            "Jun 11, 2025,736.40,744.00,730.15,736.40",
            "Jun 12, 2025,715.35,737.70,711.25,715.35",
            "Jun 13, 2025,712.05,714.95,698.30,712.05",
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
            "Jul 14, 2025,681.00,681.00,672.00,674.90"
        };

        for (String row : data) {
            String[] p = row.split(",");
            String dateStr = p[0].trim() + ", " + p[1].trim();
            ZonedDateTime date = ZonedDateTime.of(
                java.time.LocalDate.parse(dateStr, fmt).atStartOfDay(),
                java.time.ZoneOffset.UTC
            );

            series.addBar(new BaseBar(
                java.time.Duration.ofDays(1), date,
                Double.parseDouble(p[2]), // open
                Double.parseDouble(p[3]), // high
                Double.parseDouble(p[4]), // low
                Double.parseDouble(p[5]), // close
                0 // volume
            ));
        }

        return series;
    }

    private static void printIndicator(TechnicalIndicator ind, BarSeries series) {
        System.out.println("=== " + ind.getName() + " ===");
        Double[] vals = ind.compute(series);
        for (int i = 0; i < vals.length; i++) {
            System.out.printf("%s: %.4f\n",
                series.getBar(i).getEndTime().toLocalDate(), vals[i]);
        }
        System.out.printf("Latest = %.4f\n\n", ind.calculateLatest(series));
    }
}
