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
 * TODO Moaz
 */
package eu.trentorise.opendata.opendatarise.semantics.model.knowledge;

import java.util.List;

/**
 * TODO Moaz
 * 
 * 
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @version July, 2013
 */
public interface IWord {
    
    /**
     * TODO Moaz
     */
    String getLemma();

    /**
     * @return a list of concepts which connected with the given term
     * @see Term
     * @see Sense
     */
    List<IConcept> getSenses();

    /**
     * @return the concept from the list of concepts which more specific for the given term
     * @see Sense
     */
    IConcept getSelectedSense();

     /**
     * TODO Moaz
     */
    void setToken(String token);

     /**
     * TODO Moaz
     */
    void setLemma(String lemma);

    /**
     * TODO Moaz
     */    
    void setSenses(List<IConcept> senses);

    /**
     * TODO Moaz
     */      
    void setSelectedSense(IConcept selectedSense);
}
