package com.botplus.algotrade.strategy;


public class StrategyCondition {
    public String type;                 // "cross_above", "greater_than", etc.
    public String indicator1;
    public String indicator2;
    public Double threshold;
    public Double thresholdLow;
    public Double thresholdHigh;
    public Integer periods;
    public String action;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIndicator1() {
		return indicator1;
	}
	public void setIndicator1(String indicator1) {
		this.indicator1 = indicator1;
	}
	public String getIndicator2() {
		return indicator2;
	}
	public void setIndicator2(String indicator2) {
		this.indicator2 = indicator2;
	}
	public Double getThreshold() {
		return threshold;
	}
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}
	public Double getThresholdLow() {
		return thresholdLow;
	}
	public void setThresholdLow(Double thresholdLow) {
		this.thresholdLow = thresholdLow;
	}
	public Double getThresholdHigh() {
		return thresholdHigh;
	}
	public void setThresholdHigh(Double thresholdHigh) {
		this.thresholdHigh = thresholdHigh;
	}
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}      
    
    // "BUY" / "SELL"
}
