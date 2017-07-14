package com.kai.dev.jdbcdao.dao;

import com.kai.dev.jdbcdao.entity.Location;
import com.kai.dev.jdbcdao.entity.Server;
import java.util.List;
import java.util.Properties;


public class ServerDAO extends AbstractDAO<Server> {

    public ServerDAO(){
        super(Server.class);
    }
    
    public Server getByIdWithLocationAndContinent(int serverId){
        Server server = getById(serverId);
        int locationId = server.getLocationId();        
        LocationDAO locationDAO = DAOFactory.INSTANCE.getLocationDAO();
        Location location = locationDAO.getByIdWithContinent(locationId);
        server.setLocation(location);
        return server;
    }
    
    public List<Server> getAllByLocationId(int locationId){
        String id = String.valueOf(locationId);
        Properties conditions = new Properties();
        conditions.put(Server.FIELD_LOCATION_ID, id);
        List<Server> servers = getAllByConditions(conditions);
        return servers;
    }
    
    public List<Server> getAllWithLocations(){
        LocationDAO locationDAO = DAOFactory.INSTANCE.getLocationDAO();
        List<Server> servers = getAll();
        for (Server server : servers){
            int locationId = server.getLocationId();
            Location location = locationDAO.getById(locationId);
            server.setLocation(location);
        }
        return servers;
    }  
}
