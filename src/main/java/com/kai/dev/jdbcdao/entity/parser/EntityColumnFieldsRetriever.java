package com.kai.dev.jdbcdao.entity.parser;

import com.kai.dev.jdbcdao.annotation.Column;
import com.kai.dev.jdbcdao.entity.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class EntityColumnFieldsRetriever<T extends Entity>
{
    public Collection<Field> retrieve(T entity)
    {
        Collection<Field> columnFields = new ArrayList<>();
        Collection<Field> fields = getFields(entity);
        for (Field field : fields)
        {
            if (isNotSuitableField(field))
            {
                continue;
            }
            columnFields.add(field);
        }
        return columnFields;
    }
    
    
    private Collection<Field> getFields(T entity)
    {
        Collection<Field> fields = new ArrayList<>();
        fields.addAll(getSuperclassFields(entity));
        fields.addAll(getClassFields(entity));
        return fields;
    }
    
    
    private List<Field> getSuperclassFields(T entity)
    {
        return Arrays.asList(entity.getClass()
                                   .getSuperclass()
                                   .getDeclaredFields());
    }
    
    
    private List<Field> getClassFields(T entity)
    {
        return Arrays.asList(entity.getClass()
                                   .getDeclaredFields());
    }
    
    
    private boolean isNotSuitableField(Field field)
    {
        boolean doesntHaveColumnAnnotation = 
                field.getDeclaredAnnotation(Column.class) == null;
        boolean isEntity = Entity.class
                               .isAssignableFrom(field.getType());
        boolean isCollection = Collection.class
                               .isAssignableFrom(field.getType());
        return  doesntHaveColumnAnnotation || isCollection || isEntity;
    }
}
