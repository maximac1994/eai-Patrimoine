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

/**
 *
 * @author jerom
 */
@Stateless
public class ServiceSalle implements ServiceSalleLocal {

    @EJB
    GestionPatrimoineLocal gestionpatrimoineLocal;
    
    @Override
    public List<Salle> listerSalles() {
       return gestionpatrimoineLocal.listerSalles();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
