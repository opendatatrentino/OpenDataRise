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

/**
* TODO Moaz
*/
package eu.trentorise.opendata.opendatarise.semantics.model.knowledge;

import java.util.Locale;

/**
* TODO Moaz
 * 
 * 
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @version July, 2013
 */
public interface IConcept {

    /**
     * TODO Moaz
     */
    String getSynsetUri();
    

    /**
     * TODO Moaz
     */
    String getCommonlyReferredAs(Locale language);

    /**
     * TODO Moaz
     */
    String getSummary(Locale language);

    /**
     * TODO Moaz
     */
    String getDescription(Locale language);

    /**
     * TODO Moaz
     */
    String getPartOfSpeach(Locale language);
}
