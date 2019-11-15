/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
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
public class ListenerDemandeListeRessources implements MessageListener {
    
    public ListenerDemandeListeRessources() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        System.out.print("blabla");
        
    }
    
}
