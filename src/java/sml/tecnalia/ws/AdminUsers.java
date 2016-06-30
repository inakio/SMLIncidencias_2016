package sml.tecnalia.ws;

import java.sql.SQLException;
import java.util.ArrayList;
import sml.tecnalia.login.checkLogin;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import sml.tecnalia.login.checkMySQLDataBase;
import sml.tecnalia.routes.Route;
import sml.tecnalia.users.User;

/**
 * REST Web Service
 *
 * @author inaki
 */
@Path("adminusers")
public class AdminUsers {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Login
     */
    public AdminUsers() {
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
    /**
     * PUT method for creating a user
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */    
    @PUT
    @Path("altadeusuario")
    @Produces("application/json")
    public String altadeusuario(String content) throws Exception{  
        System.out.println("AdminUsers.altadeusuario::: content="+content);
        JSONObject obj = new JSONObject (content);
        System.out.println("AdminUsers.altadeusuario::: obj="+obj.toString());
        User usrs = null;
        try{
            ObjectMapper mapper = new ObjectMapper();
            usrs = mapper.readValue(obj.toString(), User.class);
        }catch(Exception ex){
            System.err.println("AdminUsers.altadeusuario::: ex="+ex.toString());
        }
        System.out.println("AdminUsers.altadeusuario:::Received data: " + content);      
        //Send data to Server
        String sResponse = User.altaUsuario(usrs);
        return(sResponse);
       }
    /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("saveTypes")
    @Produces("application/json")
    public String saveTypes(String data) throws Exception{
        System.out.println("AdminUsers.saveTypes()::: data="+data);
        JSONObject obj = new JSONObject (data);
        String name       = obj.getString("name");
        String types      = obj.getString("types");
        String levels     = obj.getString("levels");
        System.out.println("AdminUsers.saveTypes():::in java class saveTypes(data) name="+name+", types="+types+", levels="+levels);
        String out = "{\"name\":\""+name+"\",\"types\":\""+types+"\",\"levels\":\""+levels+"\"}";
        System.out.println("AdminUsers.saveTypes()::: out="+out);
        int ilevels = Integer.parseInt(levels);
        User.saveTypes(name, types, ilevels);
        System.out.println("AdminUsers.saveTypes():::tipos guardados");
        return out;
    } 
    /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("getInfo")
    @Produces("application/json")
    public String getInfoUser(String data) throws Exception{
        System.out.println("AdminUsers.getInfoUser()::: data="+data);
        JSONObject obj = new JSONObject (data);
        String name= obj.getString("name");
        System.out.println("AdminUsers.getInfoUser()::: user="+name);
        checkMySQLDataBase cmsqldb = new checkMySQLDataBase();

        String email                             = cmsqldb.getEmail(name);
        String passwd                            = cmsqldb.getPasswd(name);
        ArrayList<String> routeNames             = cmsqldb.getRouteNames(name);
        ArrayList<String> routes                 = cmsqldb.getRoutes(name);
        ArrayList<String> routeIds               = cmsqldb.getRouteIds(name);
        ArrayList<ArrayList<Integer>> routeTimes = cmsqldb.getRoutesTimes(name);
        ArrayList<String> routeAvisos            = cmsqldb.getRouteDiasAviso(name);
        ArrayList<String> routeMarkers           = cmsqldb.getRouteMarkers(name);
                
        String types                 = cmsqldb.getTypes(name);
        int    levels                = cmsqldb.getIncsLevel(name);
        String out = "{\"name\":\""+name+"\",\"passwd\":\""+passwd+"\",";//  }";
        out = out + "\"email\":\""+email+"\",";
        out = out + "\"types\":\""+types+"\",";
        out = out + "\"levels\":\""+levels+"\",";
        out = out + "\"routes\":[";
  
        for(int r=0;r<routeNames.size();r++){
            out = out + "{\"name\":\""+routeNames.get(r)+"\", \"index\":\""+routeIds.get(r)+"\", \"iniTime\":\""+routeTimes.get(r).get(0)+"\", \"endTime\":\""+routeTimes.get(r).get(1)+ "\", \"diasAviso\":\""+routeAvisos.get(r)+ "\",\"route\":\""+routes.get(r)+"\",\"markers\":\""+routeMarkers.get(r)+"\"},";
        }
        if (routeNames.size()!=0){out = out.substring(0,out.length()-1);}
        out = out + "]}";
        System.out.println("rutas->\n"+out);
        return out;
    }
        /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("changePasswd")
    @Produces("application/json")
    public void changePasswd(String data) throws Exception{
        System.out.println("AdminUsers.changePasswd()::: data="+data);
        JSONObject obj = new JSONObject (data);
        String name   = obj.getString("name");
        String passwd = obj.getString("passwd");
        System.out.println("AdminUsers.changePasswd()::: name="+name);
        User.savePasswd(name,passwd);
    }
    /**
     * PUT method for updating or creating an instance of AccesoService
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("changeEmail")
    @Produces("application/json")
    public void changeEmail(String data) throws Exception{
        System.out.println("AdminUsers.changeEmail()::: data="+data);
        JSONObject obj = new JSONObject (data);
        String name   = obj.getString("name");
        String email  = obj.getString("email");
        System.out.println("AdminUsers.changeEmail()::: user="+name);
        User.saveEmail(name,email);

    }    
    
    
}

