package com.kai.dev.jdbcdao.entity.builder;

import com.kai.dev.jdbcdao.entity.Continent;
import com.kai.dev.jdbcdao.entity.Location;
import com.kai.dev.jdbcdao.entity.Server;


public class EntityBuilderFactory {
    
    public static EntityBuilder create(Class type){
        
            EntityBuilder builder = null;
            
            if (type == Server.class){
                builder = new ServerBuilder();
            }
            else if (type == Location.class){
                builder = new LocationBuilder();
            }
            else if (type == Continent.class){
                builder = new ContinentBuilder();
            }
            else {
                throw new IllegalArgumentException("Incorrect entity type.");
            }

            return builder;        
    }     
}
