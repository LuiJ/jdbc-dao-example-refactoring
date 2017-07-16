package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.db.QueryBuilder;
import com.kai.dev.jdbcdao.db.DbHelper;
import com.kai.dev.jdbcdao.entity.Entity;
import java.util.List;
import java.util.Properties;



public class DAOHelper <T extends Entity> 
{   
    private final DbHelper<T> dbHelper;
    private final QueryBuilder<T> queryBuilder;
    
    public DAOHelper(Class<T> type)
    {
        dbHelper = new DbHelper<>(type);
        queryBuilder = new QueryBuilder<>(type);
    } 
    
    public T getById(int id)
    {
        Properties conditions = new Properties();
        conditions.put(Entity.FIELD_ID, String.valueOf(id));
        String query = queryBuilder.buildSelectQuery(conditions);
        T result = dbHelper.executeSelectQuery(query).get(0);
        return result;
    }     

    public T getByConditionsSingleResult(Properties conditions)
    {
        T entity = null;
        List<T> entities = getAllByConditions(conditions);
        if (!entities.isEmpty()){
            entity = entities.get(0);
        }
        return entity;
    }    

    public List<T> getAllByConditions(Properties conditions)
    {
        String query = queryBuilder.buildSelectQuery(conditions);
        List<T> result = dbHelper.executeSelectQuery(query);
        return result;
    }    

    public List<T> getAll()
    {
        String query = queryBuilder.buildSelectAllQuery();
        List<T> result = dbHelper.executeSelectQuery(query);
        return result;
    }    

    public int save(T entity) 
    {
        int savedEntityId = 0; 
        Integer id = entity.getId(); 
        String saveQuery = null;       
        if (id == null){
            saveQuery = queryBuilder.buildInsertQuery(entity);
            savedEntityId = dbHelper.executeUpdateQuery(saveQuery).get(0);
        }
        else {
            saveQuery = queryBuilder.buildUpdateQuery(entity);
            dbHelper.executeUpdateQuery(saveQuery);
            savedEntityId = entity.getId();
        }        
        return savedEntityId;
    }
}
