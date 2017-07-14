package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.entity.Identifiable;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface EntityBuilder {

    Identifiable build(ResultSet resultSet) throws SQLException;
    
}
