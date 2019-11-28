/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import business.GestionPatrimoineLocal;
import entities.Equipement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jerom
 */
@Stateless
public class ServiceEquipement implements ServiceEquipementLocal {

    @EJB
    GestionPatrimoineLocal gestionPatrimoineLocal;
    
    @Override
    public List<Equipement> listerEquipements() {
        Logger.getLogger(ServiceEquipement.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.services ServiceEquipement - listerEquipements()");

        return gestionPatrimoineLocal.listerEquipements();
    }
    
}
