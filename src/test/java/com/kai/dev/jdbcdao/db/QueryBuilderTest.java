package com.kai.dev.jdbcdao.db;

import com.kai.dev.jdbcdao.entity.Server;
import java.util.Properties;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class QueryBuilderTest
{
    private static final Integer ID = 5;
    private static final String NAME = "Server #5";
    private static final Integer LOCATION_ID = 15;
    
    private static final String EXPECTED_SELECT_QUERY = "SELECT * FROM " + Server.TABLE_NAME + " "
                                                      + "WHERE " + Server.FIELD_NAME + "='" + NAME + "' AND "
                                                      + Server.FIELD_LOCATION_ID + "='" + LOCATION_ID + "'";
    
    private static final String EXPECTED_SELECT_ALL_QUERY = "SELECT * FROM " + Server.TABLE_NAME;
    
    private static final String EXPECTED_INSERT_QUERY = "INSERT INTO " + Server.TABLE_NAME + " "
                                                      + "(" + Server.FIELD_NAME + ", " 
                                                      + Server.FIELD_LOCATION_ID + ") "
                                                      + "VALUES ('" + NAME + "', "
                                                      + "'" + LOCATION_ID + "')";
    
    private static final String EXPECTED_UPDATE_QUERY = "UPDATE " + Server.TABLE_NAME + " "
                                                      + "SET " + Server.FIELD_NAME + "='" + NAME + "', "
                                                      + Server.FIELD_LOCATION_ID + "='" + LOCATION_ID + "' "
                                                      + "WHERE " + Server.FIELD_ID + "='" + ID + "'";
    
    private static final String EXPECTED_DELETE_QUERY = "DELETE FROM " + Server.TABLE_NAME + " "
                                                      + "WHERE " + Server.FIELD_ID + "='" + ID + "'";
    
    private static QueryBuilder<Server> queryBuilder;
    
    @BeforeClass
    public static void setUp()
    {
        queryBuilder = new QueryBuilder<>(Server.class);
    }
    
    @Test
    public void shouldBuildSelectQuery()
    {
        Properties selectConditions = new Properties();
        selectConditions.put(Server.FIELD_NAME, NAME);
        selectConditions.put(Server.FIELD_LOCATION_ID, String.valueOf(LOCATION_ID));
        
        String actualQuery = queryBuilder.buildSelectQuery(selectConditions);
        String failureMessage = "Exp: " + EXPECTED_SELECT_QUERY + "\n"
                              + "Act: " + actualQuery;
        Assert.assertTrue(failureMessage, EXPECTED_SELECT_QUERY.equals(actualQuery));
    }
    
    @Test
    public void shouldBuildSelectAllQuery()
    {
        String actualQuery = queryBuilder.buildSelectAllQuery();
        String failureMessage = "Exp: " + EXPECTED_SELECT_ALL_QUERY + "\n"
                              + "Act: " + actualQuery;
        Assert.assertTrue(failureMessage, EXPECTED_SELECT_ALL_QUERY.equals(actualQuery));
    }
    
    @Test
    public void shouldBuildInsertQuery()
    {
        Server server = new Server();
        server.setName(NAME);
        server.setLocationId(LOCATION_ID);
        
        String actualQuery = queryBuilder.buildInsertQuery(server);
        String failureMessage = "Exp: " + EXPECTED_INSERT_QUERY + "\n"
                              + "Act: " + actualQuery;
        Assert.assertTrue(failureMessage, EXPECTED_INSERT_QUERY.equals(actualQuery));
    }
}
