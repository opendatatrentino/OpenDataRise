package eu.trentorise.opendata.opendatarise;

import com.google.refine.model.OverlayModel; 
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.refine.model.Project;






public class OdrProjectOverlay implements OverlayModel {
            
    int step;
    Project project;    
    boolean createdWithOdr;
    
    static final Logger logger = LoggerFactory.getLogger("odr");
    
    
    /**
     * Step is set to step 1: Selection
     * 
     */
    public OdrProjectOverlay(){
        this.createdWithOdr = true;
        this.step = 1;        
        this.project = null; 
    }
    
    /**
     *
     * @param step
     */
    public void setStep(int step){
        if (step < 1 || step > ODR.MAX_STEPS){
            throw new OdrException("Step out of range: " + step);
        }
        
        switch(step) {
            case 1:{
                
                break;
            }
            case 2:{
                if (project == null) {
                    throw new OdrException("There must be a project before entering step 2!");
                }
                
                break;
            }
            case 3:{
                break;
            }
            case 4:{
                break;
            }
            case 5:{
                break;
            }                
            case 6:{
                break;
            }        
            case 7:{
                break;
            }        
            case 8:{
                break;
            }                
        }
        
        this.step = step;
        
    }
    
    public void onBeforeSave(Project project){};
    
    public void onAfterSave(Project project){};
    
    public void dispose(Project project){};
    
    /**
     * This method should kind of "override " static load of OverlayModel See https://github.com/opendatatrentino/OpenDataRise/issues/18
     * @param project
     * @param obj
     * @return
     * @throws Exception 
     */
    static public OverlayModel load(Project project, JSONObject obj) throws Exception {
        OdrProjectOverlay po = new OdrProjectOverlay();        
        po.project = project; 
        po.setCreatedWithOdr(obj.getBoolean("createdWithOdr"));
        po.setStep(obj.getInt("step"));          
        return po;
    }
    
    static public OverlayModel reconstruct(JSONObject obj) throws JSONException {        
        throw new OdrException("reconstruct(JSONObject obj) method of OdrProjectModel got called, for the time being I would like avoiding it, see https://github.com/opendatatrentino/OpenDataRise/issues/18");
    }    
    
    public void write(JSONWriter writer, Properties options) throws JSONException {
        logger.debug("Writing odr overlay model");
        writer.object();
        writer.key("step"); writer.value(step);
        writer.key("createdWithOdr"); writer.value(createdWithOdr);
        writer.endObject();
        
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setCreatedWithOdr(boolean created) {
        this.createdWithOdr = created;
    }

    public int getStep() {
        return step;
    }
}
