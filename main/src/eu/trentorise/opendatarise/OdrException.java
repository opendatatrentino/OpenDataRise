/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendatarise;

/**
 *
 * @author David Leoni
 */
public class OdrException extends RuntimeException {    
    public OdrException(String s){
        super("OdrException: " + s);
    }
}
