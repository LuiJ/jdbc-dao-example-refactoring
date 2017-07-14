package com.kai.dev.jdbcdao.db;

import com.kai.dev.jdbcdao.entity.Identifiable;
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


public class DbHelper <T extends Identifiable> {
    
    private Class<T> type;
    
    private enum OperationEnum {
        SELECT, UPDATE;
    }

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSetSelect = null;
    private ResultSet resultSetUpdate = null;
    
    public DbHelper(Class<T> type){
        this.type = type;
    } 
    
    
    public List<T> executeSelectQuery(String query){         
        List<T> entities = new ArrayList<>();
        try {            
            openDbConnection();
            statement = connection.createStatement(); 
            resultSetSelect = statement.executeQuery(query);
                        
            EntityBuilder builder = EntityBuilderFactory.create(type); 
            
            while (resultSetSelect.next()){
                T entity = (T) builder.build(resultSetSelect);
                entities.add(entity);
            }                       
        } 
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
        } 
        finally {
            closeDbConnection(OperationEnum.SELECT);
        }        
        return entities;
    }
    
    
    public List<Integer> executeUpdateQuery(String query){         
        List<Integer> entitiesId = new ArrayList<>();
        try {            
            openDbConnection();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            resultSetUpdate = preparedStatement.getGeneratedKeys();     
            
            while (resultSetUpdate.next()){
                int id = resultSetUpdate.getInt(1);
                entitiesId.add(id);
            }                       
        } 
        catch (ClassNotFoundException | SQLException | IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage());
        } 
        finally {
            closeDbConnection(OperationEnum.UPDATE);
        }        
        return entitiesId;
    }
    
    
    private void openDbConnection() throws ClassNotFoundException, SQLException,
                                           IllegalArgumentException 
    {    
        String driver = JdbcConfig.INSTANCE.getDriver();
        Class.forName(driver);            
        String url = JdbcConfig.INSTANCE.getDbUrl();
        String user = JdbcConfig.INSTANCE.getUser();
        String password = JdbcConfig.INSTANCE.getPassword();
        connection = DriverManager.getConnection(url, user, password);          
    }
    
    
    private void closeDbConnection(OperationEnum operation){
        try {
            if (resultSetSelect != null && operation == OperationEnum.SELECT) {
                resultSetSelect.close();
            }
            if (resultSetUpdate != null && operation == OperationEnum.UPDATE) {
                resultSetUpdate.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } 
        catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
    
}
