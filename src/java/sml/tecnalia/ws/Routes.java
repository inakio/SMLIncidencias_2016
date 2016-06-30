/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sml.tecnalia.ws;

import sml.tecnalia.login.checkMySQLDataBase;
import sml.tecnalia.routes.Route;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import sml.tecnalia.routes.NewRoute;


/**
 * REST Web Service
 *
 * @author inaki
 */
@Path("routes")
public class Routes {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Login
     */
    public Routes() {
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
    @Path("saveRoute")
    @Produces("application/json")
    public String saveRoute(String data) throws Exception{
        System.out.println("Routes.saveRoute():::data="+data);
        NewRoute nRoute = null;
        JSONObject obj  = new JSONObject (data);
        String status = "{\"status\":\"error\"}";
        try{
            ObjectMapper mapper = new ObjectMapper();
            nRoute = mapper.readValue(obj.toString(), NewRoute.class);
            Route rt = new Route(nRoute);
            status = "{\"status\":\"ok\"}";
        }catch(Exception ex){
            System.err.println("Routes.saveRoute::: ex="+ex.toString());
        }   
        System.out.println("Routes.saveRoute()::: in java class saveRute(data) data="+data); 
        return status;
    }
     /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("eraseRoute")
    @Produces("application/json")
    public String eraseRoute(String data) throws Exception{
        System.out.println("in eraseRoute() data="+data);
        System.out.println("in java class eraseRoute(data) data="+data);
        JSONObject obj = new JSONObject (data);
        String name          = obj.getString("name");
        String trajectory_id = obj.getString("trajectory_id");

        System.out.println("Routes.eraseRoute()::: name="+name+", trajectory_id="+trajectory_id);
        String out = "{\"name\":\""+name+"\",\"trajectory_id\":\""+trajectory_id+"\"}";
        checkMySQLDataBase cmsqldb = new checkMySQLDataBase();
        cmsqldb.eraseRoute(name, trajectory_id);
        
        return out;
    }
    /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("renameRoute")
    @Produces("application/json")
    public String renameRoute(String data) throws Exception{
        System.out.println("Routes.renameRoute():::in renameRoute() data="+data);
        System.out.println("Routes.renameRoute()::: data="+data);
        JSONObject obj = new JSONObject (data);
        String name          = obj.getString("name");
        String trajectory_id = obj.getString("trajectory_id");
        String route_name    = obj.getString("route_name");
        System.out.println("Routes.renameRoute():::in java class renameRoute(data) user="+name+", trajectory_id="+trajectory_id);
        String out = "{\"name\":\""+name+"\",\"trajectory_id\":\""+trajectory_id+"\",\"route_name\":\""+route_name+"\"}";
        checkMySQLDataBase cmsqldb = new checkMySQLDataBase();
        cmsqldb.renameRoute(name, trajectory_id, route_name);
        System.out.println("Routes.renameRoute():::trayectoria renombrada");
        return out;
    }
   
}

