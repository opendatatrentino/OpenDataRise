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

import java.util.Locale;

/**
 * A concept is a language independent element that gives meaning
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Sergey Kanshin <kanshin@disi.unitn.it>
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IConcept {

    /**
     * Gets the URI of the concept's Synset
     *
     * @return the Synset's URI as string
     */
    String getSynsetURI();

    /**
     * Gets the common name for the concept in the given language
     *
     * @param language the natural language
     * @return the common name for the concept in the given language
     */
    String getCommonlyReferredAs(Locale language);

    /**
     * Gets the summary of the concept in the given language
     *
     * @param language the natural language
     * @return the summary of the concept in the given language
     */
    String getSummary(Locale language);

    /**
     * Gets the description on the concept in the given language
     *
     * @param language the natural language
     * @return the description on the concept in the given language
     */
    String getDescription(Locale language);

    /**
     * Gets the Part of Speech (POS) of the concept in the given language
     *
     * @param language the natural language
     * @return the Part of Speech (POS) of the concept in the given language
     */
    String getPartOfSpeech(Locale language);
}
