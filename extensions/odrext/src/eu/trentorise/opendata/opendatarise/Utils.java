/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import eu.trentorise.opendata.ckanalyze.model.Types;
import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author David Leoni
 */
public class Utils {
    
    
    
    /**
     * Returns frequency of a column of a given type. 
     * @param s
     * @param type
     * @return 
     */
    public static  String colFreqToString(ResourceStats s, Types type) {
        if (s == null) return "-";
        Integer freq = s.getColsPerTypeMap().get(type);        
        if (freq == null) {
            return "0";
        } else {
            return freq.toString();
        }
    }    
    
    /**
     * Performs a get call. If return code is different than 200 an exception is thrown.
     * @param url
     * @return 
     */
    public static String getRest(String url){
        int code;
        String body;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            ResponseHandler<String> handler = new BasicResponseHandler();
            HttpResponse response = client.execute(httpGet);
            body = handler.handleResponse(response);
            code = response.getStatusLine().getStatusCode();
            
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            
            if (code != 200){
                throw new RuntimeException("Expected http response code of 200. Got "+ code);
            }
            return body;   
    }    
}
