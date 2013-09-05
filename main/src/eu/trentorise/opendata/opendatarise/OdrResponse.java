/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise;

import static com.google.refine.commands.Command.DEFAULT_ADDITIONAL_CODE;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author David Leoni
 */

    /**
     * odr new To fight the horror
     */
    public class OdrResponse {

        @JsonIgnore
        private int responseCode;
        private int additionalResponseCode;
        private String module;

        /**
         * Defaults to a 200 response code and empty string for module;
         */
        public OdrResponse() {

            this.responseCode = HttpServletResponse.SC_OK;
            this.additionalResponseCode = DEFAULT_ADDITIONAL_CODE;
            this.module = "";
        }

        public int getAdditionalResponseCode() {
            return additionalResponseCode;
        }

            
        public int getResponseCode() {
            return responseCode;
        }

        public String getModule() {
            return module;
        }

        /**
         * @param responseCode the responseCode to set
         */
        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        /**
         * @param additionalResponseCode the additionalResponseCode to set
         */
        public void setAdditionalResponseCode(int additionalResponseCode) {
            this.additionalResponseCode = additionalResponseCode;
        }

        /**
         * @param module the module to set
         */
        public void setModule(String module) {
            this.module = module;
        }
    }



