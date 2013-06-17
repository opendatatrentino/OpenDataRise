
package eu.trentorise.opendatarise.process;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.refine.importing.ImportingJob;
import com.google.refine.process.Process;

public class Registry {

    private static ProcessGroup root;
    private static Logger logger;

    public static ProcessGroup getRootProcessGroup() {
        if (root == null) root = new ProcessGroup();
        return root;
    }

    private static Logger getLogger() {
        if (logger == null) logger = LoggerFactory.getLogger("odr-registry");
        return logger;
    }

    public static void registerProcessAsStarted(Process process, String startTime) {
        process.setStartTime(startTime);
        getRootProcessGroup().processes.add(process);
    }

    public static void registerProcessGroupAsStarted(ProcessGroup pg, String startTime) {
        pg.setStartTime(startTime);
        getRootProcessGroup().processGroups.add(pg);
    }

    public static void registerImportingJobAsStarted(ImportingJob ij, String startTime) {
        ij.setStartTime(startTime);
        getRootProcessGroup().importingJobs.add(ij);
    }

    public static void registerProcessAsCompleted(Process process, String endTime) {
        getRootProcessGroup().processes.remove(process);
        process.setEndTime(endTime);
        try {
            getRootProcessGroup().proc_finished.add(process.writeToJsonObject());
        } catch (JSONException e) {
            getLogger().error("Problem unregistering process " + process.hashCode());
        }

    }

    public static void registerProcessGroupAsCompleted(ProcessGroup pg, String endTime) {
        getRootProcessGroup().processGroups.remove(pg);
        pg.setEndTime(endTime);
        getRootProcessGroup().processGroups.add(pg);

    }

    public static void registerImportingJobAsCompleted(ImportingJob ij, String endTime) {
        getRootProcessGroup().importingJobs.remove(ij);
        ij.setEndTime(endTime);
        try {
            getRootProcessGroup().jobs_finished.add(ij.writeToJsonObject());
        } catch (JSONException e) {
            getLogger().error("Problem unregistering importing job " + ij.id);
        }
    }

    public static String getTimeNow() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    public static void cleanCompletedLists()
    {
        getRootProcessGroup().proc_finished.clear();
        getRootProcessGroup().jobs_finished.clear();
    }

}
