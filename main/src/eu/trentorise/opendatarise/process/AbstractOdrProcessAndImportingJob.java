
package eu.trentorise.opendatarise.process;

import java.io.StringWriter;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.google.refine.Jsonizable;

/**
 * Last modified by azanella On Jun 13, 2013
 * 
 * @author azanella 
 * This abstract class adds some metadata to all Refine Process and ImportingJob objects 
 *         and is extended by the defaults Refine Process and ImportingJob class. It is create to
 *         keep the changes out of the original Refine code as much as possible.
 * 
 */

public abstract class AbstractOdrProcessAndImportingJob implements Jsonizable{

    private String name;
    private String startTime;
    private String endTime;
    
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

    @Override
    public void write(JSONWriter writer, Properties options)
            throws JSONException {
        
        throw new UnsupportedOperationException();
    }

    /**
     * This method is used to convert the standard JSONWriter used normally on Jsonizable objects to a JSONObject.
     * @return JSONObject containing the same representation of the object provided by the write method
     * @throws JSONException
     */
    public JSONObject writeToJsonObject() throws JSONException
    {
        StringWriter sw = new StringWriter();
        JSONWriter jw = new JSONWriter(sw);
        this.write(jw, new Properties());
        JSONObject jo = new JSONObject(sw.toString());
        if ((jo != null) && (jo.length() > 0)) {
            if (this.getName() == null) {
                jo.put("name", JSONObject.NULL);
            } else
                jo.put("name", this.getName());
            if (this.getStartTime() == null) {
                jo.put("starttime", JSONObject.NULL);
            } else
                jo.put("starttime", this.getStartTime());
            if (this.getEndTime() == null) {
                jo.put("endtime", JSONObject.NULL);
            }
            jo.put("endtime", this.getEndTime());
        }
        return jo;
    }
    
    
}
