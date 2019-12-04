/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.Planning;
import java.util.Date;
import java.util.List;
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

    @Override
    public List<Planning> getDatesOccupees(String numeroSalle) {
        List<Planning> listePlanning = em.createNamedQuery("Planning.findByNumeroSalle")
            .setParameter("numeroSalle", numeroSalle)
            .getResultList();
        
        return listePlanning;
    }

    @Override
    public List<Planning> findByNumeroSalleDateJ(String numeroSalle, Date dateJ) {
        System.out.println("TEEEST : "+numeroSalle+" "+dateJ);
        List<Planning> planning = em.createNamedQuery("Planning.findByNumeroSalleDateJ")
            .setParameter("numeroSalle", numeroSalle)
            .setParameter("dateJ", dateJ)
            .getResultList();
        
        return planning;
    }
    
}
