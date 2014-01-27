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
package eu.trentorise.opendata.opendatarise.semantics.model.knowledge;

import java.util.Map;

/**
 *
 * Holds the context of a resource, divided in primary and secondary. Typically
 * the context is metadata from the catalog where the resource was found.
 *
 * @author David Leoni <david.leoni@trentorise.eu>
 * @date Jan 27, 2014
 */
public interface IResourceContext {

    /**
     * Returns the primary context of the resource
     *
     * @return The primary context of the resource.
     */
    Map<String, String> getPrimaryContext();

    /**
     * Sets the primary context of the resource
     *
     * @param context The primary ocntext of the resource to set
     */
    void setPrimaryContext(Map<String, String> context);

    /**
     * Returns the primary context of the resource
     *
     * @return The primary context of the resource.
     */
    Map<String, String> getSecondaryContext();

    /**
     * Sets the secondary context of the resource
     *
     * @param context The secondary ocntext of the resource to set
     */
    void setSecondaryContext(Map<String, String> context);
}
