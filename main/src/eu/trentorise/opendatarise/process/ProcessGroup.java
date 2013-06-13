
package eu.trentorise.opendatarise.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.refine.Jsonizable;
import com.google.refine.importing.ImportingJob;
import com.google.refine.process.Process;

/**
 * This class represents a set of process, importingJobs or other things to
 * monitor. A process group could be composed of other sub-groups
 * 
 * @author azanella 
 * Last modified by azanella On Jun 13, 2013
 */

public class ProcessGroup {

    List<ProcessGroup> processGroups;
    List<Process> processes;
    List<ImportingJob> importingJobs;

    private String name;
    private String startTime;
    private String endTime;

    
    public ProcessGroup() {
        processGroups = Collections.synchronizedList(new ArrayList<ProcessGroup>());
        processes = Collections.synchronizedList(new ArrayList<Process>());
        importingJobs = Collections.synchronizedList(new ArrayList<ImportingJob>());
    }
    
    /**
     * This method write this object into a JSONObject.
     * @param writer -- An existent JSONObject to extend or where to write. If null it throw NullPointerExcepition
     * @throws JSONException
     */
    public JSONObject writeJSONObject()
            throws JSONException {
        JSONObject writer = new JSONObject();
        for (Process process : processes) {
            writer.accumulate("processes", process.writeToJsonObject());
        }
        
        for (ImportingJob ijb : importingJobs) {
            writer.accumulate("importingjobs", ijb.writeToJsonObject());
        }
        
        for (ProcessGroup pg : processGroups) {
            writer.accumulate("processgroups", pg.writeJSONObject());
        }
        
        return writer;
        
    }
    
    
    //Getters setters ....
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<ProcessGroup> getProcessGroups() {
        return processGroups;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public List<ImportingJob> getImportingJobs() {
        return importingJobs;
    }

}
