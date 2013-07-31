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

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntityType;
import eu.trentorise.opendata.opendatarise.semantics.services.model.ICorrespondence;
import java.util.List;

/**
 * A service that performs semantic matching between two schemas
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface ISemanticMatchingService {

    /*
     * Returns the correspondence between the source and the target contexts. 
     * The first context is given by a root node and a list of columns. The 
     * second context is given by the definition of an entity type
     * 
     * @param sourceName - The name of the root node in the source tree
     * @param sourceNodes - Names of the source nodes under the source root node
     * @param targetEntityType - the target entity type
     * 
     * @return - the correspondence
     */
    ICorrespondence matchSchemas(String sourceName, List<String> sourceColumns,
            IEntityType targetEntityType);
}