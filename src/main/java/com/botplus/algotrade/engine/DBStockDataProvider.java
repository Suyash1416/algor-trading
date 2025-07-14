package com.botplus.algotrade.engine;


import com.botplus.algotrade.*;
import com.botplus.algotrade.base.StockDataProvider;

import java.sql.*;
import java.util.*;

public class DBStockDataProvider implements StockDataProvider {

    private final Connection connection;
    private final String query;

    public DBStockDataProvider(Connection connection, String query) {
        this.connection = connection;
        this.query = query;
    }

    @Override
    public List<StockDataRow> loadStockData() {
        List<StockDataRow> rows = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                StockDataRow row = new StockDataRow(
                    rs.getDate("date").toLocalDate(),
                    rs.getDouble("open"),
                    rs.getDouble("high"),
                    rs.getDouble("low"),
                    rs.getDouble("close"),
                    rs.getDouble("volume")
                );
                rows.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB read failed", e);
        }

        return rows;
    }
}
