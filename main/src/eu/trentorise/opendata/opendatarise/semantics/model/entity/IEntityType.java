/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.model.entity;

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.IConcept;
import java.util.List;

/**
 *
 * @author Juan
 */
public interface IEntityType {
    
    public Long getGUID();
    
    public String getURI();

    public String getName();
    
    public IConcept getConcept();
    
    public void setConcept(IConcept concept);
    
    public List<IAttributeDef> getAttributeDefs();

    public void addAttributeDef(IAttributeDef attrDef);
    
    public void removeAttributeDef(IAttributeDef attrDef);
    
    public List<IMatchingSet> getMatchingSets();
    
    public void removeMatchingSet(IMatchingSet matchingSet);
    
    public void addMatchingSet(IMatchingSet matchingSet);

}
