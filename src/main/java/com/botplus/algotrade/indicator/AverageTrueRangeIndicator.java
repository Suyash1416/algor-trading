package com.botplus.algotrade.indicator;


import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.TRIndicator;
import org.ta4j.core.num.Num;

public class AverageTrueRangeIndicator implements Indicator<Num> {

    private final EMAIndicator ema;

    public AverageTrueRangeIndicator(BarSeries series, int period) {
    	TRIndicator tr = new TRIndicator(series);
        this.ema = new EMAIndicator(tr, period); // You can also use SMAIndicator
    }

    @Override
    public Num getValue(int index) {
        return ema.getValue(index);
    }

    @Override
    public BarSeries getBarSeries() {
        return ema.getBarSeries();
    }

	@Override
	public int getUnstableBars() {
		// TODO Auto-generated method stub
		return 0;
	}
}
