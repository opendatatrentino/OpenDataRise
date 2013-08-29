/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;


import com.google.refine.RefineServlet;
import eu.trentorise.opendata.ckanalyze.client.CkanalyzeClient;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogStats;
import eu.trentorise.opendata.ckanalyze.model.Types;
import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ckan.CKANException;
import org.ckan.resource.impl.Dataset;
import org.ckan.resource.impl.Resource;
import org.ckan.result.impl.DatasetSearchResult;
import org.ckan.result.list.impl.DatasetSearchList;
import org.ckan.result.list.impl.StringList;
import org.joda.time.DateTime;

class ResourceRow implements Serializable {

    Dataset dataset;
    Resource resource;
    ResourceStats stats;
}



/**
 *
 * @author David Leoni
 */
public class Catalogs implements Serializable {

    public static final String CATALOGS_DIR = "odrext/catalogs/";
    public static final String CATALOGS_FILE =  "catalogs.bin";
    public static final String DATI_TRENTINO_IT = "http://dati.trentino.it";
    public static final String DISI_CKANALYZE_URL = "http://opendata.disi.unitn.it:8080/ckanalyze-web";
    
    private HashMap<String, Catalog> catalogs;
    private String ckanalyzeServerUrl;
    private String lastUsedCatalog;
    private static Catalogs singleton;

    public synchronized String getCkanalyzeServerUrl(){
        return ckanalyzeServerUrl;
    }
    
    public synchronized String getLastUsedCatalog(){
        return lastUsedCatalog;
    }
    
    static String getAbsCatalogsFile(){        
        return RefineServlet.getCacheDir(CATALOGS_DIR).getAbsolutePath() + File.separator + CATALOGS_FILE;
    }
    
    public static final void init() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String absPath = getAbsCatalogsFile();
        try {            
            // loads catalogs           
            fis = new FileInputStream(absPath);
            ois = new ObjectInputStream(fis);
            singleton = (Catalogs) ois.readObject();            
        } catch (FileNotFoundException ex) {            
            ODR.logger.info("Didn't find any previous catalog information.");
        } catch (IOException ex) {            
            throw new OdrException("Couldn't read file: " + absPath, ex );            
            // todo should save corrupted file somewhere and better report error
        } catch (Exception ex) {
            throw new RuntimeException(ex);            
        } finally {
            try {
                
                if (fis != null) {
                    fis.close();                    
                }
                if (ois != null) {
                    fis.close();                    
                }
                
            } catch (Exception ex) {
                Logger.getLogger(Catalogs.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (singleton == null) {
                singleton = new Catalogs();
            }            
        }

    }

    /**
     * 
     * @param catalog
     * @return null if the catalog is not found
     */
    public synchronized Catalog getCatalog(String catalog) {
        Catalog res = catalogs.get(catalog);
        if (res == null){
            throw new RuntimeException("Catalog "+catalog+" is not present!");
        }
        return catalogs.get(catalog);
    }
    
    /**
     * 
     * 
    */
    public synchronized Catalog putCatalog(String catalogUrl){
        Catalog newCatalog = new Catalog(catalogUrl);
        catalogs.put(catalogUrl,newCatalog);
        return newCatalog;
    }    
  
    private Catalogs() {
        ckanalyzeServerUrl = DISI_CKANALYZE_URL; // todo should read it from somewhere
        catalogs = new HashMap<String,Catalog>();
        lastUsedCatalog = DATI_TRENTINO_IT;
    }    

    /**
     * Saves to disk all info OpenDataRise about catalogs
     * @param catalog 
     */
    public synchronized static void saveCatalogs(){        
        OutputStream file = null;
        ObjectOutput oos = null;
        
        String absPath = getAbsCatalogsFile();
        
        try {            
            
            file = new FileOutputStream( absPath);
            OutputStream buffer = new BufferedOutputStream( file );
            oos = new ObjectOutputStream( buffer );          
            oos.writeObject(singleton);
        } catch (Exception ex) {            
            throw new OdrException("Couldn't write to file: "+ absPath,ex);
        } finally {
            try {
                if (oos != null){
                    oos.close();
                }
                
                if (file != null){
                    file.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Catalogs.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
                 
    }
    
    
    /*
      Downloads catalogs data 
      postponed 
    void downloadOldCatalogMetadata() {
        for (Catalog c : getCatalogs()){
            
        }
    }
    
    * 
    * 
    void downloadCatalogMetadata(String catalogUrl) {
        
        try {
            StringList datasetList = catInfo.ckanClient.getDatasetList();
            for (String dsn : datasetList.result) {
                Dataset ds = ckanClient.getDataset(dsn);

                for (Resource res : ds.getResources()) {
                    ResourceStats resStats = catInfo.ckanalyzeClient.getResourceStats(catalogUrl, res.getId());
                    
                    ResourceRow row = new ResourceRow();
                    row.dataset = ds;
                    row.resource = res;
                    row.stats = resStats;
                }
            }
        } catch (CKANException ex) {
            throw new RuntimeException(ex);
        }
    }
    */ 
 

    private synchronized HashMap<String, Catalog> getCatalogs() {
        return catalogs;
    }
    
    public static Catalogs getSingleton(){
        if (singleton == null){
            throw new RuntimeException("Catalogs singleton has not been created yet!");
        }
        return singleton;
    }

    synchronized public Catalog putIfNotExistingCatalog(String catalogUrl) {
       if (!catalogs.containsKey(catalogUrl)){
           Catalog newCatalog = new Catalog(catalogUrl);
           catalogs.put(catalogUrl,newCatalog);
           return newCatalog;
       } else {
           return catalogs.get(catalogUrl);
       }
    }
}
