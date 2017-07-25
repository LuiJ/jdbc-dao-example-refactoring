package com.kai.dev.jdbcdao.entity.parser;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.entity.Entity;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


public class EntityParser<T extends Entity>
{
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private final EntityColumnFieldsRetriever<T> columnFieldsRetriever;
    
    public EntityParser()
    {
        columnFieldsRetriever = new EntityColumnFieldsRetriever<>();
    }
    
    public Map<String, String> parse(T entity)
    {
        Map<String, String> parsedEntity = new LinkedHashMap<>(); 
        for (Field columnField : columnFieldsRetriever.retrieve(entity))
        {
            parsedEntity.put(getColumnName(columnField), 
                             getColumnValue(columnField, entity));
        }        
        return parsedEntity;
    }
    
    
    private String getColumnName(Field field)
    {
        return field.getDeclaredAnnotation(Column.class).name();
    }
    
    
    private String getColumnValue(Field field, T entity)
    {
        Object fieldValue = getFieldValue(field, entity);
        if (fieldValue == null)
        {
            return null;
        }
        else if (field.getType() == Date.class)
        {
            return "'" + formDateColumnValue(fieldValue) + "'";
        }
        else 
        {
            return "'" + String.valueOf(fieldValue) + "'";
        }  
    }
    
    
    private Object getFieldValue(Field field, T entity)
    {
        Object value = null;
        try 
        {
            field.setAccessible(true);
            value = field.get(entity);           
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException(e.getMessage());
        }
        return value;
    }
    
    
    private String formDateColumnValue(Object value)
    {
        Date date = (Date) value;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return dateFormatter.format(date);
    }
}
