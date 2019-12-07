
import com.google.gson.Gson;
import entities.Salle;
import exceptions.SalleExistanteException;
import exceptions.SalleInconnueException;
import exceptions.SalleOccupeeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
    
    /**
     * Renvoie la liste des salles
     * @return la liste des salles
     */
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response listSalles() {
        return Response.ok(gson.toJson(serviceSalleLocal.listerSalles())).build();
    }
    
    /**
     * Ajoute une salle saisie par l'utilisateur
     * @param content
     * @return la réponse positive ou le message d'erreur
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSalle(String content) {
        try {
            
            RSalle salle = gson.fromJson(content, RSalle.class);
            Salle s = serviceSalleLocal.getSalle(salle.getNumeroSalle());
            if(s!=null){
                throw new SalleExistanteException();
            }
            serviceSalleLocal.ajouterSalle(salle);  
        } catch (SalleExistanteException ex) {
            return Response.status(Response.Status.CONFLICT).entity("Salle déjà existante !").build();
        }
        return Response.ok("Salle correctement ajoutée !").build();
    }
    
    /**
     * Supprime la salle choisie par l'utilisateur
     * @param numeroSalle
     * @return la réponse positive ou les messages d'erreurs
     * @throws SalleInconnueException
     * @throws SalleOccupeeException 
     */
    @DELETE
    public Response removeSalle(@QueryParam("numeroSalle") String numeroSalle) throws SalleInconnueException, SalleOccupeeException {

        try {
            serviceSalleLocal.supprimerSalle(numeroSalle);
        } catch (SalleInconnueException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Salle inexistante !").build();
        } catch (SalleOccupeeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Salle occupée !").build();
        }
        return Response.ok("Salle correctement supprimée !").build();
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
