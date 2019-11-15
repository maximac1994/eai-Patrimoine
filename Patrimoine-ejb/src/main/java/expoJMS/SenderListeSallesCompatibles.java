/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expoJMS;

import MessagesTypes.ListeSallesCompatibles;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author jerom
 */
public class SenderListeSallesCompatibles {
    
    public static void main(String[] args) {
        
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String destName = "FILE_LISTE_RESSOURCES";
        Destination dest = null;
        int count = 1;
        Session session = null;
        MessageProducer sender = null;
        String text = "Message ";
        
        try {
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup("jms/__defaultConnectionFactory");

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

            for (int i = 0; i < count; ++i) {
                
                HashMap<Integer, List<Date>> sallesCompatibles = new HashMap<Integer, List<Date>>();
                
                ListeSallesCompatibles listeSallesCompatibles = new ListeSallesCompatibles();
                listeSallesCompatibles.setIdInstance(1);
                listeSallesCompatibles.setSallesCompatibles(sallesCompatibles);
                
                ObjectMessage message = session.createObjectMessage();
                message.setObject(listeSallesCompatibles);
                
                sender.send(message);
                System.out.println("Sent: " + message.getObject());
                
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
