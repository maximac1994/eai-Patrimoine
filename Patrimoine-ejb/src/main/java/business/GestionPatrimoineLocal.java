/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Equipement;
import entities.Salle;
import java.util.Date;
import java.util.List;
import ressources.REquipement;

/**
 *
 * @author jerom
 */
public interface GestionPatrimoineLocal {
    
    void creerSalle(int capacite, List<REquipement> equipements);
    
    void supprimerSalle(String numeroSalle);
    
    void modifierCapaciteSalle(String numeroSalle, int capacite);
    
    void creerPlanning(long idSalle, Date date, String etat);
    
    
    // void ajouterEquipement(long idEquipement);
    
    List<Salle> listerSalles();
    
    List<Equipement> listerEquipements();
    
}
