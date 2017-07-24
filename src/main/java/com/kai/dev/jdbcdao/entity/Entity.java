package com.kai.dev.jdbcdao.entity;

import com.kai.dev.jdbcdao.annotation.Column;


public abstract class Entity
{
    public static final String FIELD_ID = "id";
    
    @Column(name = "id")
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
