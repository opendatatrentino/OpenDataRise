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

import java.util.List;

/**
 * Represents a natural language word
 * 
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @version July, 2013
 */
public interface IWord {

    /**
     * Gets the lemma of the word
     *
     * @return the lemma of the word
     */
    String getLemma();

    /**
     * Gets the senses of the word
     *
     * @return a list of concepts which associated with the given word
     */
    List<IConcept> getSenses();

    /**
     * Gets the selected sense from the word's concepts
     *
     * @return the selected sense from the word's concepts
     */
    IConcept getSelectedSense();

    /**
     * Sets the token of the word
     *
     * @param token the token to be set
     */
    void setToken(String token);

    /**
     * Sets the lemma of the word
     * @param lemma 
     */
    void setLemma(String lemma);

    /**
     * Sets the list of concepts associated with this word
     *
     * @param senses the list of concepts associated with this word
     */
    void setSenses(List<IConcept> senses);

    /**
     * Sets the selected sense from the word's concepts
     *
     * @param selectedSense the selected sense from the word's concepts
     */
    void setSelectedSense(IConcept selectedSense);
}
