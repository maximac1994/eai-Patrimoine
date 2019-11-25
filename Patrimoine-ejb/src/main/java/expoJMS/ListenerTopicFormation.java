/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import MessagesTypes.EvenementFormationAnnulation;
import MessagesTypes.EvenementFormationProjet2;
import MessagesTypes.EvenementFormationValidation;
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
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "TOPIC_FORMATION")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "TOPIC_FORMATION")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "TOPIC_FORMATION")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class ListenerTopicFormation implements MessageListener {
    
    @EJB
    GestionPatrimoineLocal gestionPatrimoineLocal;
    
    public ListenerTopicFormation() {
    }
    
    @Override
    public void onMessage(Message message) {
        
//        System.out.println("LAAAAA");
        ObjectMessage om = (ObjectMessage)message;
        EvenementFormationAnnulation evt;
        EvenementFormationAnnulation evt2;
        EvenementFormationAnnulation evt3;
        String jmsType = "";
        
        try {
            jmsType = message.getJMSType();
        } catch (JMSException ex) {
            Logger.getLogger(ListenerTopicFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if("projet2".equals(jmsType)) {
            
            try {
                evt = (EvenementFormationAnnulation) om.getObject();
                gestionPatrimoineLocal.changerEtat(evt, "PRESSENTIE");
            } catch (JMSException ex) {
                Logger.getLogger(ListenerTopicFormation.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if("validation".equals(jmsType)) {
            try {
                evt3 = (EvenementFormationAnnulation) om.getObject();
                gestionPatrimoineLocal.changerEtat(evt3, "AFFECTEE");
            } catch (JMSException ex) {
                Logger.getLogger(ListenerTopicFormation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if("annulation".equals(jmsType)) {
            try {
                evt2 = (EvenementFormationAnnulation) om.getObject();
                gestionPatrimoineLocal.supprimerPlanning(evt2);
            } catch (JMSException ex) {
                Logger.getLogger(ListenerTopicFormation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
