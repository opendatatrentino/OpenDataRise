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

package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntity;
import java.util.List;

/**
 * Interface for entity search services
 * 
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author David Leoni <david.leoni@trentorise.eu>
 * @date Sep 23, 2013
 *
 */
public interface ISearch {

    /**
     * Performs a search for entities using EQL syntax
     *
     * @param eqlQuery A string query using EQL syntax to be executed
     * @return the list of entities that match the query
     */
    List<IEntity> searchEQL(String eqlQuery);
    
    /**
     * Performs a concept search for entities 
     *
     * @param conceptSearchQuery A string query using concept search syntax to be executed
     * @return the list of entities that match the query
     */
    List<IEntity> conceptSearch(String conceptSearchQuery);
}
