/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import business.GestionPatrimoine;
import business.GestionPatrimoineLocal;
import entities.Salle;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import ressources.RSalle;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import exceptions.SalleExistanteException;
import exceptions.SalleInconnueException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jerom
 */
@Stateless
public class ServiceSalle implements ServiceSalleLocal {

    @EJB
    GestionPatrimoineLocal gestionPatrimoineLocal;
    
    @Override
    public List<Salle> listerSalles() {
        Logger.getLogger(ServiceSalle.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.services ServiceSalle - listerSalles()");

       return gestionPatrimoineLocal.listerSalles();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void ajouterSalle(RSalle salle) throws SalleExistanteException {
        Logger.getLogger(ServiceSalle.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.services ServiceSalle - ajouterSalle() : " + salle);

//        try {
//            RSalle salle = this.gson.fromJson(s, RSalle.class);
//        } catch (JsonSyntaxException e) {
//            
//        }
        gestionPatrimoineLocal.creerSalle(salle.getNumeroSalle(), salle.getCapacite(), salle.getEquipements());
        //return "Ok";
    }

    @Override
    public void supprimerSalle(String numeroSalle) throws SalleInconnueException {
        Logger.getLogger(ServiceSalle.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.services ServiceSalle - supprimerSalle() : " + numeroSalle);
  
        gestionPatrimoineLocal.supprimerSalle(numeroSalle);
    }
    
    @Override
    public Salle getSalle(String numeroSalle) {
        Logger.getLogger(ServiceSalle.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.services ServiceSalle - getSalle() : " + numeroSalle);

        return gestionPatrimoineLocal.getSalle(numeroSalle);
    }
}
