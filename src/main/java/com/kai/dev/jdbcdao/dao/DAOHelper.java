package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.db.DbHelper;
import com.kai.dev.jdbcdao.entity.Identifiable;
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



public class DAOHelper <T extends Identifiable> {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private enum ResultTypeEnum {
        PARAMS_AND_VALUES, PARAMS, VALUES;
    }  
    
    private Class<T> type;
    private DbHelper<T> dbHelper;
    
    public DAOHelper(Class<T> type){
        this.type = type;
        dbHelper = new DbHelper<>(type);
    } 
    
    public T getById(int id){
        String tableName = getTableName();
        String query = "SELECT * FROM " + tableName + " WHERE id=" + id;
        T result = dbHelper.executeSelectQuery(query).get(0);
        return result;
    }     

    public T getByConditionsSingleResult(Properties conditions){
        T entity = null;
        List<T> entities = getAllByConditions(conditions);
        if (!entities.isEmpty()){
            entity = entities.get(0);
        }
        return entity;
    }    

    public List<T> getAllByConditions(Properties conditions){
        String tableName = getTableName();
        String spliter = " AND ";
        String queryConditions = propertiesToString(conditions, spliter);
        String query = "SELECT * FROM " + tableName + " WHERE " + queryConditions;
        List<T> result = dbHelper.executeSelectQuery(query);
        return result;
    }    

    public List<T> getAll(){
        String tableName = getTableName();
        String query = "SELECT * FROM " + tableName;
        List<T> result = dbHelper.executeSelectQuery(query);
        return result;
    }    

    public int save(T entity) {
        int savedEntityId = 0; 
        Integer id = entity.getId(); 
        String saveQuery = null;       
        if (id == null){
            saveQuery = createInsertQuery(entity);
            savedEntityId = dbHelper.executeUpdateQuery(saveQuery).get(0);
        }
        else {
            saveQuery = createUpdateQuery(entity);
            dbHelper.executeUpdateQuery(saveQuery);
            savedEntityId = entity.getId();
        }        
        return savedEntityId;
    }
    
    
    // ------------------------------------------------------------------------
    
    
    private String getTableName(){
        String tableName = null;
        try {
            tableName = (String) type.getField("TABLE_NAME").get(null);            
        }
        catch (IllegalAccessException | NoSuchFieldException e){
            throw new IllegalStateException(e.getMessage());
        }
        return tableName;
    }
    
    
    private String createInsertQuery(T entity){
        String tableName = getTableName();
        String paramsSpliter = ", ";
        String queryParams = entityParamsAndValuesToString(entity, paramsSpliter, ResultTypeEnum.PARAMS);
        String queryValues = entityParamsAndValuesToString(entity, paramsSpliter, ResultTypeEnum.VALUES);
        String query = "INSERT INTO " + tableName + " (" + queryParams 
                     + ") VALUES (" + queryValues + ")";
        return query;
    }
    
    
    private String createUpdateQuery(T entity){
        String tableName = getTableName();
        int entityId = entity.getId();
        String paramsSpliter = ", ";
        String queryParamsAndValues = entityParamsAndValuesToString(entity, paramsSpliter, ResultTypeEnum.PARAMS_AND_VALUES);
        String query = "UPDATE " + tableName + " SET " + queryParamsAndValues 
                     + " WHERE id=" + entityId;
        return query;
    }
    
    
    // TODO: Refactor in future - method has many responsibilities
    private String entityParamsAndValuesToString(T entity, String splitWith, ResultTypeEnum resultType){
        
        Method[] methods = entity.getClass().getMethods();
        List<String> queryParams = new ArrayList<>();
        
        for (Method method : methods){
            
            String methodName = method.getName();
            String methodPrefix = methodName.substring(0, 3); 
            
            Class<?> methodReturnType = method.getReturnType();
            boolean isCollection = Collection.class.isAssignableFrom(methodReturnType);
            boolean isIdentifiable = Identifiable.class.isAssignableFrom(methodReturnType);
            
            if (methodPrefix.equalsIgnoreCase("get") && !isCollection &&
                !isIdentifiable && !methodName.equalsIgnoreCase("getClass"))
            {                
                String fieldName = methodName.substring(3);
                fieldName = splitCamelCase(fieldName).toLowerCase();
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
                
                if (fieldValue != null &&
                    !fieldValue.equalsIgnoreCase("null") && 
                    !fieldValue.equalsIgnoreCase("0"))
                {
                    fieldValue = "'" + fieldValue + "'";
                }
                else {
                    fieldValue = "NULL";
                }
                
                if (resultType == ResultTypeEnum.PARAMS_AND_VALUES){
                    queryParams.add(fieldName + "=" + fieldValue);
                }
                else if (resultType == ResultTypeEnum.PARAMS){
                    queryParams.add(fieldName);
                }
                else if (resultType == ResultTypeEnum.VALUES){
                    queryParams.add(fieldValue);
                }
                else {
                    throw new IllegalArgumentException("'" + resultType 
                        + "' is incorrect resultType for method " 
                        + "'entityParamsAndValuesToString'");
                }
            }
        }          
        String result = StringUtils.join(queryParams, splitWith);
        return result;
    }
    
    
    private String propertiesToString(Properties conditions, String splitWith){
        List<String> queryParams = new ArrayList<>();
        Enumeration<Object> keys = conditions.keys();
        while (keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            String value = (String) conditions.getProperty(key);
            queryParams.add(key + "='" +value + "'");
        }
        String result = StringUtils.join(queryParams, splitWith);
        return result;
    }
    
    
    private String splitCamelCase(String s) {
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
