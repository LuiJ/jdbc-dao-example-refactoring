package com.kai.dev.jdbcdao.db;

import com.kai.dev.jdbcdao.entity.Entity;
import com.kai.dev.jdbcdao.entity.parser.EntityParser;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;


public class QueryBuilder <T extends Entity>
{    
    private final Class<T> type;
    private final EntityParser<T> entityParser;
    
    public QueryBuilder(Class<T> type){
        this.type = type;
        entityParser = new EntityParser();
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
        return "INSERT INTO " + getTableName() + " " + formInsertPart(entity);
    }       
    
    public String buildUpdateQuery(T entity)
    {
        return "UPDATE " + getTableName() + " SET " + formSetPart(entity) + " " +
               "WHERE id='" + entity.getId() + "'";
    }    
    
    public String buildDeleteQuery(T entity)
    {
        return "DELETE FROM " + getTableName() + " "
             + "WHERE id='" + entity.getId() + "'";
    }
    
    // ----------------------------------------------------------------------
    
    
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
    
    
    private String formInsertPart(T entity)
    {
        List<String> fieldsNames = new ArrayList<>();
        List<String> fieldsValues = new ArrayList<>();
        Map<String, String> parsedEntity = entityParser.parse(entity);
        for (Map.Entry<String, String> field : parsedEntity.entrySet())
        {
            if (isNotSuitableField(field))
            {
                continue;
            }
            fieldsNames.add(field.getKey());
            fieldsValues.add(field.getValue());
        }
        return "(" + StringUtils.join(fieldsNames, ", ") + ") "
             + "VALUES (" + StringUtils.join(fieldsValues, ", ") + ")";
    }
    
    
    private String formSetPart(T entity)
    {
        List<String> nameValuePairs = new ArrayList<>();
        Map<String, String> parsedEntity = entityParser.parse(entity);
        for (Map.Entry<String, String> field : parsedEntity.entrySet())
        {
            if (isNotSuitableField(field))
            {
                continue;
            }
            nameValuePairs.add(field.getKey() + "=" + field.getValue());
        }
        return StringUtils.join(nameValuePairs, ", ");
    }
    
    
    private boolean isNotSuitableField(Map.Entry<String, String> field)
    {
        return Entity.FIELD_ID.equals(field.getKey()) ||
               field.getValue() == null;
    }
}
