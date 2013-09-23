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

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.INLPText;

/**
 * The NLP interface provides natural language processing services such as named
 * entity recognition and word sense disambiguation
 *
 * @author Juan Pane <pane@disi.unitn.it>
 * @author Moaz Reyad <reyad@disi.unitn.it>
 * @author David Leoni <david.leoni@trentorise.eu>
 * @date Sep 23, 2013
 */
public interface INLP {

    /**
     * Takes a natural language string and returns concepts and entities
     * disambiguated
     *
     * @param nlText an input natural language string
     * @return the natural language text object with all the entities and
     * concepts found and disambiguated
     */
    INLPText runNLP(String nlText);

    /**
     * Takes a natural language name of an entity as a string and finds the
     * disambiguated entity.
     *
     * @param nlText the natural language name of an entity which has been
     * processed before by Named Entity Recognition. After calling the function
     * it will have also the disambiguated Entity added to the object with their
     * locations.
     */
    void namedEntityDisambiguate(INLPText nlText);

    /**
     * Takes a natural language text and finds the concepts and entities in the
     * text.
     *
     * @param nlText the input natural language string. After calling the
     * function it will have also the concept and entities found in the text.
     * They will be added to the object with their locations.
     */
    void namedEntityRecognition(INLPText nlText);

    /**
     * Takes a natural language text that has been processed by NER and
     * disambiguates the concepts
     *
     * @param nlText the input natural language string. After calling the
     * function it will have also the concepts disambiguated.
     */
    void wordSenseDisambiguate(INLPText nlText);
}
