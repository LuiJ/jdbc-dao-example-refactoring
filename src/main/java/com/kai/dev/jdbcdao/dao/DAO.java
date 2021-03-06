package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.db.QueryExecutor;
import com.kai.dev.jdbcdao.db.QueryBuilder;
import com.kai.dev.jdbcdao.entity.Entity;
import java.util.List;
import java.util.Properties;


public abstract class DAO<T extends Entity>
{    
    private final QueryExecutor<T> queryExecutor;
    private final QueryBuilder<T> queryBuilder;
    
    public DAO(Class<T> type)
    {
        queryExecutor = new QueryExecutor<>(type);
        queryBuilder = new QueryBuilder<>(type);
    }
    
    
    public T getById(int id)
    {
        Properties conditions = new Properties();
        conditions.put(Entity.FIELD_ID, String.valueOf(id));
        String query = queryBuilder.buildSelectQuery(conditions);
        return queryExecutor.executeSelectQuery(query).get(0);
    } 
    
    
    public List<T> getAll()
    {
        String query = queryBuilder.buildSelectAllQuery();
        return queryExecutor.executeSelectQuery(query);
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
        return queryExecutor.executeSelectQuery(query);
    }  
    
    
    public int save(T entity) 
    {
        String query = queryBuilder.buildInsertQuery(entity);
        return queryExecutor.executeInsertQuery(query).get(0);
    } 
    
    
    public int update(T entity) 
    {
        String query = queryBuilder.buildUpdateQuery(entity);
        queryExecutor.executeUpdateQuery(query);      
        return entity.getId();
    } 
    
    
    public int delete(T entity) 
    {
        String query = queryBuilder.buildDeleteQuery(entity);
        queryExecutor.executeDeleteQuery(query);      
        return entity.getId();
    }
}
