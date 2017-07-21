package com.kai.dev.jdbcdao.entity.parser;

import com.kai.dev.jdbcdao.entity.Server;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class EntityParserTest
{
    private static final Integer ID = 3;
    private static final String NAME = "Server #6";
    private static final Integer LOCATION_ID = 11;
    
    private static EntityParser<Server> entityParser;
    private static Map<String, String> expectedParsedEntity;
    
    @BeforeClass
    public static void setUp()
    {
        entityParser = new EntityParser<>();    
        expectedParsedEntity = new LinkedHashMap<>();
        expectedParsedEntity.put(Server.FIELD_ID, "'" + ID + "'");
        expectedParsedEntity.put(Server.FIELD_NAME, "'" + NAME + "'");
        expectedParsedEntity.put(Server.FIELD_LOCATION_ID, "'" + LOCATION_ID + "'");
    }
    
    @Test
    public void shouldParseEntity()
    {
        Server server = new Server();
        server.setId(ID);
        server.setName(NAME);
        server.setLocationId(LOCATION_ID);
        
        Map<String, String> parsedEntity = entityParser.parse(server);
        String failureMessage = "Parsed Entity has more/less properties";
        Assert.assertTrue(failureMessage, 
                          expectedParsedEntity.size() == parsedEntity.size());
        
        Set<String> fieldsNames = expectedParsedEntity.keySet();
        for (String field : fieldsNames)
        {
            failureMessage = "Parsed Entity is incorrect";
            Assert.assertTrue(failureMessage, 
                expectedParsedEntity.get(field).equals(parsedEntity.get(field)));
        }
    }
}
