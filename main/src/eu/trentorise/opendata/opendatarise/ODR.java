/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Locale;
/**
 *
 * @author David Leoni
 */

public class ODR {
    public static final int MAX_STEPS = 8;
    public static final Logger logger = LoggerFactory.getLogger("odr");    
    public static HashMap<Label, String> labelDict =  new HashMap<Label,String>();
    private static Locale locale;
            
    public static final String getLabelString(Label label){
        return labelDict.get(label);
    }
    
    public static final Label getStepLabel(int step){
        return Label.values()[step];
    }
    public static final String getStepName(int step){
        return getLabelString(getStepLabel(step));
    }
    
    
    public static final void init(){        
        ODR.locale = Locale.forLanguageTag("en-us");
        labelDict.put(Label.SELECTION, "Selection");
        labelDict.put(Label.SCHEMA_MATCHING, "Schema matching");
        labelDict.put(Label.DATA_VALIDATION, "Data Validation");
        labelDict.put(Label.SEMANTIC_ENRICHMENT, "Enrichment");
        labelDict.put(Label.RECONCILIATION, "Reconciliation");
        labelDict.put(Label.EXPORTING, "Exporting");
        labelDict.put(Label.PUBLISHING, "Publishing");    
        
    }

    public static void setLocale(String locale) {   
        ODR.logger.debug("Setting language to " + locale);                        
        ODR.locale = Locale.forLanguageTag(locale);                
    }
    
    public static void setLocale(Locale locale) {   
        ODR.logger.debug("Setting language to " + locale.toLanguageTag());                        
        ODR.locale = locale;
    }
    
    public static Locale getLocale(){
        return locale;
    }
    
    
}