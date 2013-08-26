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

package eu.trentorise.opendata.opendatarise.semantics.model.entity;

import java.util.List;

/**
 * An attribute is used in entities. It holds one or more values.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @date Jul 24, 2013
 */
public interface IAttribute {

    /**
     * Gets the Globally Unique Identifier (GUID) for the attribute
     *
     * @return the Globally Unique Identifier (GUID) represented as Long
     */
    Long getGUID();

    /**
     * Gets the attribute definition of the attribute
     *
     * @return the attribute definition of the attribute
     */
    IAttributeDef getAttributeDefinition();

    /**
     * Sets the attribute definition of the attribute
     *
     * @param ad the attribute definition of the attribute
     */
    void setAttributeDefinition(IAttributeDef ad);

    /**
     * Adds a value to the attribute
     *
     * @param value the value to be added
     */
    void addValue(IValue value);

    /**
     * Removes a value from the attribute
     *
     * @param valueID the local ID of the value to be removed
     */
    void removeValue(long valueID);

    /**
     * Returns all the value of the attribute
     *
     * @return list of all values of the attribute
     */
    List<IValue> getValues();

    /**
     * Gets the first value of the attribute
     *
     * @return the first value of the attribute
     */
    String getFirstValue();

    /**
     * Gets the number of values in the attribute
     *
     * @return the number of values in the attribute
     */
    Long getValuesCount();
}
