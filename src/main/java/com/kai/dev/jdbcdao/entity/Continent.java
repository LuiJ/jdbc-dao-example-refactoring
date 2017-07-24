package com.kai.dev.jdbcdao.entity;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.annotation.Table;
import java.util.ArrayList;
import java.util.List;


@Table(name = "continent")
public class Continent extends Entity 
{
    public static final String TABLE_NAME = "continent";
    public static final String FIELD_NAME = "name";
    
    @Column(name = "name")
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
