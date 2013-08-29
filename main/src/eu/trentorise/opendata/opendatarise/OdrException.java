/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

/**
 *
 * @author David Leoni
 */
public class OdrException extends RuntimeException {    
    public OdrException(String s){
        super("OdrException: " + s);
    }

    public OdrException(String s, Exception ex) {
        super("OdrException: " + s, ex);
    }
}
