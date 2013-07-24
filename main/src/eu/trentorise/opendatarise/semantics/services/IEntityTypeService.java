/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IAttributeDef;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntityType;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IMatchingSet;
import java.util.List;

/**
 *
 * @author Juan
 */
public interface IEntityTypeService {
    
    public List<IEntityType> getAllEntityTypes();
    
    public void addAttributeDefToEtype(IEntityType entityType, IAttributeDef attrDef);
    
    public void addMatchingSetToEtype(IEntityType entityType, IMatchingSet matchingSet);
    
    
}
