/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ressources;

import entities.Equipement;
import java.util.List;

/**
 *
 * @author jerom
 */
public class RSalle {
    
    String numeroSalle;
    int capacite;
    List<REquipement> equipements;

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public List<REquipement> getEquipements() {
        return equipements;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setEquipements(List<REquipement> equipements) {
        this.equipements = equipements;
    }
    
    
    
}
