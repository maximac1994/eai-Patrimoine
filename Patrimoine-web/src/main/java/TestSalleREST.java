
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import ressources.RSalle;
import services.ServiceSalleLocal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jerom
 */
@Path("testSalle")
public class TestSalleREST {
    
    ServiceSalleLocal serviceSalleLocal;
    
    @Context
    private UriInfo context;

    // Convertisseur JSON
    private Gson gson;
    public TestSalleREST(){
        this.gson = new Gson();
        serviceSalleLocal = lookupServiceSalle();
    }
    
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public String getJson() {
        return(gson.toJson(serviceSalleLocal.listerSalles()));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createSalle(String content) {
        
//        try {
//            return Response.ok(this.gson.toJson(this.serviceSalleLocal.ajouterSalle(content))).build();
//        } catch (SalleExistanteException ex) {
//            
//        }
    }
    
    private ServiceSalleLocal lookupServiceSalle() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceSalleLocal) c.lookup("java:global/Patrimoine-ear/Patrimoine-ejb-1.0-SNAPSHOT/ServiceSalle!services.ServiceSalleLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
