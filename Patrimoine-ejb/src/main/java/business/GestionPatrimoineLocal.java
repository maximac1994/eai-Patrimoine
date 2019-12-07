/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import MessagesTypes.DemandeRessources;
import MessagesTypes.EvenementFormationAnnulation;
import MessagesTypes.EvenementFormationChangeEtat;
import MessagesTypes.EvenementFormationProjet2;
import entities.Equipement;
import entities.Salle;
import exceptions.SalleExistanteException;
import exceptions.SalleInconnueException;
import exceptions.SalleOccupeeException;
import java.util.Date;
import java.util.List;
import ressources.REquipement;

/**
 *
 * @author jerom
 */
public interface GestionPatrimoineLocal {
    
    void creerSalle(String numeroSalle, int capacite, List<Integer> equipements) throws SalleExistanteException;
    
    void supprimerSalle(String numeroSalle) throws SalleInconnueException, SalleOccupeeException;
    
    void creerPlanning(EvenementFormationProjet2 evt);
    
    void envoyerListeSallesComp(DemandeRessources dr);

    // void ajouterEquipement(long idEquipement);
    
    List<Salle> listerSalles();
    
    List<Equipement> listerEquipements();
    
    Salle getSalle(String numeroSalle);
    
    void changerEtat(EvenementFormationChangeEtat evt, String etat);
    
    void supprimerPlanning(EvenementFormationAnnulation evt);
}
