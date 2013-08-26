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
    int substep;
    Project project;
    boolean createdWithOdr;
    private int horizontallyReconciledRows;
    static final Logger logger = LoggerFactory.getLogger("odr");


    /**
     *
     *
     */
    public OdrProjectOverlay() {
        this.createdWithOdr = false;
        this.step = 2;
        this.substep = 1;
        this.project = null;
        this.horizontallyReconciledRows = 0;
    }

    public boolean isHorizontallyReconciled(){
        return project.rows.size() == getHorizontallyReconciledRows();
    }
    /**
     * Sets step with substep = 1
     * @param step must be >= 1 and <= 8
     * @param step 
     */
    public void setStep(int step) {
        this.setStep(step,1);
    }
    
    /**
     *
     * @param step must be >= 1 and <= 8
     * @param substep must be >= 1
     */
    public void setStep(int step, int substep) {
        if (step < 1 || step > ODR.MAX_STEPS) {
            throw new OdrException("Step out of range: " + step);
        }

        if (substep < 1) {
            throw new OdrException("Substep cannot be less than 1: " + substep);
        }

        switch (step) {
            case 1: {

                break;
            }
            case 2: {
                if (project == null) {
                    throw new OdrException("There must be a project before entering step 2!");
                }

                break;
            }
            case 3: {
                break;
            }
            case 4: {
                break;
            }
            case 5: {

                break;
            }
            case 6: {
                break;
            }
            case 7: {
                break;
            }
            case 8: {
                break;
            }
        }

        this.step = step;
        this.substep = substep;

    }

    public void onBeforeSave(Project project) {
    }

    ;
    
    public void onAfterSave(Project project) {
    }

    ;
    
    public void dispose(Project project) {
    }

    ;
    
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
        

        try {
            po.setCreatedWithOdr(obj.getBoolean("createdWithOdr"));
        } catch (JSONException ex) {
            ODR.logger.info("Project not created with ODR.");
        }
        
        try {
            po.setHorizontallyReconciledRows(obj.getInt("horizontallyReconciledRows")); 
        } catch (JSONException ex) {
            ODR.logger.info("horizontallyReconciledRows information was not present. Setting it to "+po.getHorizontallyReconciledRows());
        }         
        
        int step = po.getStep();
        int substep = po.getSubstep();
        try {
            step = obj.getInt("step");
        } catch (JSONException ex) {
            ODR.logger.info("Step information was not present. Setting it to "+substep);
        }
        try {
            substep = obj.getInt("substep");
        } catch (JSONException ex) {
            ODR.logger.info("Substep information was not present. Setting it to "+substep);
        }
       
        po.setStep(step, substep);
        
        
        return po;
    }

    static public OverlayModel reconstruct(JSONObject obj) throws JSONException {
        throw new OdrException("reconstruct(JSONObject obj) method of OdrProjectModel got called, for the time being I would like avoiding it, see https://github.com/opendatatrentino/OpenDataRise/issues/18");
    }

    public void write(JSONWriter writer, Properties options) throws JSONException {
        logger.debug("Writing odr overlay model");
        writer.object();
        writer.key("step");
        writer.value(step);
        writer.key("substep");
        writer.value(substep);
        writer.key("createdWithOdr");
        writer.value(createdWithOdr);
        writer.key("horizontallyReconciledRows");
        writer.value(getHorizontallyReconciledRows());
        // this is just because in-browser update mechanism badly sucks. See https://github.com/opendatatrentino/OpenDataRise/issues/27
        writer.key("rowModel-total");
        writer.value(project.rows.size());
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

    public int getSubstep() {
        return substep;
    }

    /**
     * @return the horizontallyReconciledRows
     */
    public int getHorizontallyReconciledRows() {
        return horizontallyReconciledRows;
    }

    /**
     * @param horizontallyReconciledRows the horizontallyReconciledRows to set
     */
    public void setHorizontallyReconciledRows(int horizontallyReconciledRows) {
        this.horizontallyReconciledRows = horizontallyReconciledRows;
    }
}
