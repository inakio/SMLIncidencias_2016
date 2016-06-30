/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sml.tecnalia.ws;

import sml.tecnalia.login.checkLogin;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 *
 * @author inaki
 */
@Path("login")
public class Login {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Login
     */
    public Login() {
    }

    /**
     * Retrieves representation of an instance of com.tecnalia.ws.Login
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

     /**
     * Retrieves representation of an instance of org.tecnalia.ws.AccesoService
     * @return an instance of java.lang.String
     */
    @GET
    @Path("prueba")
    @Produces("application/json")
    public String prueba() {
        return "Prueba del Servicio de Login";
    }

    
    /**
     * PUT method for updating or creating an instance of Login
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("login")
    @Produces("application/json")
    public String loginService(String data) throws Exception{
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!data="+data);
        JSONObject obj = new JSONObject (data);
        String user= obj.getString("user");
        String passwd =obj.getString("passwd");
        checkLogin cl;
        cl = new checkLogin(user,passwd);
        System.out.println("cl.check()="+cl.check());
        if ( cl.check() ){
            return("0");
        }
        else{
            return("1");
        }
    }
}

