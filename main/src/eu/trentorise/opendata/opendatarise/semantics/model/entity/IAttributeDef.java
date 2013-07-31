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

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.IConcept;
import java.util.Locale;

/**
 * The attribute definition stores information about the attributes, such as the
 * Name, the Concept and if the attribute is set or not.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IAttributeDef {

    /**
     * Gets the Globally Unique Identifier (GUID) for the attribute definition
     *
     * @return the Globally Unique Identifier (GUID) represented as Long
     */
    Long getGUID();

    /**
     * Gets the URI of the attribute definition
     *
     * @return a string that holds the URI of the attribute definition
     */
    String getURI();

    /**
     * Gets the attribute name in the given language
     *
     * @param locale the language used to return the attribute name
     * @return the attribute name as string
     */
    String getName(Locale locale);

    /**
     * Gets the data type of the attribute definition
     *
     * @return the data type as string
     */
    String getDataType();

    /**
     * Gets the concept of the attribute definition
     *
     * @return the concept of the attribute definition
     */
    IConcept getConcept();

    /**
     * Gets the IsSet flag that tells if the attribute can hold a set of values
     * or it can hole only one value.
     *
     * @return true if the attribute can hold a set or false if it can hold only
     * one value.
     */
    boolean isSet();

    /**
     * Gets the regular expression that all the attribute values should follow
     *
     * @return the regular expression as string
     */
    String getRegularExpression();

    /**
     * Set the regular expression that all the attribute values should follow
     *
     * @param regularExpression the regular expression as string
     */
    void setRegularExpressin(String regularExpression);
    
    /**
     * Gets the IsMandatory flag that tells if the attribute is mandatory or not
     *
     * @return true if the attribute is mandatory or false if it is not
     * mandatory
     */
    boolean isMandatory();
}
