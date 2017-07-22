package com.kai.dev.jdbcdao.db;

import com.kai.dev.jdbcdao.entity.Entity;
import com.kai.dev.jdbcdao.entity.builder.EntityBuilder;
import com.kai.dev.jdbcdao.entity.builder.EntityBuilderFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class QueryExecutor <T extends Entity> {
    
    private Class<T> type;
    
    public QueryExecutor(Class<T> type){
        this.type = type;
    }     
    
    public List<T> executeSelectQuery(String query)
    {         
        List<T> entities = new ArrayList<>();
        Connection connection = openDbConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try 
        {       
            statement = connection.prepareStatement(query); 
            resultSet = statement.executeQuery();
                        
            EntityBuilder builder = EntityBuilderFactory.create(type); 
            
            while (resultSet.next()){
                T entity = (T) builder.build(resultSet);
                entities.add(entity);
            }                       
        } 
        catch (SQLException e) 
        {
            throw new IllegalStateException(e.getMessage());
        } 
        finally 
        {
            closeDbConnection(connection, statement, resultSet);
        }        
        return entities;
    }
    
    
    public List<Integer> executeUpdateQuery(String query)
    {         
        List<Integer> entitiesId = new ArrayList<>();
        Connection connection = openDbConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try 
        {  
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();     
            
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                entitiesId.add(id);
            }                       
        } 
        catch (SQLException e) 
        {
            throw new IllegalStateException(e.getMessage());
        } 
        finally 
        {
            closeDbConnection(connection, statement, resultSet);
        }         
        return entitiesId;
    }
    
    
    public List<Integer> executeInsertQuery(String query)
    {
        return executeUpdateQuery(query);
    }
    
    
    public List<Integer> executeDeleteQuery(String query)
    {
        return executeUpdateQuery(query);
    }
    
    
    private Connection openDbConnection() 
    {    
        Connection connection = null; 
        try
        {
            String driver = JdbcConfig.INSTANCE.getDriver();
            Class.forName(driver);            
            String url = JdbcConfig.INSTANCE.getDbUrl();
            String user = JdbcConfig.INSTANCE.getUser();
            String password = JdbcConfig.INSTANCE.getPassword();
            connection = DriverManager.getConnection(url, user, password);  
        }
        catch (SQLException | 
               ClassNotFoundException | 
               IllegalArgumentException e) 
        {
            throw new IllegalStateException(e.getMessage());
        }
        return connection;
    }
    
    
    private void closeDbConnection(Connection connection,
                                   Statement statement,
                                   ResultSet resultSet)
    {
        try 
        {
            if (resultSet != null) 
            {
                resultSet.close();
            }
            if (statement != null) 
            {
                statement.close();
            }
            if (connection != null) 
            {
                connection.close();
            }
        } 
        catch (SQLException e) 
        {
            throw new IllegalStateException(e.getMessage());
        }
    }
    
}
