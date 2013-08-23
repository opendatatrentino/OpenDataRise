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

            String catalogUri = request.getParameter("catalogUri");
            Catalog catalog = Catalogs.getCatalog(catalogUri);
            if (catalog == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {

                writer.object();
                writer.key("");

                String stats = om.writeValueAsString(Catalogs.getCatalog(catalogUri).getStats());
                ODR.logger.debug("Stats to send: \n" + stats);
                writer.key("stats");
                writer.value(stats);

                writer.endObject();
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (JSONException e) {
            respondException(response, e);
        }



    }
}
