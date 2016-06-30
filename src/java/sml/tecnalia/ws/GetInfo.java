

package sml.tecnalia.ws;


import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


/**
 * REST Web Service
 *
 * @author inaki
 */
@Path("getInfo")
public class GetInfo {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Login
     */
    public GetInfo() {
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

   
}

