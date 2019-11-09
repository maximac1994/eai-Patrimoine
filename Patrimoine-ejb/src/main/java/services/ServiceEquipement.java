/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import business.GestionPatrimoineLocal;
import entities.Equipement;
import java.util.List;
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
        return gestionPatrimoineLocal.listerEquipements();
    }
    
}
