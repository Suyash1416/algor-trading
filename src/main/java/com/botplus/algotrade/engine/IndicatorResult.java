package com.botplus.algotrade.engine;


public class IndicatorResult {

    private final String name;
    private final Double[] values;

    public IndicatorResult(String name, Double[] values) {
        this.name = name;
        this.values = values;
    }

    /**
     * Gets the name of the indicator (used as column header).
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the array of computed values for the indicator.
     */
    public Double[] getValues() {
        return values;
    }
}
