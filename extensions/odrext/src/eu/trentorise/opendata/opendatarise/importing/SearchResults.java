/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.importing;

import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Leoni
 */
public class SearchResults {
    private ArrayList<SearchResult> results;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    public SearchResults(ArrayList<SearchResult> results, int iTotalRecords,int iTotalDisplayRecords){
        this.results = results;
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    /**
     * @return the results
     */
    public ArrayList<SearchResult> getResults() {
        return results;
    }

  
    /**
     * @return the iTotalRecords
     */
    public int getiTotalRecords() {
        return iTotalRecords;
    }

   
    /**
     * @return the iTotalDisplayRecords
     */
    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }


}
