/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Salle;
import java.util.List;
import javax.ejb.Local;
import ressources.RSalle;

/**
 *
 * @author jerom
 */
@Local
public interface ServiceSalleLocal {
    public List<Salle> listerSalles();
    public void ajouterSalle(RSalle salle);
    //public void ajouterSalle(String salle);
    //public String ajouterSalle(RSalle salle);
}
