package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.entity.Continent;
import com.kai.dev.jdbcdao.entity.Location;
import java.util.List;


public class ContinentDAO extends AbstractDAO<Continent> {

    public ContinentDAO(){
        super(Continent.class);
    }
    
    public Continent getByIdWithLocationsAndServers(int continentId){
        Continent continent = getById(continentId);
        LocationDAO locationDAO = DAOFactory.INSTANCE.getLocationDAO();
        List<Location> locations = locationDAO.getAllByContinentIdWithServers(continentId);
        continent.setLocations(locations);
        return continent;
    }
    
}
