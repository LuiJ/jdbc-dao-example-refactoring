package com.kai.dev.jdbcdao.db;

import java.util.ResourceBundle;


public enum JdbcConfig {
    
    INSTANCE;
    
    private static final String CONFIGURATION_FILE_NAME = "jdbc";
    private static final String DRIVER = "driver";
    private static final String DB_URL = "db-url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    
    private final String driver;
    private final String dbUrl;    
    private final String user;
    private final String password;
    
    JdbcConfig(){
        ResourceBundle config = ResourceBundle.getBundle(CONFIGURATION_FILE_NAME);
        driver = config.getString(DRIVER);
        dbUrl = config.getString(DB_URL);
        user = config.getString(USER);
        password = config.getString(PASSWORD);
    }

    public String getDriver() {
        return driver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }        
}
