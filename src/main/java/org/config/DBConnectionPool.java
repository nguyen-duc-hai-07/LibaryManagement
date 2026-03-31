package org.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DBConnectionPool {
    private HikariDataSource dataSource;
    private final String URL = "jdbc:postgresql://localhost:2310/Library";
    private final String USER = "postgres";
    private final String PASSWORD = "123456";
    private static DBConnectionPool instance = new DBConnectionPool();

    private DBConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setAutoCommit(false);
        dataSource = new HikariDataSource(config);
    }
    public static DBConnectionPool getInstance() {
        return instance;
    }
    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
