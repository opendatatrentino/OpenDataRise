/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.knowledge.IWord;
import java.util.List;

/**
 *
 * @author Juan
 */
public interface IKnowledgeService {
    
    
    public List<IWord> readByWordLema(String wordLema);
    
    public List<IWord> readByWordPrefix(String prefix);
    
    
}
