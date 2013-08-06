/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import eu.trentorise.opendata.ckanalyze.client.CkanalyzeClient;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogueDatatypeCount;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogueStat;
import java.text.NumberFormat;
import org.apache.velocity.VelocityContext;





/**
 *
 * @author David Leoni
 */
public class CKANalyzeRawStats {
    
    static CKANalyzeRawStats stats;
    
    CkanalyzeClient client;
    CatalogueStat catalogStats;

    
    
    public CatalogueStat getCatalogStats(){
        return catalogStats;
    }

    
    
    public static final CKANalyzeRawStats getStats(){
        return stats;
    }
    
    public CKANalyzeRawStats() {
        client = new CkanalyzeClient("http://opendata.disi.unitn.it:8080/ckanalyze-web");
        this.catalogStats = client.getCatalogueStat("http://dati.trentino.it");
    }
    

    public String formatCatalogColumnTypePercentage(String type){
        Double d = this.catalogStats.getAvgColsPerTypeMap().get(type);
        try {
            double sum = 0.0;
            for (CatalogueDatatypeCount c : this.catalogStats.getAvgColsPerType()){
                sum += c.getCount();
            }

            NumberFormat percentFormat = NumberFormat.getPercentInstance(ODR.getLocale());
            percentFormat.setMaximumFractionDigits(1);
            return percentFormat.format(d );
        } catch (IllegalArgumentException e){
            return ("Error converting percentage for " + type + ": value is " + d);
            
        }
    }    
    
    
    
    
    
    public static final void init(){
         stats = new CKANalyzeRawStats();
         readCatalogueStats();                
    }    
    
    public static final void readCatalogueStats(){
       
        stats.catalogStats = stats.client.getCatalogueStat("http://dati.trentino.it");
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
