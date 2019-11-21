/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import entities.SalleEquipement;
import entities.SalleEquipementPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author jerom
 */
@Stateless
public class SalleEquipementFacade extends AbstractFacade<SalleEquipement> implements SalleEquipementFacadeLocal {

    @PersistenceContext(unitName = "ProjetJEE_Patrimoine-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalleEquipementFacade() {
        super(SalleEquipement.class);
    }

    @Override
    public List<SalleEquipementPK> findByNum(String numeroSalle) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<SalleEquipementPK> cq = cb.createQuery(SalleEquipementPK.class);
        Root<SalleEquipementPK> root = cq.from(SalleEquipementPK.class);
        cq.where(
                cb.and(
                        cb.equal(cb.upper(root.get("numeroSalle").as(String.class)), numeroSalle)
                )
        );
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<SalleEquipement> getEquipBySalle(String numeroSalle) {
        List<SalleEquipement> listeSE = em.createNamedQuery("SalleEquipement.findByNumeroSalle")
                .setParameter("numeroSalle", numeroSalle)
                .getResultList();
        
        return listeSE;
    }
    
}
