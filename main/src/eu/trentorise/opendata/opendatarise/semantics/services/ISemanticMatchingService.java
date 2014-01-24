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

import eu.trentorise.opendata.jackan.ckan.CkanDataset;
import eu.trentorise.opendata.jackan.ckan.CkanResource;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntityType;
import eu.trentorise.opendata.opendatarise.semantics.services.model.ICorrespondence;
import java.util.List;

/**
 * A service that performs semantic matching between two schemas
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author David Leoni <david.leoni@trentorise.eu>
 * @date Jan 24, 2014
 */
public interface ISemanticMatchingService {

    /**
     * Given metadata and column headers names of an input resource of ckan, along with the url of the catalog of provenance, it guesses a target entitytype and returns a matching between the source headers and the attributes of the target entity type.
     
     * @param catalogUrl
     * @param ckanDataset the dataset of the resource
     * @param ckanResource a ckan resource
     * @param sourceColumns the names of the resource columns
     * @return  - the correspondence
     */
    
    ICorrespondence matchSchemas(String catalogUrl, CkanDataset ckanDataset, CkanResource ckanResource, List<String> sourceColumns);
}