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
 * The matching set are a set of attribute definitions to be used in entity
 * matching
 *
 * @author Juan
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IMatchingSet {

    /**
     * Gets the Globally Unique Identifier (GUID) for the Matching Set
     *
     * @return the Globally Unique Identifier (GUID) represented as Long
     */
    Long getGUID();

    /**
     * Gets the URI of the Matching Set
     *
     * @return a string that holds the URI of the Matching Set
     */
    String getURI();

    /**
     * Gets the attribute definitions for the Matching Set
     *
     * @return the attribute definitions for the Matching Set
     */
    List<IAttributeDef> getAttributeDefs();

    /**
     * Adds an attribute definition to the Matching Set
     *
     * @param attrDef the attribute definition to be added
     */
    void addAttributeDef(IAttributeDef attrDef);

    /**
     * Removes an attribute definition from the Matching Set
     *
     * @param attrDef the attribute definition to be removed
     */
    void removeAttributeDef(IAttributeDef attrDef);
}
