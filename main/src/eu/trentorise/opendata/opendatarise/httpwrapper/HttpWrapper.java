
package eu.trentorise.opendata.opendatarise.httpwrapper;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.refine.ProjectManager;
import com.google.refine.ProjectMetadata;
import com.google.refine.importing.ImportingManager;
import com.google.refine.model.Project;

/**
 * Last modified by azanella On May 31, 2013
 * 
 * @author azanella
 * 
 */
public class HttpWrapper extends Thread {

    /**
     * Enum which lists available importing format types
     * 
     * @author azanella
     * 
     */
    public enum ImportingFormatType {
        TXT(".txt", "text/line-based", "Line-based text files"), CSV(".csv", "text/line-based/*sv",
                "CSV / TSV / separator-based files"), TSV(".tsv", "text/line-based/*sv",
                "CSV / TSV / separator-based files"), XML(".xml", "text/xml", "XML files"), RDF(".rdf", "text/xml/rdf",
                "RDF/XML files"), JSON(".json", "text/json", "JSON files"), JS(".js", "text/json", "JSON files"), XLS(
                ".xls", "binary/xls", "Excel files"), XLSX(".xlsx", "text/xml/xlsx", "Excel (.xlsx) files"), ODS(
                ".ods", "text/xml/ods", "Open Document Format spreadsheets (.ods)"), N3(".n3", "text/rdf+n3",
                "RDF/N3 files"), MARC(".marc", "text/marc", "MARC files"), MRC(".mrc", "text/marc", "MARC files"), PX(
                ".px", "text/line-based/pc-axis", "PC-Axis text files");

        private String type;
        private String describerCode;
        private String description;

        private ImportingFormatType(String type, String describerCode, String description) {
            this.describerCode = describerCode;
            this.type = type;
            this.description = description;
        }

        public String getType() {
            return type;
        }

        public String getDescriberCode() {
            return describerCode;
        }

        public String getDescription() {
            return description;
        }

        public static ImportingFormatType fromExtToType(String ext) {
            for (ImportingFormatType ft : ImportingFormatType.values()) {
                if (ext.equals(ft.getType())) return ft;
            }
            return null;
        }

    };

    private static HttpClient client = null;
    public static String baseUrl = "http://localhost:4444/"; // temporary -- We
                                                             // must pass this
                                                             // dinamically from
                                                             // the servlet.

    private static HttpClient getClient() {
        if (client == null) client = new DefaultHttpClient();
        return client;
    }

    /**
     * This method creates a new refine project from the URL of file. The file
     * should be in a format compatible with Refine. This method sets some
     * parameters by default to simplify calls.
     * 
     * @param encoding
     *            : Specifies the file encoding.
     * @param projectName
     *            : Name of the project
     * @param url
     *            : The URL of the file
     * @param separator
     *            : The character which separates the file
     * @param fileType
     *            : If you want to specify a format manually. It is not
     *            mandatory, If you want to autodetect the type from the
     *            extension of file, put this to null;
     * @param guessCellValueTypes
     *            : Guess value type (number, date, etc) : default true
     * @return The project instance if the operation is completed correctly,
     *         null otherwise.
     */

    public static Project createProjectFromUrl(String encoding, String projectName, String url,
            ImportingFormatType fileType, String separator, boolean guessCellValueTypes) {
        return createProjectFromUrl(encoding, projectName, url, separator, fileType, -1, 1, 0, -1, true,
                guessCellValueTypes, true, true, false);
    }

    /**
     * This method creates a new refine project from the URL of file. The file
     * should be in a format compatible with Refine. You can specify one or more
     * parameters.
     * 
     * @param encoding
     *            : Specifies the file encoding.
     * @param projectName
     *            : Name of the project
     * @param url
     *            : The URL of the file
     * @param separator
     *            : The character which separates the file
     * @param fileType
     *            : If you want to specify a format manually. It is not
     *            mandatory, If you want to autodetect the type from the
     *            extension of file, put this to null;
     * @param ignoreLines
     *            : Number of initial lines at the top of the file to ignore
     * @param headerLines
     *            : Number of Heading lines
     * @param skipDataLines
     *            : Number of initial line with data to discard
     * @param limit
     *            : Max number of line to process (set to -1 to process all
     *            lines)
     * @param storeBlankRows
     *            : Store blank rows
     * @param guessCellValueTypes
     *            : Guess value type (number, date, etc) : default true
     * @param processQuotes
     *            : Quotation marks are used to enclose cells containing column
     *            separators
     * @param storeBlankCellsAsNulls
     * @param includeFileSources
     *            : Store file source (file names, URLs) in each row
     * @return The project instance if the operation is completed correctly,
     *         null otherwise.
     */
    private static Project createProjectFromUrl(String encoding, String projectName, String url, String separator,
            ImportingFormatType fileType, int ignoreLines, int headerLines, int skipDataLines, int limit,
            boolean storeBlankRows, boolean guessCellValueTypes, boolean processQuotes, boolean storeBlankCellsAsNulls,
            boolean includeFileSources) {
        return createProject(encoding, projectName, url, null, separator, fileType, ignoreLines, headerLines,
                skipDataLines, limit, storeBlankRows, guessCellValueTypes, processQuotes, storeBlankCellsAsNulls,
                includeFileSources);
    }

