package com.kai.dev.jdbcdao.db;

import com.kai.dev.jdbcdao.entity.Server;
import java.util.Properties;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class QueryBuilderTest
{
    private static final int ID = 5;
    private static final String NAME = "Server #5";
    private static final int LOCATION_ID = 15;
    
    private static final String EXPECTED_SELECT_QUERY = "SELECT * FROM " + Server.TABLE_NAME + " "
                                                      + "WHERE name='" + NAME + "' AND "
                                                      + "location_id='" + LOCATION_ID + "'";
    
    private static final String EXPECTED_SELECT_ALL_QUERY = "SELECT * FROM " + Server.TABLE_NAME;
    
    private static final String EXPECTED_INSERT_QUERY = "INSERT INTO " + Server.TABLE_NAME + " "
                                                      + "('id', 'name', 'location_id') "
                                                      + "VALUES ('" + ID + "', '" + NAME + ""
                                                      + "', '" + LOCATION_ID + "')";
    
    private static final String EXPECTED_UPDATE_QUERY = "UPDATE " + Server.TABLE_NAME + " "
                                                      + "SET name='" + NAME + "', "
                                                      + "location_id='" + LOCATION_ID + "' "
                                                      + "WHERE id='" + ID + "'";
    
    private static final String EXPECTED_DELETE_QUERY = "DELETE FROM " + Server.TABLE_NAME + " "
                                                      + "WHERE id='" + ID + "'";
    
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
}
