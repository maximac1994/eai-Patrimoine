/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import MessagesTypes.DemandeRessources;
import business.GestionPatrimoineLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author jerom
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "TOPIC_DEMANDE_LISTE_RESSOURCES")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "TOPIC_DEMANDE_LISTE_RESSOURCES")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "TOPIC_DEMANDE_LISTE_RESSOURCES")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class ListenerTopicDemandeListeRessources implements MessageListener {
    
    @EJB
    GestionPatrimoineLocal gestionPatrimoineLocal;
    
    public ListenerTopicDemandeListeRessources() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        // Réception du message
        ObjectMessage om = (ObjectMessage)message;
        DemandeRessources dr;
        
        try {
            // Envoi de la liste des salles compatibles
            dr = (DemandeRessources)om.getObject();
            gestionPatrimoineLocal.envoyerListeSallesComp(dr);
        } catch (JMSException ex) {
            Logger.getLogger(ListenerTopicDemandeListeRessources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
