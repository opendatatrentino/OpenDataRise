/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendatarise.prova;

import org.glassfish.jersey.client.JerseyWebTarget;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author david_2
 */
public class ProvaDeps {

    {
        System.out.println("HI I'm ProvaDeps!");
        try {
            WebTarget target;
            Client c = ClientBuilder.newClient();
            target = c.target("pippo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
