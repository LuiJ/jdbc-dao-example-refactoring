package com.kai.dev.jdbcdao.entity;

import java.util.ArrayList;
import java.util.List;


public class Continent extends Entity 
{
    public static final String TABLE_NAME = "continent";
    public static final String FIELD_NAME = "name";
    
    private String name;
    private List<Location> locations;
    
    public Continent(){
        locations = new ArrayList<>();
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
