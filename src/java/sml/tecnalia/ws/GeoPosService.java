/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sml.tecnalia.ws;

import java.awt.Component;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import sml.tecnalia.users.User;

/**
 * REST Web Service
 *
 * @author 106363
 */
@Path("geopos")
public class GeoPosService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AccesoService
     */
    public GeoPosService() {
    }

    /**
     * Retrieves representation of an instance of org.tecnalia.ws.AccesoService
     * @return an instance of java.lang.String
     */
    @GET
    @Path("prueba")
    @Produces("application/json")
    public String prueba() {
        System.out.println("Prueba del Servicio de GeoPos");
        return "Prueba del Servicio de GeoPos";
    }

    /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("insertpos")
    @Produces("application/json") 
    public String insertpos(String data) throws Exception{
        System.out.println("insertpos:::");
        System.out.println("insertpos:::rebote "+data);
        
        ResourceBundle config = ResourceBundle.getBundle("org.tecnalia.properties.main");
        String path=config.getString ("WS_PATH");

        //Reenviar el servicio REST.
        URL url = new URL(path+"/geopos/insertpos");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        
        //get response
        String sResponse = "{}";
        int iCode = conn.getResponseCode();
        if (iCode == 200)
        {
            //Ok --> get response data
            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line = buff.readLine();
            
            if (line != null)
                sResponse = line;
            else
                sResponse = "{'eCode': 11, 'eDesc': 'null response from SMLGetMobilePos'}";

            buff.close();
            in.close();
        }
        else
            sResponse = "{'eCode': 12, 'eDesc': 'Error connecting to SMLGetMobilePos. Http Code: " + iCode + "'}";
        
        conn.disconnect();
 
        return(sResponse);
    }
    
    @POST
    @Path("pruebaSimple")
    @Produces("application/json")
    public String Prueba(String content) throws Exception {
        System.out.println(content);
        return "Recibido!";
    }
    
    @PUT
    @Path("identify")
    @Produces("application/json") 
    //@Produces("text/plain")
    //public String identify(String data) throws Exception{
    public String identify(String data) throws Exception{
        System.out.println("identify:::");
        System.out.println("identify:::rebote "+data);
        //return "HOLA RE-PAKETE" ;
        
        ResourceBundle config = ResourceBundle.getBundle("org.tecnalia.properties.main");
        String path=config.getString ("WS_PATH");

        //Reenviar el servicio REST.
        URL url = new URL(path+"/geopos/identify");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        
        //get response
        String sResponse = "{}";
        int iCode = conn.getResponseCode();
        if (iCode == 200)
        {
            //Ok --> get response data
            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line = buff.readLine();
            
            if (line != null)
                sResponse = line;
            else
                sResponse = "{'eCode': 11, 'eDesc': 'null response from SMLGetMobilePos'}";

            buff.close();
            in.close();
        }
        else
            sResponse = "{'eCode': 12, 'eDesc': 'Error connecting to SMLGetMobilePos. Http Code: " + iCode + "'}";
        
        conn.disconnect();
 
        return(sResponse);

    }
    
    
    @PUT
    @Path("gettrajectory")
    @Produces("application/json") 
    public String gettrajectory(String data) throws Exception{
        ResourceBundle config = ResourceBundle.getBundle("org.tecnalia.properties.main");
        String path=config.getString ("WS_PATH");

        //Reenviar el servicio REST.
        URL url = new URL(path+"/geopos/gettrajectory");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");

        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();
        
        //get response
        String sResponse = "{}";
        int iCode = conn.getResponseCode();
        if (iCode == 200)
        {
            //Ok --> get response data
            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line = buff.readLine();
            
            if (line != null)
                sResponse = line;
            else
                sResponse = "{'eCode': 11, 'eDesc': 'null response from SMLGetMobilePos'}";

            buff.close();
            in.close();
        }
        else
            sResponse = "{'eCode': 12, 'eDesc': 'Error connecting to SMLGetMobilePos. Http Code: " + iCode + "'}";
        
        conn.disconnect();
 
        return(sResponse);
    }
    /*
    @Component
    public class SimpleCORSFilter implements Filter {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
                    HttpServletResponse response = (HttpServletResponse) res;
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
                    chain.doFilter(req, res);
            }
            public void init(FilterConfig filterConfig) {}
            public void destroy() {}

    }
    
    */
    
}
