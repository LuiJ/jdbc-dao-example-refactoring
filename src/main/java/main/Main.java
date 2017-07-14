package main;

import com.kai.dev.jdbcdao.dao.ContinentDAO;
import com.kai.dev.jdbcdao.dao.DAOFactory;
import com.kai.dev.jdbcdao.dao.LocationDAO;
import com.kai.dev.jdbcdao.dao.ServerDAO;
import com.kai.dev.jdbcdao.entity.Continent;
import com.kai.dev.jdbcdao.entity.Location;
import com.kai.dev.jdbcdao.entity.Server;
import java.util.List;

public class Main {
    
    public static void main(String[] args){
        
        
//        // ServerDAO         
//        ServerDAO serverDAO = DAOFactory.INSTANCE.getServerDAO();
//        
//        Server server = serverDAO.getByIdWithLocationAndContinent(1);
//        String serverName = server.getName();
//        Location location = server.getLocation();
//        String locationName = location.getName();
//        Continent continent = location.getContinent();
//        String continentName = continent.getName();
//        System.out.println("----------------------------------");
//        System.out.println("Continent: " + continentName);
//        System.out.println("    Location: " + locationName);
//        System.out.println("        Server: " + serverName);
//        System.out.println("----------------------------------");
//        
//        Server server = new Server();
//        server.setName("Srv 2.4");
//        server.setLocationId(2);
//        int newServerId = serverDAO.save(server);
//        System.out.println(newServerId);
        
        
        
//        // LocationDAO
//        LocationDAO locationDAO = DAOFactory.INSTANCE.getLocationDAO();
//        
//        Location location = locationDAO.getByIdWithContinentAndServers(3);
//        String locationName = location.getName();
//        Continent continent = location.getContinent();
//        String continentName = continent.getName();  
//        List<Server> servers = location.getServers();
//        System.out.println("----------------------------------");
//        System.out.println("Continent: " + continentName);
//        System.out.println("    Location: " + locationName);
//        for (Server server : servers){
//            String serverName = server.getName();
//            System.out.println("        Server: " + serverName);
//        }
//        System.out.println("----------------------------------");
//        
//        Location location = new Location();
//        location.setName("Spain, Barcelona");
//        location.setContinentId(2);
//        int newLocationId = locationDAO.save(location);
//        System.out.println(newLocationId);
        
        
        
        // ContinentDAO
        ContinentDAO continentDAO = DAOFactory.INSTANCE.getContinentDAO();
        
        Continent continent = continentDAO.getByIdWithLocationsAndServers(1);
        String continentName = continent.getName(); 
        List<Location> locations = continent.getLocations();
        System.out.println("----------------------------------");
        System.out.println("Continent: " + continentName);
        for (Location location : locations){
            String locationName = location.getName();
            System.out.println("    Location: " + locationName);
            List<Server> servers = location.getServers();
            for (Server server : servers){
                String serverName = server.getName();
                System.out.println("        Server: " + serverName);
            }
        }
        System.out.println("----------------------------------");
        
    }    
}
