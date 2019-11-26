/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import MessagesTypes.DemandeRessources;
import MessagesTypes.EvenementFormationAnnulation;
import MessagesTypes.EvenementFormationChangeEtat;
import MessagesTypes.EvenementFormationProjet2;
import MessagesTypes.ListeSallesCompatibles;
import MessagesTypes.SalleComp;
import entities.Equipement;
import entities.Planning;
import entities.Salle;
import entities.SalleEquipement;
import entities.SalleEquipementPK;
import exceptions.SalleExistanteException;
import exceptions.SalleInconnueException;
import expoJMS.SenderFileListeRessources;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import repositories.EquipementFacadeLocal;
import repositories.PlanningFacadeLocal;
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
    
    @EJB
    PlanningFacadeLocal planningFacadeLocal;
    
    SenderFileListeRessources sender;

    
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

    @Override
    public void envoyerListeSallesComp(DemandeRessources dr) {
        List<Integer> listeEquipementsNec = dr.getEquipementsNecessaires(); // Liste des équipements demandés
        List<Salle> listeAllSalles = salleFacadeLocal.findAll(); // Liste de toutes les salles
        
        ListeSallesCompatibles listeSallesComp = new ListeSallesCompatibles(); // Liste des salles compatibles à renvoyer
        listeSallesComp.setIdInstance(dr.getIdInstance()); // La liste concerne l'instance IdInstance
        
        // Parcours de toutes les salles de la BD
        for(Salle salle : listeAllSalles) {
            
            // On récupère les équipements de la salle
            List<SalleEquipement> listeSE = salleEquipementFacadeLocal.getEquipBySalle(salle.getNumeroSalle());
            boolean salleOk = true;
            boolean capaciteOk = false;
            int nbEquip = 0; // Compteur du nombre d'équipement
            
            if(dr.getNbMax() <= salle.getCapacite()) {
                capaciteOk = true;
            }
            
            // Parcours des équipements demandés
            for(Integer equipement : listeEquipementsNec) {
                boolean equipOK = false;
                
                
                // Pour chaque équipement de la salle actuelle,
                // s'il correspond à un équipement demandé, on incrémente le compteur de 1
                // (Nécessaire pour prendre TOUS les équipements en compte s'il y en a plusieurs)
                for(SalleEquipement se : listeSE) {
                    if(equipement == se.getSalleEquipementPK().getIdEquipement()) {
                        nbEquip++;
                    }
                }
                //System.out.print("nbequip : " + nbEquip);
                //System.out.print("taille liste : " + listeEquipementsNec.size());
                // Si le nombre d'équipement correspond
                if (nbEquip == listeEquipementsNec.size()) {
                    equipOK = true;
                }
                salleOk = equipOK;
            }
            
            salleOk = salleOk && capaciteOk;
            
            // Si la salle correspond aux équipements demandés
            if(salleOk) {
                SalleComp salleComp = new SalleComp();
                salleComp.setNumeroSalle(salle.getNumeroSalle());
                salleComp.setDatesOccupees(getDatesOccupees(salle.getNumeroSalle())); // Récupération du planning de la salle (dates occupées)
                listeSallesComp.getListeSallesComp().add(salleComp); // Ajout de la salle à la liste à renvoyer
                System.out.print(salle.getNumeroSalle() + " - " + getDatesOccupees(salle.getNumeroSalle()));
            }
        }
        //System.out.print(listeSallesComp.getListeSallesComp().get(0).getNumeroSalle());
        //sender.sendListeSallesComp(listeSallesComp);
    }
    
    private List<Date> getDatesOccupees(String numeroSalle) {
        
        // Liste des dates occupées pour une salle
        List<Planning> listePlanning = planningFacadeLocal.getDatesOccupees(numeroSalle);
        
        List<Date> listeDates = new ArrayList<Date>();
        
        // Parcours des dates occupées de la salle
        for(Planning planning : listePlanning){
            listeDates.add(planning.getPlanningPK().getDateJ()); // Ajout des dates à la liste à renvoyer
        }
        return listeDates;
    }

    @Override
    public void changerEtat(EvenementFormationChangeEtat evt, String etat) {
        
        String numeroSalle = evt.getIdSalle();
        Date dateD = evt.getDateDebut();
        int cpt = evt.getDuree();
        //int i;
        
        for (int i = 0; i < cpt; i++) {
//            System.out.println("AVT : " + dateD);
            Planning p = planningFacadeLocal.findByNumeroSalleDateJ(numeroSalle, dateD);
            p.setEtat(etat);
            Calendar c = Calendar.getInstance();
            c.setTime(dateD);
            DateFormat df = new SimpleDateFormat("EEEE");
            String day = df.format(dateD);
            if("vendredi".equals(day)){
                c.add(Calendar.DATE, 3);  // number of days to add
            } else {
                c.add(Calendar.DATE, 1);
            }
            
            dateD = c.getTime();
            //System.out.println("APRES : " + date);
        }
        System.out.println("Salle correctement passée en état " + etat + " !");
    }

    @Override
    public void supprimerPlanning(EvenementFormationAnnulation evt) {
        String numeroSalle = evt.getIdSalle();
        Date dateD = evt.getDateDebut();
        int cpt = evt.getDuree();
        
        for (int i = 0; i < cpt; i++) {
            Planning p = planningFacadeLocal.findByNumeroSalleDateJ(numeroSalle, dateD);
            planningFacadeLocal.remove(p);
            Calendar c = Calendar.getInstance();
            c.setTime(dateD);
            DateFormat df = new SimpleDateFormat("EEEE");
            String day = df.format(dateD);
            if("vendredi".equals(day)){
                c.add(Calendar.DATE, 3);  // number of days to add
            } else {
                c.add(Calendar.DATE, 1);
            }
            
            dateD = c.getTime();
            //System.out.println("APRES : " + date);
            
            
        }
        System.out.println("Salle libérée !");
    }
}
