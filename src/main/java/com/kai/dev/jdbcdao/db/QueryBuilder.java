package com.kai.dev.jdbcdao.db;

import com.kai.dev.jdbcdao.entity.Entity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;


public class QueryBuilder <T extends Entity>
{
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private final Class<T> type;
    
    public QueryBuilder(Class<T> type){
        this.type = type;
    }    
    
    public String buildSelectQuery(Properties selectConditions)
    {
        return "SELECT * FROM " + getTableName() + " "
             + "WHERE " + formWherePart(selectConditions);
    }    
    
    public String buildSelectAllQuery()
    {
        return "SELECT * FROM " + getTableName();
    }    
    
    public String buildInsertQuery(T entity)
    {
        String paramsSpliter = ", ";
        String queryParams = entityParamsAndValuesToString(entity, paramsSpliter, ResultType.PARAMS);
        String queryValues = entityParamsAndValuesToString(entity, paramsSpliter, ResultType.VALUES);
        return "INSERT INTO " + getTableName() + " (" + queryParams + 
               ") VALUES (" + queryValues + ")";
    }       
    
    public String buildUpdateQuery(T entity)
    {
        String paramsSpliter = ", ";
        String queryParamsAndValues = entityParamsAndValuesToString(entity, paramsSpliter, ResultType.PARAMS_AND_VALUES);
        return "UPDATE " + getTableName() + " SET " + queryParamsAndValues +
               " WHERE id='" + entity.getId() + "'";
    }    
    
    public String buildDeleteQuery(T entity)
    {
        return "DELETE FROM " + getTableName() + " WHERE id='" + entity.getId() + "'";
    }
    
    // ----------------------------------------------------------------------
    
    
    private enum ResultType 
    {
        PARAMS_AND_VALUES, PARAMS, VALUES;
    }     
    
    
    private String getTableName()
    {
        String tableName = null;
        try {
            tableName = (String) type.getField("TABLE_NAME").get(null);            
        }
        catch (IllegalAccessException | NoSuchFieldException e){
            throw new IllegalStateException(e.getMessage());
        }
        return tableName;
    }    
    
    
    private String formWherePart(Properties selectConditions)
    {
        List<String> queryParams = new ArrayList<>();
        Enumeration<Object> keys = selectConditions.keys();
        while (keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            String value = (String) selectConditions.getProperty(key);
            queryParams.add(key + "='" +value + "'");
        }
        return StringUtils.join(queryParams, " AND ");
    }    
    
    
    private String entityParamsAndValuesToString(T entity, 
                                                String splitWith, 
                                                ResultType resultType)
    {
        List<String> queryParams = new ArrayList<>();        
        Method[] methods = entity.getClass().getMethods();
        
        for (Method method : methods)
        {            
            String methodName = method.getName();
            String methodPrefix = methodName.substring(0, 3); 
            
            Class<?> methodReturnType = method.getReturnType();
            boolean returnsCollection = Collection.class.isAssignableFrom(methodReturnType);
            boolean returnsEntity = Entity.class.isAssignableFrom(methodReturnType);
            
            if (!methodPrefix.equalsIgnoreCase("get") || 
                methodName.equalsIgnoreCase("getClass") || 
                returnsEntity || returnsCollection)
            {
                continue;
            }
            
            // Transform Entity field's name into DB Table colunm name
            String fieldName = methodName.substring(3);
            fieldName = splitCamelCase(fieldName).toLowerCase();
            
            // Get Entity field's value
            String fieldValue = null;
            try {
                Object result = method.invoke(entity);
                if (methodReturnType == Date.class){
                    Date date = (Date) result;
                    SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
                    fieldValue = dateFormatter.format(date);
                }
                else {
                    fieldValue = String.valueOf(result);
                }                    
            }
            catch (IllegalAccessException | InvocationTargetException | ClassCastException e){
                throw new IllegalStateException(e.getMessage());
            } 

            // Transform Entity field's value into String
            if (fieldValue != null &&
                !fieldValue.equalsIgnoreCase("null") && 
                !fieldValue.equalsIgnoreCase("0"))
            {
                fieldValue = "'" + fieldValue + "'";
            }
            else {
                fieldValue = "NULL";
            }

            if (resultType == ResultType.PARAMS_AND_VALUES){
                queryParams.add(fieldName + "=" + fieldValue);
            }
            else if (resultType == ResultType.PARAMS){
                queryParams.add(fieldName);
            }
            else if (resultType == ResultType.VALUES){
                queryParams.add(fieldValue);
            }
            else {
                throw new IllegalArgumentException("'" + resultType 
                    + "' is incorrect resultType for method " 
                    + "'entityParamsAndValuesToString'");
            }
        }          
        String result = StringUtils.join(queryParams, splitWith);
        return result;
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
