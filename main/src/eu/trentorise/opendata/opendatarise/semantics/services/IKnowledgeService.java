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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.IWord;
import java.util.List;

/**
 * Knowledge services are used to read words from the a knowledge base.
 *
 * @author Juan
 * @author Moaz Reyad <moazreyad@gmail.com>
 * @date Jul 24, 2013
 */
public interface IKnowledgeService {

    /**
     * Reading words by lemma
     * @param wordLemma the word's lemma
     * @return the list of words that have this lemma
     */
    List<IWord> readByWordLemma(String wordLemma);

    /**
     * Reading a word by a prefix
     * @param prefix the prefix of the words to be found
     * @return the list of words that starts with the given prefix
     */
    List<IWord> readByWordPrefix(String prefix);
}
