/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.commands;

import com.google.refine.commands.Command;
import com.google.refine.model.AbstractOperation;
import com.google.refine.model.Project;
import com.google.refine.util.ParsingUtilities;
import java.io.IOException;


import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.google.refine.process.Process;
import eu.trentorise.opendata.opendatarise.operations.SetStepOperation;


/**
 *
 * @author David Leoni
 */
public class SetStepCommand extends Command {
    
    
    public SetStepCommand() {
		
    }

	/**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Project project = getProject(request);
            
            int step = Integer.parseInt(request.getParameter("step"));
            /*
            String jsonString = request.getParameter("schema");
            JSONObject json = ParsingUtilities.evaluateJsonStringToObject(jsonString);
            RdfSchema schema = RdfSchema.reconstruct(json);
            */
            
            AbstractOperation op = new SetStepOperation(step);
            Process process = op.createProcess(project, new Properties());
            
            performProcessAndRespond(request, response, project, process);
            
            /*project.schema = schema;
            
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "application/json");
            
            Writer w = response.getWriter();
            JSONWriter writer = new JSONWriter(w);
            writer.object();
            writer.key("code"); writer.value("ok");
            writer.key("historyEntry"); 
            
            //dummy history for now
            writer.object();
            writer.key("op"); writer.value("saveRdfSchema");
            writer.key("description"); writer.value("Save RDF schema");
            writer.endObject();
            
            writer.endObject();
            
            w.flush();
            w.close();*/
            
        } catch (Exception e) {
            respondException(response, e);
        }
    }    
    

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        throw new UnsupportedOperationException();
    };

    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        throw new UnsupportedOperationException();
    };

    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        throw new UnsupportedOperationException();
    };
    
}
