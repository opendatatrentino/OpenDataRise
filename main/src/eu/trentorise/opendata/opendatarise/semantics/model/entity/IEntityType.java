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

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.IConcept;
import java.util.List;
import java.util.Locale;

/**
 * The entity type defines the attributes that the entity can have
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IEntityType {

    /**
     * Gets the Globally Unique Identifier (GUID) for the entity type
     *
     * @return the Globally Unique Identifier (GUID) represented as Long
     */
    Long getGUID();

    /**
     * Gets the URI of the entity type
     *
     * @return a string that holds the URI of the entity type
     */
    String getURI();

    /**
     * Gets the name of the entity type in the given language
     *
     * @param locale the language used to return the entity type name
     * @return the name of the entity type
     */
    String getName(Locale locale);

    /**
     * Gets the concept of the entity type
     *
     * @return the concept of the entity type
     */
    IConcept getConcept();

    /**
     * Sets the concept of the entity type
     *
     * @param concept the concept of the entity type
     */
    void setConcept(IConcept concept);

    /**
     * Gets the attribute definitions for the entity type
     *
     * @return the attribute definitions for the entity type
     */
    List<IAttributeDef> getAttributeDefs();

    /**
     * Adds an attribute definition to the entity type
     *
     * @param attrDef the attribute definition to be added
     */
    void addAttributeDef(IAttributeDef attrDef);

    /**
     * Removes an attribute definition from the entity type
     *
     * @param attrDefID the local ID of the attribute definition to be removed
     */
    void removeAttributeDef(long attrDefID);

    /**
     * Gets the unique indexes
     *
     * @return the unique indexes
     */
    List<IUniqueIndexes> getUniqueIndexes();

    /**
     * Removes Unique Indexes
     *
     * @param uniqueIndexesID the local ID of the unique indexes to be removed
     */
    void removeUniqueIndexes(long uniqueIndexesID);

    /**
     * Adds Unique Indexes
     *
     * @param uniqueIndexes the unique indexes to be added
     */
    void addUniqueIndexes(IUniqueIndexes uniqueIndexes);
}
