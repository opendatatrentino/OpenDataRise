/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.ProjectManager;
import com.google.refine.commands.Command;
import static com.google.refine.commands.Command.DEFAULT_ADDITIONAL_CODE;
import com.google.refine.model.Project;
import com.google.refine.preference.PreferenceStore;
import com.google.refine.preference.TopList;
import eu.trentorise.opendata.ckanalyze.model.catalog.CatalogStats;
import eu.trentorise.opendata.opendatarise.Catalog;
import eu.trentorise.opendata.opendatarise.Catalogs;
import eu.trentorise.opendata.opendatarise.ODR;
import eu.trentorise.opendata.opendatarise.OdrResponse;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

/**
 *
 * @author David Leoni
 */
public class GetCatalogStatsCommand extends Command {

    
    static class CatalogStatsResponse extends OdrResponse {
        CatalogStats stats;


        public CatalogStats getStats() {
            return stats;
        }


        public void setStats(CatalogStats stats) {
            this.stats = stats;
        }
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {

            String catalogUrl = request.getParameter("ckanUrl");
            Catalog catalog = Catalogs.getSingleton().putCatalog(catalogUrl);
                        
                        
            CatalogStatsResponse odrResp = new CatalogStatsResponse();
            odrResp.setStats(catalog.getStats());
            odrRespond(odrResp, response);
                      
        } catch (Exception e) {
            odrRespondException(response, e);
        }

    }
}
