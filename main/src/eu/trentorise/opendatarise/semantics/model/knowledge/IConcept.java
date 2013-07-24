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
