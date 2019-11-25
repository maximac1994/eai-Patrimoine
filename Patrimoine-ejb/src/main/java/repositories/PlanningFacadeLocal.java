/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Planning;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jerom
 */
@Local
public interface PlanningFacadeLocal {

    void create(Planning planning);

    void edit(Planning planning);

    void remove(Planning planning);

    Planning find(Object id);

    List<Planning> findAll();

    List<Planning> findRange(int[] range);
    
    List<Planning> getDatesOccupees(String numeroSalle);
    
    Planning findByNumeroSalleDateJ(String numeroSalle, Date dateJ);

    int count();
    
}
