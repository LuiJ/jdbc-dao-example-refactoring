package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.entity.Entity;
import com.kai.dev.jdbcdao.entity.parser.EntityColumnFieldsRetriever;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;


public class EntityBuilder<T extends Entity>
{
    private final Class<T> type;    
    private final EntityColumnFieldsRetriever<T> columnFieldsRetriever;
    
    public EntityBuilder(Class<T> type){
        this.type = type;
        columnFieldsRetriever = new EntityColumnFieldsRetriever<>();
    }   
    
    
    public T build(ResultSet resultSet) throws SQLException
    {
        T entity = createNewInstance();
        initialize(entity, resultSet);
        return entity;
    }
    
    
    private T createNewInstance()
    {
        T instance = null;
        try 
        {
            instance = type.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new IllegalStateException(e.getMessage());
        }
        return instance;
    }
    
    
    private void initialize(T entity, ResultSet resultSet) throws SQLException
    {
        Collection<Field> columnFields = columnFieldsRetriever.retrieve(entity);
        for (Field field : columnFields)
        {
            initializeField(entity, field, resultSet);
        }
    }
    
    
    private void initializeField(T entity, Field field, 
                                       ResultSet resultSet) throws SQLException 
    {
        String columnName = field.getDeclaredAnnotation(Column.class).name();
        Object valueObject = resultSet.getObject(columnName);
        Class<?> fieldType = field.getType();
        try 
        {
            field.setAccessible(true);
            field.set(entity, fieldType.cast(valueObject));
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
