/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

/**
 * Trying to have a more uniform response system
 * @author David Leoni
 */
class OdrErrorResponse extends OdrResponse {

    private String message;

    /**
     *
     * @param message Description in natural language
     * @param responseCode HTTP response code
     */
    public OdrErrorResponse(int responseCode, String message) {
        super();
        setResponseCode(responseCode);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}