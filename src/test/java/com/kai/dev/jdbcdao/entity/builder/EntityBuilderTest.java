package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.entity.Server;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class EntityBuilderTest 
{
    private static final Integer ID = 3;
    private static final String NAME = "Server #6";
    private static final Integer LOCATION_ID = 11;
    
    private static EntityBuilder<Server> entityBuilder;
    private static ResultSetStub resultSetStub;
    
    @BeforeClass
    public static void setUp()
    {
        entityBuilder = new EntityBuilder<>(Server.class);   
        
        resultSetStub = new ResultSetStub();
        resultSetStub.put("id", ID);
        resultSetStub.put("name", NAME);
        resultSetStub.put("location_id", LOCATION_ID);
    }
    
    @Test
    public void shouldBuildEntity()
    {
        try 
        {
            Server server = entityBuilder.build(resultSetStub);
            
            String failureMessage = "Entity builded incorrectly";
            Assert.assertTrue(failureMessage, ID.equals(server.getId()));
            Assert.assertTrue(failureMessage, NAME.equals(server.getName()));
            Assert.assertTrue(failureMessage, LOCATION_ID.equals(server.getLocationId()));
        }
        catch (SQLException e)
        {
            Assert.assertTrue(e.getMessage(), false);
        }
    }
}
