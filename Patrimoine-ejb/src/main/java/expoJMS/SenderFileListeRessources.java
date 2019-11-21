/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import MessagesTypes.ListeSallesCompatibles;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author jerom
 */
public class SenderFileListeRessources {
    Context context = null;
    ConnectionFactory factory = null;
    Connection connection = null;
    String factoryName = null;
    String destName = null;
    Destination dest = null;
    Session session = null;
    MessageProducer sender = null;

    public SenderFileListeRessources() {
        
        // Initialisation de la connexion
        System.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        System.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        factoryName = "jms/__defaultConnectionFactory";
        destName = "FILE_LISTE_RESSOURCES";
        
        try{
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the sender
            sender = session.createProducer(dest);

            // start the connection, to enable message sends
            connection.start();
        }catch (NamingException exception) {
            exception.printStackTrace();
        }catch (JMSException exception) {
            exception.printStackTrace();
        }
    }
    
    public void sendListeSallesComp (ListeSallesCompatibles liste) {
        try {
            // Envoi du message contenant la liste des salles compatibles
            ObjectMessage om = session.createObjectMessage(liste);
            sender.send(om);
        } catch (JMSException exception) {
            exception.printStackTrace();
        }
       finally {
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
