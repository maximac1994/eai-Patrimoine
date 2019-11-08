/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Planning;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jerom
 */
@Stateless
public class PlanningFacade extends AbstractFacade<Planning> implements PlanningFacadeLocal {

    @PersistenceContext(unitName = "ProjetJEE_Patrimoine-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningFacade() {
        super(Planning.class);
    }
    
}
