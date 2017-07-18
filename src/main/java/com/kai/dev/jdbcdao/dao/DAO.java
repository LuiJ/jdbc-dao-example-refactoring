package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.db.DbHelper;
import com.kai.dev.jdbcdao.db.QueryBuilder;
import com.kai.dev.jdbcdao.entity.Entity;
import java.util.List;
import java.util.Properties;


public abstract class DAO<T extends Entity>
{    
    private final DbHelper<T> dbHelper;
    private final QueryBuilder<T> queryBuilder;
    
    public DAO(Class<T> type)
    {
        dbHelper = new DbHelper<>(type);
        queryBuilder = new QueryBuilder<>(type);
    }
    
    
    public T getById(int id)
    {
        Properties conditions = new Properties();
        conditions.put(Entity.FIELD_ID, String.valueOf(id));
        String query = queryBuilder.buildSelectQuery(conditions);
        return dbHelper.executeSelectQuery(query).get(0);
    } 
    
    
    public List<T> getAll()
    {
        String query = queryBuilder.buildSelectAllQuery();
        return dbHelper.executeSelectQuery(query);
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
        return dbHelper.executeSelectQuery(query);
    }  
    
    
    public int save(T entity) 
    {
        String saveQuery = queryBuilder.buildInsertQuery(entity);
        return dbHelper.executeUpdateQuery(saveQuery).get(0);
    } 
    
    
    public int update(T entity) 
    {
        String saveQuery = queryBuilder.buildUpdateQuery(entity);
        dbHelper.executeUpdateQuery(saveQuery);      
        return entity.getId();
    } 
}
