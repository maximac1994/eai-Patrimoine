/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Equipement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jerom
 */
@Local
public interface ServiceEquipementLocal {
    public List<Equipement> listerEquipements();
}
