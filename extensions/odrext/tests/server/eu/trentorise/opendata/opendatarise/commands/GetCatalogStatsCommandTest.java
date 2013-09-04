/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.commands.history.CancelProcessesCommand;
import com.google.refine.tests.RefineTest;
import eu.trentorise.opendata.opendatarise.Catalogs;
import eu.trentorise.opendata.opendatarise.commands.CkanTest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
public class GetCatalogStatsCommandTest  extends CkanTest  {
  // System Under Test
    GetCatalogStatsCommand SUT;

 
    @Override
    @BeforeClass
    public void beforeClass() {
        System.out.println("GetCatalogStatsCommandTest.beforeClass ");
        super.beforeClass();
    }
    
    
    @BeforeMethod
    @Override
    public void SetUp() {
        System.out.println("GetCatalogStatsCommandTest.beforeTest ");
        super.SetUp();
        SUT = new GetCatalogStatsCommand();                
    }    
    

    @AfterMethod
    @Override
    public void TearDown() {
        System.out.println("GetCatalogStatsCommand.afterTest ");
        super.TearDown();
        SUT = null;
    }    
    
    
    
        

    /** Statistics need to be ready for this
     * 
     */
    @Test    
    public void doGetTest() {

        // mock dependencies
        when(request.getParameter("ckanUrl")).thenReturn(testCatalog);
        try {
            when(response.getWriter()).thenReturn(pw);
        } catch (IOException e1) {
            Assert.fail();
        }

        // run
        try {
            SUT.doGet(request, response);
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
            SUT.doGet(request, response);
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
            SUT.doGet(request, response);
        } catch (Exception ex){
            Assert.fail();
        }

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        
    }    
    
}
