package com.kai.dev.jdbcdao.entity;


public abstract class Entity
{
    public static final String FIELD_ID = "id";
    
    private Integer id;
    
    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }
}
