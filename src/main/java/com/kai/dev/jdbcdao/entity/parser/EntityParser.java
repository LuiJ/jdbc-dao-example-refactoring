package com.kai.dev.jdbcdao.entity.parser;

import com.kai.dev.jdbcdao.entity.Entity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;


public class EntityParser <T extends Entity>
{
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public HashMap<String, String> parse(T entity)
    {
        HashMap<String, String> parsedEntity = new HashMap<>();
        Method[] methods = entity.getClass().getMethods();
        for (Method method : methods)
        { 
            if (isNotSuitableMethod(method))
            {
                continue;
            }
            parsedEntity.put(getFieldName(method), getFieldValue(entity, method));
        }
        return parsedEntity;
    }
    
    
    private boolean isNotSuitableMethod(Method method)
    {
        String methodPrefix = method.getName().substring(0, 3); 
        boolean returnsCollection = Collection.class.isAssignableFrom(method.getReturnType());
        boolean returnsEntity = Entity.class.isAssignableFrom(method.getReturnType());
        
        return !methodPrefix.equalsIgnoreCase("get") ||
                method.getName().equalsIgnoreCase("getClass") ||
                returnsEntity || returnsCollection;
    }
    
    
    private String getFieldName(Method method)
    {
        return splitCamelCase(method.getName().substring(3)).toLowerCase();        
    }
    
    
    private String getFieldValue(T entity, Method method)
    {
        String fieldValue = null;
        try {
            Object result = method.invoke(entity);
            if (method.getReturnType() == Date.class){
                fieldValue = formDateFieldValue(result);
            }
            else {
                fieldValue = String.valueOf(result);
            }                    
        }
        catch (IllegalAccessException | InvocationTargetException | ClassCastException e){
            throw new IllegalStateException(e.getMessage());
        } 
        return handleNullValue(fieldValue);        
    }
    
    
    private String formDateFieldValue(Object value)
    {
        Date date = (Date) value;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return dateFormatter.format(date);
    }
    
    
    private String handleNullValue(String fieldValue)
    {
        String value = fieldValue;
        if (fieldValue == null || 
            fieldValue.equalsIgnoreCase("null") ||
            fieldValue.equalsIgnoreCase("0"))
        {
            value = "NULL";
        }
        return value;
    }
    
    
    private String splitCamelCase(String s) 
    {
        String result = s.replaceAll(
           String.format("%s|%s|%s",
              "(?<=[A-Z])(?=[A-Z][a-z])",
              "(?<=[^A-Z])(?=[A-Z])",
              "(?<=[A-Za-z])(?=[^A-Za-z])"
           ),
           "_"
        );
        return result;
    }
}
