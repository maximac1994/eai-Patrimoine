
import com.google.gson.Gson;
import entities.Salle;
import exceptions.SalleInconnueException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
@Path("salle")
public class SalleRest {
    
    ServiceSalleLocal serviceSalleLocal;
    
    @Context
    private UriInfo context;

    // Convertisseur JSON
    private Gson gson;
    public SalleRest(){
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
        RSalle salle = gson.fromJson(content, RSalle.class);
        serviceSalleLocal.ajouterSalle(salle);        
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifierCapaciteSalle(String c) {
        RSalle salle1 = gson.fromJson(c, RSalle.class);
        String numeroSalle = salle1.getNumeroSalle();
        int capacite = salle1.getCapacite();
        Salle s;
        
        s = serviceSalleLocal.modifierCapaciteSalle(numeroSalle, capacite);
        
        return Response.ok(this.gson.toJson(s)).build();
    }
    
    @DELETE
    public Response supprimerSalle(String numeroSalle) throws SalleInconnueException {

            serviceSalleLocal.supprimerSalle(numeroSalle);

        return Response.ok().build();
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
