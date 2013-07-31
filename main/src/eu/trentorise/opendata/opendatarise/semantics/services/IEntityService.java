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
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IAttribute;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntity;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IValue;

/**
 * Entity services allow CRUD on entities and attributes
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IEntityService {

    /**
     * Creates an entity
     *
     * @param entity the entity to be created
     */
    void createEntity(IEntity entity);

    /**
     * Updates an entity
     *
     * @param entity the entity to be updated
     */
    void updateEntity(IEntity entity);

    /**
     * Deletes an entity
     *
     * @param entityID the local ID of the entity to be deleted
     */
    void deleteEntity(long entityID);

    /**
     * Reads an entity given its local entity ID
     *
     * @param entityID the local ID of the entity
     * @return the entity which has this ID, or null if not found
     */
    IEntity readEntity(long entityID);

    /**
     * Adds an attribute to an entity
     *
     * @param entity the entity that
     * @param attribute
     */
    void addAttribute(IEntity entity, IAttribute attribute);

    /**
     * Adds an attribute value to an attribute in an entity
     *
     * @param entity the entity which owns the attribute
     * @param attribute the attribute that owns the value
     * @param value the value to be added
     */
    void addAttributeValue(IEntity entity, IAttribute attribute,
            IValue value);

    /**
     * Updates an attribute value in an attribute in an entity
     *
     * @param entity the entity which owns the attribute
     * @param attribute the attribute that owns the value
     * @param newValue the value to be updated
     */
    void updateAttributeValue(IEntity entity, IAttribute attribute,
            IValue newValue);
}
