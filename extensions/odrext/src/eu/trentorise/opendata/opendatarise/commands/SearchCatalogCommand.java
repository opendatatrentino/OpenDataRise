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
import eu.trentorise.opendata.ckanalyze.model.Types;
import eu.trentorise.opendata.ckanalyze.model.resources.ResourceStats;
import eu.trentorise.opendata.opendatarise.Catalog;
import eu.trentorise.opendata.opendatarise.Catalogs;
import eu.trentorise.opendata.opendatarise.ODR;
import eu.trentorise.opendata.opendatarise.Utils;
import static eu.trentorise.opendata.opendatarise.Utils.colFreq;
import eu.trentorise.opendata.opendatarise.importing.SearchResult;
import eu.trentorise.opendata.opendatarise.importing.SearchResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ckan.resource.impl.Group;
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
     * had issues using GET in browser side datatables
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            /*            ObjectMapper om = new ObjectMapper(); */
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");
            JSONWriter writer = new JSONWriter(response.getWriter());

            String catalogUrl = request.getParameter("ckanUrl");
            String ckanSearchInput = request.getParameter("ckanSearchInput");
            
            String format = request.getParameter("format");

            /*          
             ArrayList<String> formats = new ArrayList<String>();            
             if (Boolean.parseBoolean(request.getParameter("formatCsv"))){
             formats.add("csv");
             };
             if (Boolean.parseBoolean(request.getParameter("formatXml"))){
             formats.add("xml");
             };            
             if (Boolean.parseBoolean(request.getParameter("formatJson"))){
             formats.add("json");
             };
             */


            int sEcho = Integer.parseInt(request.getParameter("sEcho"));
            int iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
            ODR.logger.debug("iDisplayStart = " + iDisplayStart);
            int iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
            ODR.logger.debug("iDisplayLength = " + iDisplayLength);

            
            Catalog catalog = Catalogs.getSingleton().putIfNotExistingCatalog(catalogUrl);

            SearchResults sr = catalog.search(ckanSearchInput, format, iDisplayStart, iDisplayLength);

            writer.object();
            writer.key("sEcho");
            writer.value(sEcho);
            writer.key("iTotalRecords");
            writer.value(sr.getiTotalRecords());
            writer.key("iTotalDisplayRecords");
            writer.value(sr.getiTotalDisplayRecords()); // todo
            writer.key("aaData");
            writer.array();
            for (SearchResult r : sr.getResults()) {
                ResourceStats s = r.getResourceStats();
                writer.array();
                writer.value(r.getDataset().getName());
                writer.value(r.getResource().getName());
                List<Group> groups = r.getDataset().getGroups();
                if (groups.size() > 0) {
                    writer.value("-");
                } else {
                    writer.value(groups.get(0).getName());
                    for (int i = 1; i < groups.size(); i++) {
                        writer.value("," + groups.get(i).getName());
                    }

                }

                writer.value(r.getResource().getFormat());
                writer.value(s.getColumnCount());
                writer.value(s.getRowCount());
                writer.value(colFreq(s, Types.STRING));
                writer.value(colFreq(s, Types.FLOAT));
                writer.value(colFreq(s, Types.INT));
                writer.value(colFreq(s, Types.DATE));
                writer.value(colFreq(s, Types.GEOJSON));
                writer.value(colFreq(s, Types.EMPTY));
                writer.value(s.getStringLengthAvg());
                writer.endArray();
            }
            writer.array();
            writer.endArray();
            writer.endArray();
            /*            String stats = om.writeValueAsString(Catalogs.getCatalog(catalogUri).getStats());
             ODR.logger.debug("Stats to send: \n" + stats);
             writer.key("stats");
             writer.value(stats);
             */
            writer.endObject();
            response.setStatus(HttpServletResponse.SC_OK);


        } catch (Exception ex) {
            respondException(response, ex);
        }
    }
}