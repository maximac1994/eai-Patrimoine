/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Equipement;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jerom
 */
@Stateless
public class EquipementFacade extends AbstractFacade<Equipement> implements EquipementFacadeLocal {

    @PersistenceContext(unitName = "ProjetJEE_Patrimoine-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EquipementFacade() {
        super(Equipement.class);
    }
    
}
