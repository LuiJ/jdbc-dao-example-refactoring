package com.kai.dev.jdbcdao.entity.builder;

import java.util.HashMap;
import java.util.Map;


public class ResultSetStub extends AbstractResultSet
{
    private Map<String, Object> resultSet;
    
    public ResultSetStub()
    {
        resultSet = new HashMap<>();
    }
    
    public void put(String colunmName, Object columnValue)
    {
        resultSet.put(colunmName, columnValue);
    }
    
    @Override
    public Object getObject(String columnLabel)
    {
        return resultSet.get(columnLabel);
    }
}
