package com.kai.dev.jdbcdao.dao;


public enum DAOFactory {
    
    INSTANCE;
    
    private ServerDAO serverDAO;
    private LocationDAO locationDAO;
    private ContinentDAO continentDAO;

    public ServerDAO getServerDAO() {
        if (serverDAO == null){
            serverDAO = new ServerDAO();
        }
        return serverDAO;
    }

    public LocationDAO getLocationDAO() {
        if (locationDAO == null){
            locationDAO = new LocationDAO();
        }
        return locationDAO;
    }

    public ContinentDAO getContinentDAO() {
        if (continentDAO == null){
            continentDAO = new ContinentDAO();
        }
        return continentDAO;
    }            
}
