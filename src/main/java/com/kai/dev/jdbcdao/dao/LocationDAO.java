package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.entity.Continent;
import com.kai.dev.jdbcdao.entity.Location;
import com.kai.dev.jdbcdao.entity.Server;
import java.util.List;
import java.util.Properties;


public class LocationDAO extends AbstractDAO<Location> {

    public LocationDAO(){
        super(Location.class);
    }
    
    public List<Location> getAllByContinentIdWithServers(int continentId){
        String id = String.valueOf(continentId);
        Properties conditions = new Properties();
        conditions.put(Location.FIELD_CONTINENT_ID, id);
        List<Location> locations = getAllByConditions(conditions);
        ServerDAO serverDAO = DAOFactory.INSTANCE.getServerDAO();
        for (Location location : locations){
            int locationId = location.getId();
            List<Server> servers = serverDAO.getAllByLocationId(locationId);
            location.setServers(servers);
        }
        return locations;
    }
    
    public Location getByIdWithContinent(int locationId){
        Location location = getById(locationId);
        int continentId = location.getContinentId();
        ContinentDAO continentDAO = DAOFactory.INSTANCE.getContinentDAO();
        Continent continent = continentDAO.getById(continentId);
        location.setContinent(continent);
        return location;
    }
    
    public Location getByIdWithContinentAndServers(int locationId){
        Location location = getByIdWithContinent(locationId);
        ServerDAO serverDAO = DAOFactory.INSTANCE.getServerDAO();
        List<Server> servers = serverDAO.getAllByLocationId(locationId);
        location.setServers(servers);
        return location;
    }
    
    public List<Location> getAllWithServers(){
        ServerDAO serverDAO = DAOFactory.INSTANCE.getServerDAO();
        List<Location> locations = getAll();
        for (Location location : locations){
            int locationId = location.getId();
            List<Server> servers = serverDAO.getAllByLocationId(locationId);
            location.setServers(servers);
        }
        return locations;
    }
    
    public List<Location> getAllWithContinentsAndServers(){
        ContinentDAO continentDAO = DAOFactory.INSTANCE.getContinentDAO();
        List<Location> locations = getAllWithServers();
        for (Location location : locations){
            int continentId = location.getId();
            Continent continent = continentDAO.getById(continentId);
            location.setContinent(continent);
        }
        return locations;
    }
    
}
