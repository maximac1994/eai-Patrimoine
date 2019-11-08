/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Salle;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jerom
 */
public interface GestionPatrimoineLocal {
    
    void creerSalle(long capacite);
    
    void creerPlanning(long idSalle, Date date, String etat);
    
    void ajouterEquipement(long idEquipement);
    
    List<Salle> listerSalles();
    
}
