package eu.trentorise.opendatarise.operations;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.Properties;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.google.refine.history.Change;
import com.google.refine.history.HistoryEntry;
import com.google.refine.model.AbstractOperation;
import com.google.refine.model.Project;
import com.google.refine.operations.OperationRegistry;
import com.google.refine.util.ParsingUtilities;
import com.google.refine.util.Pool;
import eu.trentorise.opendatarise.ODR;
import eu.trentorise.opendatarise.OdrException;
import eu.trentorise.opendatarise.OdrProjectOverlay;


/**
 *
 * @author david_2
 */
public class SetStepOperation extends AbstractOperation {

    final protected int step;

    public SetStepOperation(int step) {
        this.step = step;
    }

    static public AbstractOperation reconstruct(Project project, JSONObject obj)
            throws Exception {
        ODR.logger.debug("Reconstructing SetStepOperation...");
        return new SetStepOperation(obj.getInt("step"));
    }

    public void write(JSONWriter writer, Properties options)
            throws JSONException {
        writer.object();
        writer.key("op");
        writer.value(OperationRegistry.s_opClassToName.get(this.getClass()));
        writer.key("description");
        writer.value("Set ODR project step");
        writer.key("step");
        writer.value(step);
        writer.endObject();

    }

    @Override
    protected String getBriefDescription(Project project) {
        return "Set ODR project step";
    }

    @Override
    protected HistoryEntry createHistoryEntry(Project project,
            long historyEntryID) throws Exception {
                
        Change change = new StepChange(step);
        String description = "Changed OpenDataRise step to " + step;
        
        return new HistoryEntry(historyEntryID, project, description,
                SetStepOperation.this, change);
    }

    /**
     *
     */
    static public class StepChange implements Change {
        final protected int newStep;
        protected int oldStep;
        
        public StepChange(int step) {            
            newStep = step;
        }
        
        public void apply(Project project) {
            if (newStep < 2 || newStep > ODR.MAX_STEPS){
                throw new OdrException("Can't go to step " + newStep);
            } else {
                synchronized (project) {
                    OdrProjectOverlay odrPO = ((OdrProjectOverlay) project.overlayModels.get("OdrProjectOverlay"));
                    oldStep = odrPO.getStep();
                    odrPO.setStep(newStep);
                }
            }
        }
        
        public void revert(Project project) {
            synchronized (project) {
                OdrProjectOverlay odrPO = ((OdrProjectOverlay) project.overlayModels.get("OdrProjectOverlay"));
                odrPO.setStep(oldStep);
            }
        }
        
        public void save(Writer writer, Properties options) throws IOException {
            writer.write("newStep=");
            writer.write(Integer.toString(newStep));
            writer.write('\n');
            writer.write("oldStep=");
            writer.write(Integer.toString(oldStep));
            writer.write('\n');
            writer.write("/ec/\n"); // end of change marker

            
        }
        
        static public Change load(LineNumberReader reader, Pool pool)
                throws Exception {
            
            int oldStep = 0;
            int newStep = 0;
            
            String line;
            while ((line = reader.readLine()) != null && !"/ec/".equals(line)) {
                int equal = line.indexOf('=');
                CharSequence field = line.subSequence(0, equal);
                String value = line.substring(equal + 1);
                
                if ("oldStep".equals(field) && value.length() > 0) {
                    oldStep = Integer.parseInt(value);
                } else if ("newStep".equals(field) && value.length() > 0) {
                    newStep = Integer.parseInt(value);
                }
            }
            
            StepChange change = new StepChange(newStep);
            change.oldStep = oldStep;
            
            return change;             
        }
        
    }
}
