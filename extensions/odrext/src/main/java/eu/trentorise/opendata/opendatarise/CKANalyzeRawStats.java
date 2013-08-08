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






/**
 *
 * @author David Leoni
 */
public class CKANalyzeRawStats {
    
    static CKANalyzeRawStats stats;
    static String DATI_TRENTINO_IT = "http://dati.trentino.it";
    
    CkanalyzeClient client;
    CatalogStats catalogStats;
    

    
    
    public CatalogStats getCatalogStats(){
        return catalogStats;
    }

    
    
    public static final CKANalyzeRawStats getStats(){
        return stats;
    }
    
    public CKANalyzeRawStats() {
        client = new CkanalyzeClient("http://opendata.disi.unitn.it:8080/ckanalyze-web");
        this.catalogStats = client.getCatalogStats(DATI_TRENTINO_IT);
        
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
    
                
    public static final void init(){
         stats = new CKANalyzeRawStats();
         readCatalogStats();     
         ResourceStats resStats = stats.client.getResourceStats(DATI_TRENTINO_IT, "d0892ada-b8b9-43b6-81b9-47a86be126db");
        
         /*
         ODR.logger.debug("laghi monitorati column count: " + resStats.getColumnCount());         
         ODR.logger.debug("laghi monitorati string columns: " + resStats.getColsPerTypeMap().get(Types.STRING));
         ODR.logger.debug("laghi monitorati float columns: " + resStats.getColsPerTypeMap().get(Types.FLOAT));
         */
         
    }    
    
    public static final void readCatalogStats(){
       
        stats.catalogStats = stats.client.getCatalogStats("http://dati.trentino.it");
        //System.out.println(catalogStat.getAvgColsPerType());
                
    }
          
    public static final String formatAvgStringLength(){
        return VelocityHelper.formatAvg(stats.catalogStats.getAvgStringLength(), 2);
    }

    public static final String formatTotalFileSizeCount(){
        return VelocityHelper.formatFilesize(stats.catalogStats.getTotalFileSizeCount());
    }
    
    public static final String formatAvgColumnCount(){
        return VelocityHelper.formatAvg(stats.catalogStats.getAvgColumnCount());
    }
    
    public static final String formatAvgRowCount(){
        return VelocityHelper.formatAvg(stats.catalogStats.getAvgRowCount());
    }

    
    
    
}
