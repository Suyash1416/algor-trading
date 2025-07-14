package com.botplus.algotrade.indicator;

import java.util.Map;
import com.botplus.algotrade.base.* ;

   
   public abstract class ConfigurableIndicator implements TechnicalIndicator {
    protected int period;

    @Override
    public void configure(Map<String, Object> parameters) {
        if (parameters.containsKey("period")) {
            this.period = (int) parameters.get("period");
        } else {
            throw new IllegalArgumentException("Missing required parameter: period");
        }
    }
}
