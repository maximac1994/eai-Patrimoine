/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Equipement;
import entities.Salle;
import entities.SalleEquipement;
import entities.SalleEquipementPK;
import exceptions.SalleExistanteException;
import exceptions.SalleInconnueException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import repositories.EquipementFacadeLocal;
import repositories.SalleEquipementFacadeLocal;
import repositories.SalleFacadeLocal;
import ressources.REquipement;

/**
 *
 * @author jerom
 */
@Stateless
public class GestionPatrimoine implements GestionPatrimoineLocal {

    @EJB
    SalleFacadeLocal salleFacadeLocal;
    
    @EJB
    SalleEquipementFacadeLocal salleEquipementFacadeLocal;
    
    @EJB
    EquipementFacadeLocal equipementFacadeLocal;
    
    @Override
    public void creerSalle(String numeroSalle, int capacite, List<Integer> equipements) throws SalleExistanteException {
        Salle salle = new Salle();
        salle.setNumeroSalle(numeroSalle);
        salle.setCapacite(capacite);
        salleFacadeLocal.create(salle);
        
        //for (REquipement equipement : equipements) {
        for (Integer equipement : equipements) {
            //SalleEquipement salleEquipement = new SalleEquipement(numeroSalle, equipement.getId());
            SalleEquipementPK salleEquipementPK = new SalleEquipementPK();
            salleEquipementPK.setIdEquipement(equipement);
            salleEquipementPK.setNumeroSalle(salle.getNumeroSalle());
            
            SalleEquipement salleEquipement = new SalleEquipement();
            salleEquipement.setSalleEquipementPK(salleEquipementPK);
            
            salleEquipementFacadeLocal.create(salleEquipement);
        }
        
    }

    @Override
    public void creerPlanning(long idSalle, Date date, String etat) {
        //Planning planning = new Planning(idSalle, date, etat);
        //this.planningFacade.create(planning);
    }

    @Override
    public List<Salle> listerSalles() {
        return salleFacadeLocal.findAll();
    }

    @Override
    public List<Equipement> listerEquipements() {
        return equipementFacadeLocal.findAll();
    }

    @Override
    public void supprimerSalle(String numeroSalle) throws SalleInconnueException {
        Salle salle = salleFacadeLocal.find(numeroSalle);
        if (salle == null) {
            throw new SalleInconnueException();
        }
        //List<SalleEquipementPK> listSallesEPK = salleEquipementFacadeLocal.findByNum(numeroSalle);
        SalleEquipement salleE = salleEquipementFacadeLocal.find(numeroSalle);
        //System.out.println(salleE);
        //System.out.println(listSallesEPK);
        // SalleEquipementPK salleEPK = salleE.getSalleEquipementPK();
        
        salleFacadeLocal.remove(salle);
        
        //for (SalleEquipementPK sePK : listSallesEPK) {
            salleEquipementFacadeLocal.remove(salleE);
        //}
    }


    @Override
    public Salle getSalle(String numeroSalle) {
        Salle salle = salleFacadeLocal.find(numeroSalle);
        return salle;
    }
}
