/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Salle;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import repositories.SalleFacadeLocal;

/**
 *
 * @author jerom
 */
@Stateless
public class GestionPatrimoine implements GestionPatrimoineLocal {

    @EJB
    SalleFacadeLocal salleFacadeLocal;
    
    @Override
    public void creerSalle(long capacite) {
        //Salle salle = new Salle(capacite);
        //this.salleFacade.create(client);
    }

    @Override
    public void creerPlanning(long idSalle, Date date, String etat) {
        //Planning planning = new Planning(idSalle, date, etat);
        //this.planningFacade.create(planning);
    }

    @Override
    public void ajouterEquipement(long idEquipement) {
        
    }

    @Override
    public List<Salle> listerSalles() {
        return salleFacadeLocal.findAll();
    }

    
    
}
