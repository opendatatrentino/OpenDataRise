/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntity;

/**
 *
 * @author Juan
 */
public interface IIdentityServices {
    
    public Long createGUID(IEntity entity);
    
}