    /**
     * This method creates a new refine project from the URL of file. The file
     * should be in a format compatible with Refine. This method sets some
     * parameters by default to simplify calls.
     * 
     * @param encoding
     *            : Specifies the file encoding.
     * @param projectName
     *            : Name of the project
     * @param uploadFile
     *            : File object to upload
     * @param separator
     *            : The character which separates the file
     * @param fileType
     *            : If you want to specify a format manually. It is not
     *            mandatory, If you want to autodetect the type from the
     *            extension of file, put this to null;
     * @param guessCellValueTypes
     *            : Guess value type (number, date, etc) : default true
     * @return The project instance if the operation is completed correctly,
     *         null otherwise.
     */

    public static Project createProjectFromFile(String encoding, String projectName, File uploadFile,
            ImportingFormatType fileType, String separator, boolean guessCellValueTypes) {
        return createProjectFromFile(encoding, projectName, uploadFile, separator, fileType, -1, 1, 0, -1, true,
                guessCellValueTypes, true, true, false);
    }

    /**
     * This method creates a new refine project from the URL of file. The file
     * should be in a format compatible with Refine. You can specify one or more
     * parameters.
     * 
     * @param encoding
     *            : Specifies the file encoding.
     * @param projectName
     *            : Name of the project
     * @param uploadFile
     *            : File object to upload
     * @param separator
     *            : The character which separates the file
     * @param fileType
     *            : If you want to specify a format manually. It is not
     *            mandatory, If you want to autodetect the type from the
     *            extension of file, put this to null;
     * @param ignoreLines
     *            : Number of initial lines at the top of the file to ignore
     * @param headerLines
     *            : Number of Heading lines
     * @param skipDataLines
     *            : Number of initial line with data to discard
     * @param limit
     *            : Max number of line to process (set to -1 to process all
     *            lines)
     * @param storeBlankRows
     *            : Store blank rows
     * @param guessCellValueTypes
     *            : Guess value type (number, date, etc) : default true
     * @param processQuotes
     *            : Quotation marks are used to enclose cells containing column
     *            separators
     * @param storeBlankCellsAsNulls
     * @param includeFileSources
     *            : Store file source (file names, URLs) in each row
     * @return The project instance if the operation is completed correctly,
     *         null otherwise.
     */
    private static Project createProjectFromFile(String encoding, String projectName, File uploadFile,
            String separator, ImportingFormatType fileType, int ignoreLines, int headerLines, int skipDataLines,
            int limit, boolean storeBlankRows, boolean guessCellValueTypes, boolean processQuotes,
            boolean storeBlankCellsAsNulls, boolean includeFileSources) {
        return createProject(encoding, projectName, null, uploadFile, separator, fileType, ignoreLines, headerLines,
                skipDataLines, limit, storeBlankRows, guessCellValueTypes, processQuotes, storeBlankCellsAsNulls,
                includeFileSources);
    }

