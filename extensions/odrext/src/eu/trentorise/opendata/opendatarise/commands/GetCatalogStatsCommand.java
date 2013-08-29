/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.ProjectManager;
import com.google.refine.commands.Command;
import com.google.refine.model.Project;
import com.google.refine.preference.PreferenceStore;
import com.google.refine.preference.TopList;
import eu.trentorise.opendata.opendatarise.Catalog;
import eu.trentorise.opendata.opendatarise.Catalogs;
import eu.trentorise.opendata.opendatarise.ODR;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 *
 * @author David Leoni
 */
public class GetCatalogStatsCommand extends Command {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {
            ObjectMapper om = new ObjectMapper();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");
            JSONWriter writer = new JSONWriter(response.getWriter());

            String catalogUrl = request.getParameter("ckanUrl");
            Catalog catalog = Catalogs.getSingleton().putCatalog(catalogUrl);
            if (catalog.getStats() == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                ODR.logger.debug("Stats to send: \n" + catalog.getStats());
                writer.object();
                writer.key("stats");
                writer.value(catalog.getStats());
                writer.endObject();
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            respondException(response, e);
        }



    }
}
