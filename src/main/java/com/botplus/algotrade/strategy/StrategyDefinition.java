package com.botplus.algotrade.strategy;



import java.util.List;

public class StrategyDefinition {
    public String name;
    public List<StrategyCondition> conditions;
	public String getStrategyName() {
		return name;
	}
	public void setStrategyName(String strategyName) {
		this.name = strategyName;
	}
	public List<StrategyCondition> getConditions() {
		return conditions;
	}
	public void setConditions(List<StrategyCondition> conditions) {
		this.conditions = conditions;
	}
    
    
    
}
