package com.kai.dev.jdbcdao.entity;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.annotation.Table;


@Table(name = "server")
public class Server extends Entity 
{    
    public static final String TABLE_NAME = "server";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_LOCATION_ID = "location_id";
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "location_id")
    private Integer locationId;
    
    private Location location;

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public Integer getLocationId() 
    {
        return locationId;
    }

    public void setLocationId(Integer locationId) 
    {
        this.locationId = locationId;
    }

    public Location getLocation() 
    {
        return location;
    }

    public void setLocation(Location location) 
    {
        this.location = location;
    }           
}
