/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import MessagesTypes.DemandeRessources;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author jerom
 */
public class ReceiverDemandeListeRessources {
    
    public static void main(String[] args) {
        
        System.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "jms/__defaultConnectionFactory";
        String destName = "TOPIC_DEMANDE_LISTE_RESSOURCES";
        Destination dest = null;
        Session session = null;
        MessageConsumer receiver = null;
        
        try {
            // create the JNDI initial context
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup("jms/__defaultConnectionFactory");

            // look up the Destination
            dest = (Destination) context.lookup("Topic");

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the receiver
            receiver = session.createConsumer(dest);

            // start the connection, to enable message receipt
            connection.start();

            while(true){
                Object message = receiver.receive();
                ObjectMessage demandeRessources = (ObjectMessage) message;
                DemandeRessources dr = new DemandeRessources();
                dr = (DemandeRessources) demandeRessources.getObject();
                
                System.out.println("Received object DemandeRessources : " + dr.getIdFormation());
                /* Chercher les salles ayant un nbMax <= au nbMax de DemandeRessources */
                /* Ainsi que les salles ayant les équipements nécessaires */
            }
            
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    } 
}