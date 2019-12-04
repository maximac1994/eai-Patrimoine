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
import entities.PlanningPK;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
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

    public GestionPatrimoine() {
        this.sender = new SenderFileListeRessources();
    }

    
    @Override
    public void creerSalle(String numeroSalle, int capacite, List<Integer> equipements) throws SalleExistanteException {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - creerSalle() : " + numeroSalle + ", " + capacite + ", " + equipements.toString());
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
    public void creerPlanning(EvenementFormationProjet2 evt) {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - creerPlanning() : " + evt.toString());

        String numeroSalle = evt.getIdSalle();
        Date dateDebut = evt.getDateDebut();
        Date dateFin = null;
        Date dateD = evt.getDateDebut();
        int cpt = evt.getDuree();
        
        for (int i = 0; i < cpt; i++) {
//            System.out.println("AVT : " + dateD);
            Planning planning = new Planning();
//            planning.setEtat("PRESSENTIE");
            PlanningPK planningPK = new PlanningPK();
            planningPK.setNumeroSalle(numeroSalle);
            planningPK.setDateJ(dateD);
            planning.setPlanningPK(planningPK);
            planningFacadeLocal.create(planning);
            
            dateFin = dateD;
            
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
        }
        changerEtat(evt, "PRESSENTIE");
        System.out.println("Salle correctement réservée du " + dateDebut + " au " + dateFin + ". Etat PRESSENTIE !");
    }

    @Override
    public List<Salle> listerSalles() {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - listerSalles()");
        return salleFacadeLocal.findAll();
    }

    @Override
    public List<Equipement> listerEquipements() {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - listerEquipements()");
        return equipementFacadeLocal.findAll();
    }

    @Override
    public void supprimerSalle(String numeroSalle) throws SalleInconnueException {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - supprimerSalle() : " + numeroSalle);
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
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - getSalle() : " + numeroSalle);
        Salle salle = salleFacadeLocal.find(numeroSalle);
        return salle;
    }

    @Override
    public void envoyerListeSallesComp(DemandeRessources dr) {
        try {
            Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - envoyerListeSallesComp() : " + dr.toString());
            
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
//                System.out.print(salle.getNumeroSalle() + " - " + getDatesOccupees(salle.getNumeroSalle()));
                }
            }
            //System.out.print(listeSallesComp.getListeSallesComp().get(0).getNumeroSalle());
            sender.sendListeSallesComp(listeSallesComp);
        } catch (JMSException ex) {
            Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Date> getDatesOccupees(String numeroSalle) {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - getDatesOccupees() : " + numeroSalle);

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
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - changerEtat() : " + evt.toString() + ", " + etat);
        System.out.println(evt.getDateDebut());
        System.out.println(evt.getDuree());
        Date dateD = evt.getDateDebut();
        int days = evt.getDuree();
        //int i;
        while(days >0){
            DateFormat df = new SimpleDateFormat("EEEE");
            String day = df.format(dateD);
            if( (!"samedi".equals(day)) && (!"dimanche".equals(day))){
                List<Planning> pls = planningFacadeLocal.findByNumeroSalleDateJ(evt.getIdSalle(), dateD);
                if(pls.isEmpty()){
                    Planning nP = new Planning();
                    nP.setEtat(etat);
                    PlanningPK pPK = new PlanningPK();
                    pPK.setNumeroSalle(evt.getIdSalle());
                    pPK.setDateJ(dateD);
                    nP.setPlanningPK(pPK);
                    planningFacadeLocal.create(nP);
                }else{
                    pls.get(0).setEtat(etat);
                }
                
                
                
                days--;
                
            }
            dateD.setTime(dateD.getTime()+(25*3600*1000));
        }
        
        System.out.println("Salle correctement passée en état " + etat + " !");
    }

    @Override
    public void supprimerPlanning(EvenementFormationAnnulation evt) {
        Logger.getLogger(GestionPatrimoine.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE] Package.business GestionPatrimoine - supprimerPlanning() : " + evt.toString());
        String numeroSalle = evt.getIdSalle();
        Date dateD = evt.getDateDebut();
        int cpt = evt.getDuree();
        
        for (int i = 0; i < cpt; i++) {
            List<Planning> pls = planningFacadeLocal.findByNumeroSalleDateJ(numeroSalle, dateD);
            
            if(!pls.isEmpty()){
                    planningFacadeLocal.remove(pls.get(0));
            }
            
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