    @SuppressWarnings("static-access")
    private static Project createProject(String encoding, String projectName, String url, File uploadFile,
            String separator, ImportingFormatType fileType, int ignoreLines, int headerLines, int skipDataLines,
            int limit, boolean storeBlankRows, boolean guessCellValueTypes, boolean processQuotes,
            boolean storeBlankCellsAsNulls, boolean includeFileSources) {

        try {
            while (ImportingManager.controllers.isEmpty()) {
                Thread.currentThread().sleep(1000);
            }
            // Step 1 : create the importing job
            HttpPost postreq = new HttpPost(baseUrl + "command/core/create-importing-job");
            long jobid = -1;
            JSONObject retval = readResponse(getClient().execute(postreq));
            if (retval != null && retval.has("jobID")) {
                jobid = retval.getLong("jobID");
            }
            // Step 2 : pass the URL of the resource to download (or the file to
            // upload)
            String command = baseUrl
                    + "command/core/importing-controller?controller=core%2Fdefault-importing-controller&jobID=" + jobid
                    + "&subCommand=load-raw-data";
            postreq = new HttpPost(command);
            // URL branch
            if (uploadFile == null) {
                MultipartEntity mte = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                mte.addPart("download", new StringBody(url));
                postreq.setEntity(mte);
            }
            // UPLOAD FILE BRANCH
            else {
                MultipartEntity mte = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                mte.addPart("upload", new FileBody(uploadFile));
                postreq.setEntity(mte);
                url = uploadFile.getName();
            }
            retval = readResponse(getClient().execute(postreq));

            boolean wait = true;
            boolean error = false;
            // Wait until the server ends. It could end in two states: Error (we
            // return false) or ready (we continue)
            while (wait) {
                command = baseUrl + "/command/core/get-importing-job-status?jobID=" + jobid;
                postreq = new HttpPost(command);
                retval = readResponse(getClient().execute(postreq));
                retval = retval.getJSONObject("job").getJSONObject("config");

                if (projectName == null) {
                    if (url != null) projectName = url.substring(url.lastIndexOf("/") + 1).replace(".", " ");
                    if (uploadFile != null)
                        projectName = uploadFile.getName().substring(url.lastIndexOf("/") + 1).replace(".", " ");
                }

                if (retval.get("state").equals("error")) {
                    wait = false;
                    error = true;
                } else if (retval.get("state").equals("ready")) {
                    wait = false;
                } else
                    Thread.currentThread().sleep(1000);
            }
            if (error) return null;

            // Step 3 Set importing options and create the project
            JSONObject options = new JSONObject();

            options.put("encoding", encoding);
            options.put("separator", separator);
            options.put("ignoreLines", ignoreLines);
            options.put("headerLines", headerLines);
            options.put("skipDataLines", skipDataLines);
            options.put("limit", limit);
            options.put("storeBlankRows", storeBlankCellsAsNulls);
            options.put("guessCellValueTypes", guessCellValueTypes);
            options.put("storeBlankCellsAsNulls", storeBlankCellsAsNulls);
            options.put("includeFileSources", includeFileSources);
            options.put("projectName", projectName);

            command = baseUrl
                    + "command/core/importing-controller?controller=core%2Fdefault-importing-controller&jobID=" + jobid
                    + "&subCommand=create-project";
            postreq = new HttpPost(command);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("options", options.toString()));

            if (fileType == null) {
                String ext = url.substring(url.lastIndexOf('.'));
                fileType = ImportingFormatType.fromExtToType(ext);
                if (fileType == null) return null;
            }

            params.add(new BasicNameValuePair("format", fileType.getDescriberCode()));
            postreq.setEntity(new UrlEncodedFormEntity(params));
            retval = readResponse(getClient().execute(postreq));
            if (!((retval != null) && (retval.has("status")) && (retval.get("status").equals("ok")))) return null;

            if (projectName == null) projectName = url.substring(url.lastIndexOf("/") + 1).replace(".", " ");
            wait = true;
            error = false;
            // Wait until the server ends. It could end in two states: Error (we
            // return null) or ready (we continue)
            Project retprj = null;
            while (wait) {
                command = baseUrl + "/command/core/get-importing-job-status?jobID=" + jobid;
                postreq = new HttpPost(command);
                retval = readResponse(getClient().execute(postreq));
                retval = retval.getJSONObject("job").getJSONObject("config");
                if (retval.get("state").equals("error")) {
                    wait = false;
                    error = true;
                } else if (retval.get("state").equals("created-project")) {
                    long id = retval.getLong("projectID");
                    retprj = ProjectManager.singleton.getProject(id);
                    wait = false;
                } else
                    Thread.currentThread().sleep(1000);
            }
            return retprj;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void run() {
        HttpGet req = new HttpGet(baseUrl + "command/core/get-version");
        HttpResponse response;
        try {
            boolean test = true;
            while (test) {
                response = getClient().execute(req);
                StringWriter writer = new StringWriter();
                IOUtils.copy(response.getEntity().getContent(), writer);
                String resp = writer.toString();
                try {
                    new JSONObject(resp);
                    test = false;
                } catch (JSONException e) {
                    test = true;
                }
            }
            Project prj = createProjectFromFile("utf-8", null, new File(
                    "C:/Users/azanella/Downloads/prodotti_protetti.csv"), ImportingFormatType.CSV, ",", false);
            if (prj != null) {
                System.out.println("ALL RIGHTS!!  " + prj.id);
            }
            // deleteAllProjects();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Set<Long> getAllProjectIds() {
        return ProjectManager.singleton.getAllProjectMetadata().keySet();
    }

    public List<String> getAllProjectNames() {
        List<String> retval = new ArrayList<String>();
        for (ProjectMetadata pm : ProjectManager.singleton.getAllProjectMetadata().values()) {
            retval.add(pm.getName());
        }
        return retval;
    }

    public void deleteAllProjects() {
        ArrayList<Long> lista = new ArrayList<Long>(getAllProjectIds());
        for (Long p : lista) {
            ProjectManager.singleton.deleteProject(p);
        }
    }

    private static JSONObject readResponse(HttpResponse response) {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(response.getEntity().getContent(), writer);
            String resp = writer.toString();
            return new JSONObject(resp);
        } catch (Exception e) {
            return null;
        }
    }

    private static String readStringResponse(HttpResponse response) {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(response.getEntity().getContent(), writer);
            String resp = writer.toString();
            return resp;
        } catch (Exception e) {
            return null;
        }
    }

}
