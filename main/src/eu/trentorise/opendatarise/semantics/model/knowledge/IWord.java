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
