/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendatarise;

import com.google.refine.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
/**
 *
 * @author David Leoni
 */

public class ODR {
    public static final int MAX_STEPS = 8;
    public static final Logger logger = LoggerFactory.getLogger("odr");
    public static String language;
    public static HashMap<Label, String> labelDict =  new HashMap<Label,String>();
    public static String PROJECT_OVERLAY_NAME = "OdrProjectOverlay";
            
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
        ODR.language = "en-us";
        labelDict.put(Label.SELECTION, "Selection");
        labelDict.put(Label.SCHEMA_MATCHING, "Schema matching");
        labelDict.put(Label.DATA_VALIDATION, "Data Validation");
        labelDict.put(Label.SEMANTIC_ENRICHMENT, "Enrichment");
        labelDict.put(Label.RECONCILIATION, "Reconciliation");
        labelDict.put(Label.EXPORTING, "Exporting");
        labelDict.put(Label.PUBLISHING, "Publishing");    

    }

    public static void initOverlay(Project prj) {
                
        OdrProjectOverlay po = new OdrProjectOverlay();
        po.setProject(prj);                
        prj.overlayModels.put(ODR.PROJECT_OVERLAY_NAME, po);
        
    }
    
    
    
}