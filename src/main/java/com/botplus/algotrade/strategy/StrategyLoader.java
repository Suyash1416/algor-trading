package com.botplus.algotrade.strategy;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StrategyLoader {

    public static List<StrategyDefinition> loadStrategies(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return List.of(mapper.readValue(new File(filePath), StrategyDefinition[].class));
    }
}
