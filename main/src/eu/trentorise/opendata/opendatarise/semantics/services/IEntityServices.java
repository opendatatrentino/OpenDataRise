/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IAttribute;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntity;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IValue;

/**
 *
 * @author Juan
 */
public interface IEntityServices {
    
    public void createEntity(IEntity entity);
    
    public void updateEntity(IEntity entity);
    
    public void deleteEntity(IEntity entity);
    
    public void readEntity(IEntity entity);
    
    public void addAttribute(IEntity entity, IAttribute attribute);
    
    public void addAttributeValue(IEntity entity, IAttribute attribute, 
            IValue value);
    
    public void updateAttributeValue(IEntity entity, IAttribute attribute, 
            IValue newValue);
    
}
