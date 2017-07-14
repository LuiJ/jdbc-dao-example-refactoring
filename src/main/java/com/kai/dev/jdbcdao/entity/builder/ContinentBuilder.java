package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.entity.Continent;
import com.kai.dev.jdbcdao.entity.Identifiable;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ContinentBuilder implements EntityBuilder {

    @Override
    public Identifiable build(ResultSet continentResultSet) throws SQLException {
        
        Continent continent = new Continent();
        
        Integer id = (Integer) continentResultSet.getObject(Continent.FIELD_ID);
        continent.setId(id);
        
        String name = continentResultSet.getString(Continent.FIELD_NAME);
        continent.setName(name);
        
        return continent;
    }    
}
