package com.kai.dev.jdbcdao.entity;

import java.util.ArrayList;
import java.util.List;


public class Continent implements Identifiable {

    public static final String TABLE_NAME = "continent";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    
    private Integer id;
    private String name;
    private List<Location> locations;
    
    public Continent(){
        locations = new ArrayList<>();
    }

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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }        
}
