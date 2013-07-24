/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.model.entity;

import java.util.List;

/**
 *
 * @author Juan
 */
public interface IMatchingSet {
    
    public Long getGUID();
    
    public String getURI();
    
    public List<IAttributeDef> getAttributeDefs();

    public void addAttributeDef(IAttributeDef attrDef);
    
    public void removeAttributeDef(IAttributeDef attrDef);
}
