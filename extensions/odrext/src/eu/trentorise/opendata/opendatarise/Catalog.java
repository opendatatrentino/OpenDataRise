/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import eu.trentorise.opendata.ckanalyze.client.CkanalyzeClient;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogStats;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.Nonnull;


/**
 *
 * @author David Leoni
 */
public  class Catalog implements Serializable {   
    private String url;    
    private CatalogStats stats;   
    private transient CkanalyzeClient ckanalyzeClient;
    private transient org.ckan.Client ckanClient;    
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        this.ckanalyzeClient = new CkanalyzeClient(Catalogs.getCkanalyzeServerUrl());             
        this.ckanClient = new org.ckan.Client(new org.ckan.Connection(url),"");                      
    };
    
    
    /**
     * 
     * @param url Must not be null.
     */
    public Catalog( @Nonnull String url){
        this.url = url;
        this.ckanalyzeClient = new CkanalyzeClient(Catalogs.getCkanalyzeServerUrl());     
        this.stats = ckanalyzeClient.getCatalogStats(this.url);        
        this.ckanClient = new org.ckan.Client(new org.ckan.Connection(url),"");        
    }

    public Catalog(String url, CatalogStats stats){
        this(url);
        this.stats = stats;
        
    };
    
    public String getUrl() {
        return url;
    }

    /**
     * if catalog stats are not available null is returned
     * 
     */
    public CatalogStats getStats() {
        return stats;
    }
    
    
/* todo p3 postponed   
    private ImmutableList<ResourceRow> metadata;        
    private DateTime lastStatsUpdate;
    private DateTime lastCkanUpdate;
    public Catalog(String url, CatalogStats stats, ImmutableList<ResourceRow> metadata, DateTime lastStatsUpdate, DateTime lastCkanUpdate ){
        if (url == null){
            throw new OdrException("catalogUrl must not be null.");
        }
    };
  */

    
}