/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.model.entity;

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.IConcept;

/**
 *
 * TODO MOAZ
 */
public interface IAttributeDef {
    
    
    public Long getGUID();

    public String getURI();
    
    public String getName();

    public String getDataType();

    public IConcept getConcept();

    public Boolean getIsSet();
    
    public String getRegularExpressin();
    
    public void setRegularExpressin(String regularExpression);
    
}
