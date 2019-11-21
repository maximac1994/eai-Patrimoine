/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
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
    
    public ListenerTopicFormation() {
    }
    
    @Override
    public void onMessage(Message message) {
        ObjectMessage om = (ObjectMessage)message;
        String jmsType = "";
        try {
            jmsType = message.getJMSType();
        } catch (JMSException ex) {
            Logger.getLogger(ListenerTopicFormation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if("projet2".equals(jmsType)) {
            
        }
        
        if("validation".equals(jmsType)) {
            
        }
        
        if("annulation".equals(jmsType)) {
            
        }
    }
    
}
