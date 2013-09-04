/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.tests.RefineTest;
import eu.trentorise.opendata.opendatarise.Catalogs;
import eu.trentorise.opendata.opendatarise.commands.CkanTest;
import eu.trentorise.opendata.opendatarise.importing.SearchResults;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

 /**
 *
 * @author David Leoni
 */

public class SearchCatalogCommandTest  extends CkanTest  {
  // System Under Test
    SearchCatalogCommand SUT;
   
    
    @Override
    @BeforeClass
    public void beforeClass() {
        System.out.println("SearchCatalogCommandTest.beforeClass ");
        super.beforeClass();
    }    

    
    @BeforeMethod
    @Override
    public void SetUp() {
        System.out.println("SearchCatalogCommandTest.beforeTest ");
        super.SetUp();
        SUT = new SearchCatalogCommand();    
    }    
    

    @AfterMethod
    @Override
    public void TearDown() {
        System.out.println("SearchCatalogCommandTest.afterTest ");
        super.TearDown();
        SUT = null;
    }    

    
    @Test 
    public void doPostTest() {

        // mock dependencies
        when(request.getParameter("ckanUrl")).thenReturn(testCatalog);
        when(request.getParameter("ckanSearchInput")).thenReturn("");
        when(request.getParameter("sEcho")).thenReturn("1");
        when(request.getParameter("iDisplayStart")).thenReturn("0");
        when(request.getParameter("iDisplayLength")).thenReturn("10");
        Catalogs.getSingleton().putCatalog(testCatalog);
        
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException e1) {
            Assert.fail();
        }

        // run
        try {
            SUT.doPost(request, response);
        } catch (Exception ex){
            Assert.fail();
        }

        verify(response).setStatus(HttpServletResponse.SC_OK);
        
    }
    

    /*
     * completely wrong url
     */
    @Test     
    public void doWrongUrlTest() {        
        
        // mock dependencies
        when(request.getParameter("ckanUrl")).thenReturn("Cirippa");
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException e1) {
            Assert.fail();
        }

        // run
        try {
            SUT.doPost(request, response);
        } catch (Exception ex){
            Assert.fail();
        }

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        
    }
    
    /**
     * trying to call a non-ckan url
     */
    @Test     
    public void doWrongUrl2Test() {        
        // mock dependencies
        when(request.getParameter("ckanUrl")).thenReturn("http://www.google.com");
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException e1) {
            Assert.fail();
        }

        // run
        try {
            SUT.doPost(request, response);
        } catch (Exception ex){
            Assert.fail();
        }

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        
    }    
    
    @Test 
    public void testSearch() throws Exception {
        int limit = 10;         
        Catalogs.getSingleton().putCatalog(testCatalog);
        SearchResults srs = Catalogs.getSingleton().getCatalog(testCatalog).search("", "csv", 0, limit);
        assert(srs.getResults().size() == limit);            
    }

        
    
}
