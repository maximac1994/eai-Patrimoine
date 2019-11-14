
import com.google.gson.Gson;
import entities.Equipement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import services.ServiceEquipementLocal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jerom
 */
@Path("equipement")
public class EquipementRest {
    
    ServiceEquipementLocal serviceEquipementLocal;
    
    @Context
    private UriInfo context;
    
    private Gson gson;

    public EquipementRest() {
        this.gson = new Gson();
        serviceEquipementLocal = lookupServiceEquipement();
    }
    
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response listerEquipements() {
        return Response.ok((gson.toJson(serviceEquipementLocal.listerEquipements()))).build();
    }
    
    private ServiceEquipementLocal lookupServiceEquipement() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceEquipementLocal) c.lookup("java:global/Patrimoine-ear/Patrimoine-ejb-1.0-SNAPSHOT/ServiceEquipement!services.ServiceEquipementLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
