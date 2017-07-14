package com.kai.dev.jdbcdao.entity;

import java.util.ArrayList;
import java.util.List;


public class Location implements Identifiable {

    public static final String TABLE_NAME = "location";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CONTINENT_ID = "continent_id";
    
    private Integer id;
    private String name;
    private Integer continentId;
    private Continent continent;
    private List<Server> servers;
    
    public Location(){
        servers = new ArrayList<>();
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

    public Integer getContinentId() {
        return continentId;
    }

    public void setContinentId(Integer continentId) {
        this.continentId = continentId;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }    
}
