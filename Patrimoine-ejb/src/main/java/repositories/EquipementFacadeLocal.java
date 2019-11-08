/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Equipement;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jerom
 */
@Local
public interface EquipementFacadeLocal {

    void create(Equipement equipement);

    void edit(Equipement equipement);

    void remove(Equipement equipement);

    Equipement find(Object id);

    List<Equipement> findAll();

    List<Equipement> findRange(int[] range);

    int count();
    
}
