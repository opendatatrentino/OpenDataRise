/**
 * *****************************************************************************
 * Copyright 2012-2013 Trento Rise (www.trentorise.eu/)
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License (LGPL)
 * version 2.1 which accompanies this distribution, and is available at
 *
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.model.entity;

import java.util.List;

/**
 * An entity is a representation of real world object.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IEntity {

    /**
     * Gets the local identifier for the entity
     *
     * @return the local identifier as Long
     */
    Long getLocalID();

    /**
     * Gets the Globally Unique Identifier (GUID) for the entity
     *
     * @return the Globally Unique Identifier (GUID) represented as Long
     */
    Long getGUID();

    /**
     * Gets the URI of the entity
     *
     * @return a string that holds the URI of the entity
     */
    String getURI();

    /**
     * Gets the URL of the entity
     *
     * @return a string that holds the URL of the entity
     */
    String getURL();

    /**
     * Sets the URL of the entity
     *
     * @param url a string that holds the URL of the entity
     */
    void setURL(String url);

    /**
     * Gets the external identifier of the entity
     * 
     * @return a string representing the external identifier of the entity
     */
    String getExternalID();

    /**
     * Sets the external identifier of the entity
     * 
     * @param externalId a string representing the external identifier of the entity
     */
    void setExternalID(String externalID);

    /**
     * Gets the list of attributes in this entity
     * 
     * @return the list of attributes in this entity
     */
    List<IAttribute> getAttributes();

    /**
     * Sets the list of attributes in this entity
     * 
     * @param attributes the list of attributes to be set in the entity
     */
    void setAttributes(List<IAttribute> attributes);

    /**
     * Adds an attribute to the list of attributes in this entity
     * 
     * @param attribute the attribute to be added
     */
    void addAttribute(IAttribute attribute);

    /**
     * Gets the entity type
     * 
     * @return the entity type
     */
    IEntityType getEtype();

    /**
     * Sets the entity type
     * 
     * @param type the entity type
     */
    void setEtype(IEntityType type);
}
