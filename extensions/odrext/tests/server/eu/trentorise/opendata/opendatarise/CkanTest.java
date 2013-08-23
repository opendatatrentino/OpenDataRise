/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import eu.trentorise.opendata.opendatarise.Catalogs;
import java.util.Iterator;
import java.util.List;
import org.ckan.resource.impl.Dataset;
import org.ckan.result.impl.DatasetSearchResult;
import org.ckan.result.list.impl.DatasetSearchList;
import org.testng.annotations.Test;

import com.google.refine.tests.RefineTest;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

/**
 *
 * @author David Leoni
 */
@Test
public class CkanTest extends RefineTest {
    
    @Override
    @BeforeSuite
    public void init() {
        super.init();
        logger = LoggerFactory.getLogger(this.getClass());
        //System.setProperty("log4j.configuration", "tests.log4j.properties");
    }    
    
    /**
     * Just to try it out
     */
    public static final void tryCkanClient(){
        
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
    @Test
    public void testDatiTrentino(){
        //CKANalyzeRawStats.init();
          //       tryCkanClient();
    
    }
    
    
}
