/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.model.entity;

/**
 *
 * @author Juan
 */
public interface IValue {
    public Long getGUID();
    
    public Object getValue();
    
    public void setValue(Object value);
}
