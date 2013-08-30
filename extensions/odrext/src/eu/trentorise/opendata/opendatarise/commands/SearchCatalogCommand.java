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
import static eu.trentorise.opendata.opendatarise.Utils.colFreqToString;
import eu.trentorise.opendata.opendatarise.importing.SearchResult;
import eu.trentorise.opendata.opendatarise.importing.SearchResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ckan.resource.impl.Dataset;
import org.ckan.resource.impl.Group;
import org.ckan.resource.impl.Resource;
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

            SearchResults srs = catalog.search(ckanSearchInput, format, iDisplayStart, iDisplayLength);

            writer.object();
            writer.key("sEcho");
            writer.value(sEcho);
            writer.key("iTotalRecords");
            writer.value(srs.getiTotalRecords());
            writer.key("iTotalDisplayRecords");
            writer.value(srs.getiTotalDisplayRecords()); // todo
            writer.key("aaData");
            writer.array();
            for (SearchResult sr : srs.getResults()) {
                ResourceStats s = sr.getResourceStats();
                Resource r = sr.getResource();
                Dataset d = sr.getDataset();
                writer.array();
                writer.value(d == null ? "-" : d.getTitle());
                writer.value(r.getName());
                if (d == null) {
                    writer.value("-");
                } else {
                    List<Group> groups = d.getGroups();
                    if (groups.size() == 0) {
                        writer.value("-");
                    } else {
                        String groupsList = groups.get(0).getName();

                        for (int i = 1; i < groups.size(); i++) {
                            groupsList += " " + groups.get(i).getName();
                        }
                        writer.value(groupsList);
                    }

                }

                writer.value(r.getFormat());
                writer.value(s == null ? "-" : s.getColumnCount());
                writer.value(s == null ? "-" : s.getRowCount());
                writer.value(colFreqToString(s, Types.STRING));
                writer.value(colFreqToString(s, Types.FLOAT));
                writer.value(colFreqToString(s, Types.INT));
                writer.value(colFreqToString(s, Types.DATE));
                writer.value(colFreqToString(s, Types.GEOJSON));
                writer.value(colFreqToString(s, Types.EMPTY));
                writer.value(s == null ? "-" : s.getStringLengthAvg());
                writer.endArray();
            }            
            writer.endArray();
            writer.endObject();
            response.setStatus(HttpServletResponse.SC_OK);


        } catch (Exception ex) {
            respondException(response, ex);
        }
    }
}