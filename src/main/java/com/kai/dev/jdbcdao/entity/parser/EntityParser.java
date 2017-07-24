package com.kai.dev.jdbcdao.entity.parser;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.entity.Entity;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class EntityParser<T extends Entity>
{
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public Map<String, String> parse(T entity)
    {
        Map<String, String> parsedEntity = new LinkedHashMap<>(); 
        for (Field field : getFields(entity))
        {
            if (isNotSuitableField(field))
            {
                continue;
            }
            parsedEntity.put(getColumnName(field), 
                             getColumnValue(field, entity));
        }        
        return parsedEntity;
    }
    
    
    private List<Field> getFields(T entity)
    {
        List<Field> fields = new ArrayList<>();
        fields.addAll(getSuperclassFields(entity));
        fields.addAll(getClassFields(entity));
        return fields;
    }
    
    
    private List<Field> getSuperclassFields(T entity)
    {
        return Arrays.asList(entity.getClass()
                                   .getSuperclass()
                                   .getDeclaredFields());
    }
    
    
    private List<Field> getClassFields(T entity)
    {
        return Arrays.asList(entity.getClass()
                                   .getDeclaredFields());
    }
    
    
    private boolean isNotSuitableField(Field field)
    {
        boolean isEntity = Entity.class.isAssignableFrom(field.getType());
        boolean isCollection = Collection.class.isAssignableFrom(field.getType());
        boolean doesntHaveColumnAnnotation = 
                field.getDeclaredAnnotation(Column.class) == null;
        return  doesntHaveColumnAnnotation || isCollection || isEntity;
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
