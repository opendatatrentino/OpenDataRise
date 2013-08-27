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
public class SearchCatalogCommand extends Command {

    @Override
    /**
     * had issues using GET in browser side  datatables
     */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
 
        try {
            /*            ObjectMapper om = new ObjectMapper(); */
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");
            JSONWriter writer = new JSONWriter(response.getWriter());

            String catalogUrl = request.getParameter("ckanUrl");
            int sEcho = Integer.parseInt(request.getParameter("sEcho"));
            int iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
            ODR.logger.debug("iDisplayStart = " + iDisplayStart);            
            int iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
            ODR.logger.debug("iDisplayLength = " + iDisplayLength);
            /*            Catalog catalog = Catalogs.getCatalog(catalogUrl);
             if (catalog == null) {
             response.setStatus(HttpServletResponse.SC_NOT_FOUND);
             } else { */

            writer.object();
            writer.key("sEcho");
            writer.value(sEcho);
            writer.key("iTotalRecords");
            writer.value(2); // todo
            writer.key("iTotalDisplayRecords");
            writer.value(2); // todo
            writer.key("aaData");
            writer.array();
            writer.array();
            writer.value("Anagrafica campi neve");
            writer.value("Anagrafica marilleva in formato XML");
            writer.value("Meteo");            
            writer.value( "CSV");
            writer.value( 11);
            writer.value( 126);
            writer.value( 2);
            writer.value( 1);
            writer.value( 4);
            writer.value( 2);
            writer.value( 0);
            writer.value( 2);
            writer.value( 6.8);
            writer.endArray();

            writer.array();
            writer.value("Bollettino meteo");
            writer.value("Bollettino Val di Non e zone limitrofe");
            writer.value("Meteo");            
            writer.value( "CSV");
            writer.value( 10);
            writer.value( 116);
            writer.value( 2);
            writer.value( 1);
            writer.value( 4);
            writer.value( 1);
            writer.value( 1);
            writer.value( 1);
            writer.value( 7.2);
            writer.endArray();
            writer.endArray();
            /*            String stats = om.writeValueAsString(Catalogs.getCatalog(catalogUri).getStats());
             ODR.logger.debug("Stats to send: \n" + stats);
             writer.key("stats");
             writer.value(stats);
             */
            writer.endObject();
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (JSONException e) {
            respondException(response, e);
        }
       
    }
}