/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import MessagesTypes.ListeSallesCompatibles;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    String  factoryName = "jms/__defaultConnectionFactory";
    String destName  = "FILE_LISTE_RESSOURCES";
    Destination dest = null;
    Session session = null;
    MessageProducer sender = null;

    /**
     * Création du contexte JNDI
     */
    public void createContext() {
        try {
            // create the JNDI initial context.
            context = new InitialContext();
            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            dest = (Destination) context.lookup(destName);
        } catch (NamingException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Connexion à la ConnectionFactory
     * @throws JMSException 
     */
    public void connect() throws JMSException {
        // create the connection
        connection = factory.createConnection();
        // create the session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // create the sender
        sender = session.createProducer(dest);
        // start the connection, to enable message sends
        connection.start();
    }

    /**
     * Envoie dans la file FILE_LISTE_RESSOURCES les salles compatibles avec une formation
     * @param liste
     * @throws JMSException 
     */
    public void sendListeSallesComp(ListeSallesCompatibles liste) throws JMSException {
        Logger.getLogger(SenderFileListeRessources.class.getName()).log(Level.INFO, "[APPLI PATRIMOINE]  Package.expoJMS SenderFileListeRessources - onMessage() : " + liste.toString());
        if (context == null) {
            this.createContext();
        }
        this.connect();
        try {
            // Envoi du message contenant la liste des salles compatibles
            ObjectMessage om = session.createObjectMessage(liste);
            sender.send(om);
        } catch (JMSException exception) {
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
