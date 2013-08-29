/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.importing;

import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import org.ckan.resource.impl.Dataset;
import org.ckan.resource.impl.Resource;
import org.json.JSONObject;

/**
 *
 * @author David Leoni
 */
public class SearchResult {
    private Dataset dataset;    
    private Resource resource;    
    private ResourceStats resourceStats;
    
   

    public SearchResult(Dataset dataset, Resource resource, ResourceStats resourceStats){
        this.dataset = dataset;
        this.resource = resource;
        this.resourceStats  = resourceStats;
    }

    public Resource getResource() {
        return resource;
    }

    public ResourceStats getResourceStats() {
        return resourceStats;
    }

    public Dataset getDataset() {
        return dataset;
    }
    
    
}
