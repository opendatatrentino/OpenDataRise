/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.Jsonizable;
import com.google.refine.ProjectManager;
import com.google.refine.commands.Command;
import com.google.refine.model.Project;
import eu.trentorise.opendata.opendatarise.Catalogs;
import eu.trentorise.opendata.opendatarise.OdrResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.deri.grefine.rdf.RdfSchema;
import org.deri.grefine.rdf.Util;
import org.deri.grefine.rdf.app.ApplicationContext;
import org.deri.grefine.rdf.commands.RdfCommand;
import org.deri.grefine.rdf.vocab.SearchResultItem;
import org.deri.grefine.rdf.vocab.Vocabulary;
import org.json.JSONException;
import org.json.JSONWriter;

/**
 *
 * @author David Leoni
 */
public class SuggestCatalogCommand extends Command {

    static class CatalogSuggestion {
        public String id;
        public String name;
    }
    
    static class CatalogSuggestions extends OdrResponse {

        public  String prefix;
        public  ArrayList<CatalogSuggestion> result;

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String query = request.getParameter("prefix");

            CatalogSuggestions odrResponse = new CatalogSuggestions();

            ArrayList<CatalogSuggestion>  suggestions = new ArrayList();
            for (String c : Catalogs.getSingleton().getCatalogsSet()) {
                if (c.contains(query)) {
                    CatalogSuggestion cs = new CatalogSuggestion();
                    cs.id = c;
                    cs.name = c;
                    suggestions.add(cs);
                }
            }
            
            odrResponse.prefix = query;
            odrResponse.result = suggestions;
            
            odrRespond(odrResponse, response);
            
        } catch (Exception e) {
            odrRespondException(response, e);
        }
    }
}
