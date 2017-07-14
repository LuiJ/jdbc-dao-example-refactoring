package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.entity.Identifiable;
import java.util.List;
import java.util.Properties;


public abstract class AbstractDAO<T extends Identifiable> implements DAO<T> {
    
    private DAOHelper<T> helper;
    
    public AbstractDAO(Class<T> type){
        helper = new DAOHelper<>(type);
    }

    @Override
    public T getById(int id){
        T result = helper.getById(id);
        return result;
    } 
    
    @Override
    public List<T> getAll(){
        List<T> result = helper.getAll();
        return result;
    }
    
    @Override
    public int save(T entity) {
        int savedEntityId = helper.save(entity);
        return savedEntityId;
    } 
    
    protected T getByConditionsSingleResult(Properties conditions){
        T entity = helper.getByConditionsSingleResult(conditions);
        return entity;
    }
    
    protected List<T> getAllByConditions(Properties conditions){
        List<T> result = helper.getAllByConditions(conditions);
        return result;
    }  
}
