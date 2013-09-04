/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import eu.trentorise.opendata.opendatarise.Catalogs;
import java.io.File;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.mock;
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
public class CatalogsDbTest extends CkanTest{
    
    @Override
    @BeforeClass
    public void beforeClass() {
        System.out.println("CatalogsDbTest.beforeClass");
        super.beforeClass();
    }       
    
    
    @BeforeMethod
    @Override
    public void SetUp() {
        System.out.println("CatalogsDbTest.BeforeTest ");
        super.SetUp();        
    }      
    
    @AfterMethod 
    @Override
    public void TearDown() {
        System.out.println("CatalogsDbTest.AfterTest ");
        super.TearDown();        
    }          
    
    @Test
    public void testSaveCatalogs() {
        System.out.println("CatalogsDbTest.testSaveCatalogs()");
        assert testCatalog != null;
        assert testCatalog.length() > 0;
        Catalogs catalogs = Catalogs.getSingleton();
        catalogs.putCatalog(testCatalog);
        
        Catalogs.save();
        
        Catalogs.init();
        // throws if catalog is not found
        catalogs.getCatalog(testCatalog);
        
        File file = new File(Catalogs.getAbsCatalogsFile());
        assert file.exists();       

    }
    
}
