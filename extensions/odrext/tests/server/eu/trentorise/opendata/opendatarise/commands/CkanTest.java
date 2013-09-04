/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.ProjectManager;
import com.google.refine.commands.history.CancelProcessesCommand;
import com.google.refine.model.Project;
import com.google.refine.process.ProcessManager;
import com.google.refine.tests.RefineServletStub;
import eu.trentorise.opendata.opendatarise.Catalogs;
import java.util.Iterator;
import java.util.List;
import org.ckan.resource.impl.Dataset;
import org.ckan.result.impl.DatasetSearchResult;
import org.ckan.result.list.impl.DatasetSearchList;
import org.testng.annotations.Test;

import com.google.refine.tests.RefineTest;
import eu.trentorise.opendata.opendatarise.Catalog;
import eu.trentorise.opendata.opendatarise.ODR;
import eu.trentorise.opendata.opendatarise.Utils;
import eu.trentorise.opendata.opendatarise.importing.SearchResults;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ckan.resource.impl.Resource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

/**
 *
 * @author David Leoni
 */

public class CkanTest extends RefineTest {

    
    
    RefineServletStub servlet;
    
      
    // mocks
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    PrintWriter pw = null;    
    String testCatalog;


    @BeforeClass
    public void beforeClass() {
        System.out.println("CkanTest.beforeClass ");        
        logger = LoggerFactory.getLogger(this.getClass());        
        testCatalog = System.getProperty("testCatalog");
        assert testCatalog != null;
        assert logger != null;
        logger.info("Testing against catalog: " + testCatalog);
    }    
    
    
    @BeforeMethod
    public void SetUp() {       
        System.out.println("CkanTest.beforeTest ");
        pw = mock(PrintWriter.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);        
        Catalogs.delete();
        Catalogs.init();        
    }    
    
    @AfterMethod
    public void TearDown() {       
        System.out.println("CkanTest.afterTest ");
        pw = null;
        request = null;
        response = null;   
        Catalogs.delete();        
    }    

    
    
    /**
     * Just to try it out
     */
    protected static final void tryCkanClient(){
        
        Catalogs catInfo; 

        Dataset ds = null;
        DatasetSearchResult sr = null;
        /*try {
            sr = Catalogs.getCatInfo().getCkanClient().searchDatasets("meteo");                      
            DatasetSearchList dsl = sr.result;
            List<Dataset> results = dsl.results;
            Iterator<Dataset> iterator = results.iterator();
            while(iterator.hasNext())
            {
                ds = iterator.next();
                System.out.println(ds);
            }            
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }*/

    }        
    
    /**
     * 
     */

    protected void testDatiTrentino(){
        //CKANalyzeRawStats.init();
          //       tryCkanClient();
    
    }
    
    
    
    /**
     * Todo just threw in a copy of first search prototype
     */
    protected void mockData(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
       
       
 
        try {
            /*            ObjectMapper om = new ObjectMapper(); */
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");
            JSONWriter writer = new JSONWriter(response.getWriter());

            String catalogUrl = request.getParameter("ckanUrl");
            int sEcho = Integer.parseInt(request.getParameter("sEcho"));
            int iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
            ODR.logger.debug("iDisplayStart = " + iDisplayStart);            
            int iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
            ODR.logger.debug("iDisplayLength = " + iDisplayLength);
            /*            Catalog catalog = Catalogs.getCatalog(catalogUrl);
             if (catalog == null) {
             response.setStatus(HttpServletResponse.SC_NOT_FOUND);
             } else { */

            writer.object();
            writer.key("sEcho");
            writer.value(sEcho);
            writer.key("iTotalRecords");
            writer.value(2); // todo
            writer.key("iTotalDisplayRecords");
            writer.value(2); // todo
            writer.key("aaData");
            writer.array();
            writer.array();
            writer.value("Anagrafica campi neve");
            writer.value("Anagrafica marilleva in formato XML");
            writer.value("Meteo");            
            writer.value( "CSV");
            writer.value( 11);
            writer.value( 126);
            writer.value( 2);
            writer.value( 1);
            writer.value( 4);
            writer.value( 2);
            writer.value( 0);
            writer.value( 2);
            writer.value( 6.8);
            writer.endArray();

            writer.array();
            writer.value("Bollettino meteo");
            writer.value("Bollettino Val di Non e zone limitrofe");
            writer.value("Meteo");            
            writer.value( "CSV");
            writer.value( 10);
            writer.value( 116);
            writer.value( 2);
            writer.value( 1);
            writer.value( 4);
            writer.value( 1);
            writer.value( 1);
            writer.value( 1);
            writer.value( 7.2);
            writer.endArray();
            writer.endArray();
 
            writer.endObject();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (JSONException e) {
            // netbeans can't find this? respondException(response, e);
        }
           
    
    }



    
    /**
     * Just to try out various clients
     * @throws Exception 
     */
    protected void getResources() throws Exception {
    
            
            String url = "http://dati.trentino.it";

            String format = "csv";
            String ckanSearchInput = "";
            int offset = 0;
            int limit = 10;
            String resourcesQuery = url + "/api/3/search/resource?";
            if (format != null) {
                resourcesQuery += "format="+format+"&amp;";
            }
                resourcesQuery += "description="+ckanSearchInput+"&amp;offset="+offset+"&amp;limit="+limit+"&amp;all_fields=1";
                String stringa = Utils.getRest(resourcesQuery);
                
                logger.info("queryResource = " + resourcesQuery);
                logger.info("getRest = " + stringa);

                JSONObject resourcesQueryResponse = new JSONObject(stringa);   

                JSONArray resourcesList = resourcesQueryResponse.getJSONArray("results");
                
                Catalog catalog = new Catalog(testCatalog);
                org.ckan.Client ckanClient = new org.ckan.Client(new org.ckan.Connection(testCatalog), "");
                for (int i = 0; i < resourcesList.length(); i++){
                    logger.info("Processing resource " + resourcesList.getString(i));
                    //Resource resource = ckanClient.getResource(resourcesList.getString(i));
                    //throw new RuntimeException(resourcesList.getString(i));
                    Resource resource =  ckanClient.getGsonObjectFromGenericJson(Resource.class,resourcesList.getString(i),"getResource");
                    Dataset dataset = ckanClient.getDataset(resource.getPackage_id());
                }
                    
                    /*
                    String datasetId = resource.getString("package_id");
                            

                    get = new GetMethod(url + "/api/3/action/package_show?id=" + datasetId);
                    JSONObject dataset = new JSONObject(get.getResponseBodyAsString()).getJSONObject("result");
                    get.releaseConnection();
                    
                    org.ckan.resource.impl.Dataset
                      */      
    
    }
    
}
