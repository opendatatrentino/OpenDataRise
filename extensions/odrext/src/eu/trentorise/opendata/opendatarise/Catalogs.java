/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
    
    private static ImmutableMap<String, Catalog> catalogs;
    private static String ckanalyzeServerUrl;

    static public String getCkanalyzeServerUrl(){
        return ckanalyzeServerUrl;
    }
    public static final void init() {
        FileInputStream fin = null;
        String fn = CATALOGS_DIR + CATALOGS_FILE;
        try {
            ckanalyzeServerUrl = DISI_CKANALYZE_URL; // todo should read it from somewhere
            // loads catalogs
            File dir = RefineServlet.getCacheDir(CATALOGS_DIR);
            fin = new FileInputStream(dir.getAbsolutePath() + "/" + CATALOGS_FILE);
            ObjectInputStream ois = new ObjectInputStream(fin);
            catalogs = (ImmutableMap<String, Catalog>) ois.readObject();
            
        } catch (FileNotFoundException ex) {            
            ODR.logger.info("Didn't find any previous catalog information.");
        } catch (IOException ex) {            
            throw new OdrException("Couldn't read file: " + fn );            
            // todo should save corrupted file somewhere and better report error
        } catch (Exception ex) {
            throw new RuntimeException(ex);            
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(Catalogs.class.getName()).log(Level.SEVERE, null, ex);
            }
            catalogs = ImmutableMap.<String, Catalog>of();            
        }

    }

    /**
     * 
     * @param catalog
     * @return null if the catalog is not found
     */
    public static Catalog getCatalog(String catalog) {
        return catalogs.get(catalog);
    }
    
  
    private Catalogs() {
        throw new OdrException("Why are you calling me!?");
    }    

    /**
     * Saves to disk all info OpenDataRise got about a given catalog
     * @param catalog 
     */
    private synchronized void saveCatalogs(Catalog catalog){        
        OutputStream file = null;
        try {            
            File dir = RefineServlet.getCacheDir(CATALOGS_DIR);            
            file = new FileOutputStream( dir.getAbsolutePath() + "/" + CATALOGS_FILE );
            OutputStream buffer = new BufferedOutputStream( file );
            ObjectOutput output = new ObjectOutputStream( buffer );          
            output.writeObject(catalogs);
        } catch (Exception ex) {
            throw new OdrException("Couldn't write to file: "+ CATALOGS_DIR + CATALOGS_FILE);
        } finally {
            try {
                file.close();
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
 




    public String formatCatalogColumnTypePercentage(String catalogUrl, String type) {
        Catalog catalog = catalogs.get(catalogUrl);
        Long count = catalog.getStats().getColsPerTypeMap().get(Types.valueOf(type));

        double d = ((double) count) / catalog.getStats().getTotalColsCount();
        try {
            NumberFormat percentFormat = NumberFormat.getPercentInstance(ODR.getLocale());
            percentFormat.setMaximumFractionDigits(1);

            ODR.logger.debug("Count for " + type + "column is " + count);

            return percentFormat.format(d);
        } catch (IllegalArgumentException e) {
            return ("Error converting percentage for " + type + ": value is " + d);
        }
    }


    public static final String formatAvgStringLength(String catalogUri) {
        return VelocityHelper.formatAvg(catalogs.get(catalogUri).getStats().getAvgStringLength(), 2);
    }

    public static final String formatTotalFileSizeCount(String catalogUri) {
        return VelocityHelper.formatFilesize(catalogs.get(catalogUri).getStats().getTotalFileSizeCount());
    }

    public static final String formatAvgColumnCount(String catalogUri) {
        return VelocityHelper.formatAvg(catalogs.get(catalogUri).getStats().getAvgColumnCount());
    }

    public static final String formatAvgRowCount(String catalogUri) {
        return VelocityHelper.formatAvg(catalogs.get(catalogUri).getStats().getAvgRowCount());
    }




    private synchronized ImmutableMap<String, Catalog> getCatalogs() {
        return catalogs;
    }
}
