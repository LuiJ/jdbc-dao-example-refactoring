package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.entity.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface EntityBuilder 
{
    Entity build(ResultSet resultSet) throws SQLException;    
}
