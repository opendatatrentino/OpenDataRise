/*
CKANClient-J - Data Catalogue Software client in Java
Copyright (C) 2013 Newcastle University
Copyright (C) 2012 Open Knowledge Foundation

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.ckan;

import java.net.URL;

import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.SingleClientConnManager;

/**
 * Connection holds the connection details for this session
 *
 * @author      Andrew Martin <andrew.martin@ncl.ac.uk>, Ross Jones <ross.jones@okfn.org>
 * @version     1.8
 * @since       2012-05-01
 */
public final class Connection {

    private String m_host;
    private int m_port;
    private String _apikey = null;

    public Connection(  ) {
        this("http://datahub.io", 80);
    }

    public Connection( String host  ) {
        this( host, 80 );
    }

    public Connection( String host, int port ) {
        this.m_host = host;
        this.m_port = port;

        try {
            URL u = new URL( this.m_host + ":" + this.m_port + "/api");
        } catch ( MalformedURLException mue ) {
            System.out.println(mue);
        }
    }

    public void setApiKey( String key ) {
        this._apikey = key;
    }


    /**
    * Makes a POST request
    *
    * Submits a POST HTTP request to the CKAN instance configured within
    * the constructor, returning the entire contents of the response.
    *
    * @param  path The URL path to make the POST request to
    * @param  data The data to be posted to the URL
    * @returns The String contents of the response
    * @throws A CKANException if the request fails
    */
    protected String post(String path, String data)
        throws CKANException {
        URL url = null;

        try {
            url = new URL( this.m_host + ":" + this.m_port + path);
        } catch ( MalformedURLException mue ) {
            System.err.println(mue);
            return null;
        }

        String body = "";

    BasicClientConnectionManager bccm = null;
    ClientConnectionManager cm = null;
    try{
        /***********************************************************************/
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // set up a TrustManager that trusts everything
        sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                            System.out.println("getAcceptedIssuers =============");
                            return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs,
                                    String authType) {
                            System.out.println("checkClientTrusted =============");
                    }

                    public void checkServerTrusted(X509Certificate[] certs,
                                    String authType) {
                            System.out.println("checkServerTrusted =============");
                    }
        } }, new SecureRandom());
        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        Scheme httpsScheme = new Scheme("https", 443, sf);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(httpsScheme);
        //bccm = new BasicClientConnectionManager(schemeRegistry);
        // apache HttpClient version >4.2 should use BasicClientConnectionManager
        cm = new SingleClientConnManager(schemeRegistry);
        /***********************************************************************/
    }catch(KeyManagementException kme){System.out.println("Con ex: "+kme.getMessage());}
    catch(NoSuchAlgorithmException nsae){System.out.println("Con ex: "+nsae.getMessage());}

        //HttpClient httpclient = new DefaultHttpClient(cm);
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost postRequest = new HttpPost(url.toString());
            postRequest.setHeader( "X-CKAN-API-Key", this._apikey );

		    StringEntity input = new StringEntity(data);
		    input.setContentType("application/json");
		    postRequest.setEntity(input);

            HttpResponse response = httpclient.execute(postRequest);
            int statusCode = response.getStatusLine().getStatusCode();

            BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

            String line = "";
		    while ((line = br.readLine()) != null) {
                body += line;
		    }
        } catch( IOException ioe ) {
            System.out.println( ioe );
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return body;
    }

}






