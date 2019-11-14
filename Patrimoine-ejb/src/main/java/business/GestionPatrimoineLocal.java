/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Equipement;
import entities.Salle;
import exceptions.SalleExistanteException;
import exceptions.SalleInconnueException;
import java.util.Date;
import java.util.List;
import ressources.REquipement;

/**
 *
 * @author jerom
 */
public interface GestionPatrimoineLocal {
    
    void creerSalle(String numeroSalle, int capacite, List<REquipement> equipements) throws SalleExistanteException;
    
    void supprimerSalle(String numeroSalle) throws SalleInconnueException;
    
    void creerPlanning(long idSalle, Date date, String etat);

    // void ajouterEquipement(long idEquipement);
    
    List<Salle> listerSalles();
    
    List<Equipement> listerEquipements();
    
    Salle getSalle(String numeroSalle);
    
}
