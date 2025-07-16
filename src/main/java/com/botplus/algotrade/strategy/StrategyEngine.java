package com.botplus.algotrade.strategy;


import java.util.Map;

public class StrategyEngine {

    public static boolean evaluateCondition(StrategyCondition cond, Map<String, Double[]> indicators, int index) {

    	if(indicators.get(cond.indicator1) == null )
	    	{
	    	System.out.print("\n" + cond.indicator1 + " values are not available" );
	    	return false ;
	    	}
	    if(cond.indicator2 != null && indicators.get(cond.indicator2) == null )
	    	{
	    	System.out.print("\n" + cond.indicator2 + " values are not available" );
	    	return false ;
	    	}
    	
	    
    	Double[] i1 = indicators.get(cond.indicator1);
        Double[] i2 = cond.indicator2 != null ? indicators.get(cond.indicator2) : null;
        
        

        
        
        if (i1 == null || index < 1 || i1[index] == null || i1[index - 1] == null) return false;

        switch (cond.type) {
            case "cross_above":
                if (i2 != null) return i1[index - 1] <= i2[index - 1] && i1[index] > i2[index];
                return i1[index - 1] <= cond.threshold && i1[index] > cond.threshold;

            case "cross_below":
                if (i2 != null) return i1[index - 1] >= i2[index - 1] && i1[index] < i2[index];
                return i1[index - 1] >= cond.threshold && i1[index] < cond.threshold;

            case "greater_than":
                return (i2 != null) ? i1[index] > i2[index] : i1[index] > cond.threshold;

            case "less_than":
                return (i2 != null) ? i1[index] < i2[index] : i1[index] < cond.threshold;

            case "equal_to":
                return (i2 != null) ? i1[index].equals(i2[index]) : i1[index].equals(cond.threshold);

            case "in_range":
                return i1[index] >= cond.thresholdLow && i1[index] <= cond.thresholdHigh;

            case "rising_n":
                if (index < cond.periods) return false;
                for (int i = index - cond.periods + 1; i <= index; i++)
                    if (i1[i] <= i1[i - 1]) return false;
                return true;

            case "falling_n":
                if (index < cond.periods) return false;
                for (int i = index - cond.periods + 1; i <= index; i++)
                    if (i1[i] >= i1[i - 1]) return false;
                return true;

            default:
                return false;
        }
    }

    public static boolean evaluateStrategy(StrategyDefinition strategy, Map<String, Double[]> indicators, int barCount) {
        for (int i = 1; i < barCount; i++) {
        	System.out.print("\n Bar count ::" + i);
        	
            boolean allTrue = true;
            for (StrategyCondition cond : strategy.conditions) {
                if (!evaluateCondition(cond, indicators, i)) {
                    allTrue = false;
                    break;
                }
            }
            if (allTrue) {
                System.out.printf("\n>> Signal: %s | Strategy: %s | Index: %d\n",
                        strategy.conditions.get(0).action, strategy.name, i);
                return true ;
            }
        }
        
        return false ;
    }
}
