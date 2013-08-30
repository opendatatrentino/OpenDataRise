/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import eu.trentorise.opendata.ckanalyze.client.CkanalyzeClient;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogStats;
import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import eu.trentorise.opendata.opendatarise.importing.SearchResult;
import eu.trentorise.opendata.opendatarise.importing.SearchResults;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import org.apache.commons.httpclient.methods.GetMethod;
import org.ckan.resource.impl.Dataset;
import org.ckan.resource.impl.Resource;
import org.ckan.result.impl.ResourceResult;
import org.ckan.result.impl.ResourceStatusResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author David Leoni
 */
public class Catalog implements Serializable {

    private String url;
    private transient CatalogStats stats; // todo not transient when stats are serializable 
    private transient CkanalyzeClient ckanalyzeClient;
    private transient org.ckan.Client ckanClient;

    
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.ckanalyzeClient = new CkanalyzeClient(Catalogs.getSingleton().getCkanalyzeServerUrl());
        // todo delete when stats are serializable 
        try {
            this.stats = ckanalyzeClient.getCatalogStats(this.url);
        } catch (Exception ex) {
            this.stats = null;
        }  
        this.ckanClient = new org.ckan.Client(new org.ckan.Connection(url), "");
    }

    ;
    
    
    /**
     * 
     * @param url Must not be null.
     */
    public Catalog(@Nonnull String url) {
        this.url = url;
        this.ckanalyzeClient = new CkanalyzeClient(Catalogs.getSingleton().getCkanalyzeServerUrl());
        try {
            this.stats = ckanalyzeClient.getCatalogStats(this.url);
        } catch (Exception ex) {
            this.stats = null;
        }        
        this.ckanClient = new org.ckan.Client(new org.ckan.Connection(url), "");
        
    }

    public Catalog(String url, CatalogStats stats) {
        this(url);
        this.stats = stats;

    }

    ;
    
    public String getUrl() {
        return url;
    }

    /**
     * @return catalog stats. If they are not available returns null
     *
     */
    public CatalogStats getStats() {
        return stats;
    }

    /**
     * 
     * @param ckanSearchInput
     * @param format  the Format to use. If null, no format is used.
     * @param offset
     * @param limit
     * @return 
     */
    public SearchResults search(String ckanSearchInput, String format, int offset, int limit) {
        
         try {
                
                // returns all the json 
                String resourcesQuery = url + "/api/3/search/resource?";
                if (format != null) {
                    resourcesQuery += "format="+format+"&amp;";
                }
                resourcesQuery += "description="+ckanSearchInput+"&amp;offset="+offset+"&amp;limit="+limit + "&amp;all_fields=1";;
                String stringa = Utils.getRest(resourcesQuery);
                
                ODR.logger.debug("queryResource = " + resourcesQuery);
                ODR.logger.debug("getRest = " + stringa);

                JSONObject resourcesQueryResponse = new JSONObject(stringa);   
                
                
                JSONArray resourcesList = resourcesQueryResponse.getJSONArray("results");
                
                ArrayList<SearchResult> results = new ArrayList<SearchResult>();
                
                for (int i = 0; i < resourcesList.length(); i++){
                    //ckanClient.getResource(resourcesList.getString(i));  // this stinky command uses old ckan api
                    Resource resource = null;
                    try {
                        resource = ckanClient.getGsonObjectFromGenericJson(Resource.class,resourcesList.getString(i),"getResource");
                        if (resource == null) {
                            throw new NullPointerException("Resource must not be null");
                            
                        }
                    } catch (Exception ex){
                        continue; // problematic resources are not included in the search                        
                    }

                    
                    Dataset dataset;
                    try {                        
                        dataset = ckanClient.getDataset(resource.getPackage_id());
                    } catch (Exception ex){
                        dataset = null;                    
                    }
                    
                    ResourceStats stats;
                    try {
                         stats = ckanalyzeClient.getResourceStats(url, resource.getId());
                    } catch (Exception ex) {
                        stats = null;
                    }
                    
                    SearchResult result = new SearchResult(resource, dataset, stats);
                    results.add(result);
                    
                }
                    
                
                return new SearchResults(results, resourcesQueryResponse.getInt("count"),resourcesQueryResponse.getInt("count"));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }        
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