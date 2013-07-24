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
public interface IAttribute {
    
    public Long getGUID();

    public IAttributeDef getAttributeDefinition();

    public void setAttributeDefinition(IAttributeDef ad);

    public void addValue(IValue value);
    
    public void removeValue(IValue value);
    
    public List<IValue> getValues();

    public String getFirstValue();

    public Long getValuesCount();

}
