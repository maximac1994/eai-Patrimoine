/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.SalleEquipement;
import entities.SalleEquipementPK;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jerom
 */
@Local
public interface SalleEquipementFacadeLocal {

    void create(SalleEquipement salleEquipement);

    void edit(SalleEquipement salleEquipement);

    void remove(SalleEquipement salleEquipement);

    SalleEquipement find(Object id);
    
    List<SalleEquipementPK> findByNum(String numeroSalle);

    List<SalleEquipement> findAll();

    List<SalleEquipement> findRange(int[] range);
    
    List<SalleEquipement> getEquipBySalle(String numeroSalle);

    int count();
    
}
