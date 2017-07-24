package com.kai.dev.jdbcdao.entity;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.annotation.Table;
import java.util.ArrayList;
import java.util.List;


@Table(name = "location")
public class Location extends Entity 
{
    public static final String TABLE_NAME = "location";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CONTINENT_ID = "continent_id";
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "continent_id")
    private Integer continentId;
        
    private Continent continent;
    private List<Server> servers;
    
    public Location(){
        servers = new ArrayList<>();
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
