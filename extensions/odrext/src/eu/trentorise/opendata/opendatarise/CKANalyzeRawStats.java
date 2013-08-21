/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import eu.trentorise.opendata.ckanalyze.client.CkanalyzeClient;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogStats;
import eu.trentorise.opendata.ckanalyze.model.Types;
import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import org.ckan.CKANException;
import org.ckan.resource.impl.Dataset;
import org.ckan.result.impl.DatasetSearchResult;
import org.ckan.result.list.impl.DatasetSearchList;






/**
 *
 * @author David Leoni
 */
public class CKANalyzeRawStats {
    
    static CKANalyzeRawStats catInfo;
    static String DATI_TRENTINO_IT = "http://dati.trentino.it";
    
    String catalogUrl;
    CkanalyzeClient ckanalyzeClient;
    CatalogStats catalogStats;
    org.ckan.Client ckanClient;

    
    
    public CatalogStats getCatalogStats(){
        return catalogStats;
    }

    
    
    public static final CKANalyzeRawStats getStats(){
        return catInfo;
    }
    
    public CKANalyzeRawStats() {
        this.catalogUrl = DATI_TRENTINO_IT;
        this.ckanalyzeClient = new CkanalyzeClient("http://opendata.disi.unitn.it:8080/ckanalyze-web");
        this.catalogStats = ckanalyzeClient.getCatalogStats(catalogUrl);
        ckanClient = new org.ckan.Client(new org.ckan.Connection(catalogUrl),"");        
    }
    

    public String formatCatalogColumnTypePercentage(String type){        
        Long count = this.catalogStats.getColsPerTypeMap().get(Types.valueOf(type));
                
        double d = ((double) count) / this.catalogStats.getTotalColsCount();
        try {
            NumberFormat percentFormat = NumberFormat.getPercentInstance(ODR.getLocale());
            percentFormat.setMaximumFractionDigits(1);     
            
            ODR.logger.debug("Count for " + type + "column is " + count);
        
            return  percentFormat.format(d); 
        } catch (IllegalArgumentException e){
            return ("Error converting percentage for " + type + ": value is " + d);            
        }
    }    
    
    /**
     * Just to try it out
     */
    public static final void tryCkanClient(){
        

        Dataset ds = null;
        DatasetSearchResult sr = null;
        try{
            sr = catInfo.ckanClient.searchDatasets("meteo");
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
        }

    }        
    
    public static final void init(){
         catInfo = new CKANalyzeRawStats();
         readCatalogStats();     
         ResourceStats resStats = catInfo.ckanalyzeClient.getResourceStats(DATI_TRENTINO_IT, "d0892ada-b8b9-43b6-81b9-47a86be126db");
         
         tryCkanClient();
         
         /*
         ODR.logger.debug("laghi monitorati column count: " + resStats.getColumnCount());         
         ODR.logger.debug("laghi monitorati string columns: " + resStats.getColsPerTypeMap().get(Types.STRING));
         ODR.logger.debug("laghi monitorati float columns: " + resStats.getColsPerTypeMap().get(Types.FLOAT));
         */
         
    }    
    
    public static final void readCatalogStats(){
       
        catInfo.catalogStats = catInfo.ckanalyzeClient.getCatalogStats("http://dati.trentino.it");
        //System.out.println(catalogStat.getAvgColsPerType());
                
    }
          
    public static final String formatAvgStringLength(){
        return VelocityHelper.formatAvg(catInfo.catalogStats.getAvgStringLength(), 2);
    }

    public static final String formatTotalFileSizeCount(){
        return VelocityHelper.formatFilesize(catInfo.catalogStats.getTotalFileSizeCount());
    }
    
    public static final String formatAvgColumnCount(){
        return VelocityHelper.formatAvg(catInfo.catalogStats.getAvgColumnCount());
    }
    
    public static final String formatAvgRowCount(){
        return VelocityHelper.formatAvg(catInfo.catalogStats.getAvgRowCount());
    }

    
    
    
}
