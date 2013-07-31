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

package eu.trentorise.opendata.opendatarise.semantics.services.model;

/**
 * <i>ICorrespondenceItem</i> represents an item in the <i>ICorrespondence</i>.
 * It is a tuple of Source, Relation and Target.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface ICorrespondenceItem {

    /**
     * Gets the source string in the Correspondence item
     *
     * @return the source string
     */
    String getSource();

    /**
     * Gets the target string in the Correspondence item
     *
     * @return the target string
     */
    String getTarget();

    /**
     * Gets the relation character in the Correspondence item. It can be one of
     * the following characters: =<>!LMX?
     *
     * @return the relation character
     */
    char getRelation();
}
