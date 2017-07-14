package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.entity.Identifiable;
import com.kai.dev.jdbcdao.entity.Location;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LocationBuilder implements EntityBuilder {

    @Override
    public Identifiable build(ResultSet locationResultSet) throws SQLException {
        
        Location location = new Location();
        
        Integer id = (Integer) locationResultSet.getObject(Location.FIELD_ID);
        location.setId(id);
        
        String name = locationResultSet.getString(Location.FIELD_NAME);
        location.setName(name);
        
        Integer continentId = (Integer) locationResultSet.getObject(Location.FIELD_CONTINENT_ID);
        location.setContinentId(continentId);
        
        return location;
    }
}
