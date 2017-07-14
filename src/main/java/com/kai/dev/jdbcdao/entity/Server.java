package com.kai.dev.jdbcdao.entity;


public class Server implements Identifiable {
    
    public static final String TABLE_NAME = "server";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_LOCATION_ID = "location_id";
    
    private Integer id;
    private String name;
    private Integer locationId;
    private Location location;
    
    public Server(){}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }           
}
