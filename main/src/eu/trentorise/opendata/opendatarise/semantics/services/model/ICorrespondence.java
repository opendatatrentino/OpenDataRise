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

import java.util.List;

/**
 * <i>ICorrespondence</i> represents the output of the semantic matching. It is 
 * a list of <i>ICorrespondenceItem</i>s.
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @date Jul 24, 2013
 */
public interface ICorrespondence {

    /**
     * Sets the list of correspondence items
     *
     * @param correspondenceItems the list of correspondence items
     */
    void setCorrespondenceItems(List<ICorrespondenceItem> correspondenceItems);

    /**
     * Gets the list of correspondence items
     *
     * @return the list of correspondence items
     */
    List<ICorrespondenceItem> getCorrespondenceItems();
}