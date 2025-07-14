package com.botplus.algotrade.base;


import com.botplus.algotrade.engine.StockDataRow;
import java.util.*;

public interface StockDataProvider {
    List<StockDataRow> loadStockData();
}
