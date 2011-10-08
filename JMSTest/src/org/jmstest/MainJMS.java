package org.jmstest;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MainJMS {

	private static InitialContext ctx = null;
	private static QueueConnectionFactory queueConnectionFactory = null;
	private static QueueConnection queueConnection = null;
	private static QueueSession queueSession = null;
	private static Queue queue = null;
	private static QueueSender queueSender = null;
	private static TextMessage message = null;

	private static final String QCF_NAME = "jms/testConnectionFactory";
	private static final String QUEUE_NAME = "jms/testQueue1";

	public MainJMS() {
		super();
	}

	private static void sendMessage(String messageText) {
		// create InitialContext

		Hashtable properties = new Hashtable();

		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "t3://localhost:7001");
		properties.put(Context.SECURITY_PRINCIPAL, "weblogic");
		properties.put(Context.SECURITY_CREDENTIALS, "123qweasdzxc");

		try {
			ctx = new InitialContext(properties);
		} catch (NamingException e) {
			e.printStackTrace(System.err);
			System.exit(0);
		}

		System.out.println("Got InitialContext " + ctx.toString());

		// create QueueConnectionFactory

		try {
			queueConnectionFactory = (QueueConnectionFactory) ctx
					.lookup(QCF_NAME);
		} catch (NamingException e) {
			e.printStackTrace(System.err);
			System.exit(0);
		}

		System.out.println("Got QueueConnectionFactory "
				+ queueConnectionFactory.toString());

		// create QueueConnetion

		try {
			queueConnection = queueConnectionFactory.createQueueConnection();
		} catch (JMSException jmse) {
			jmse.printStackTrace(System.err);
			System.exit(0);
		}

		System.out.println("Got QueueConnection " + queueConnection.toString());

		// create QueueSession
		
		try {
			queueSession = queueConnection.createQueueSession(false, 0);
		} catch (JMSException e) {
			e.printStackTrace(System.err);
			System.exit(0);
		}
		
		System.out.println("Got QueueSession " + queueSession.toString());
		
		// lookup Queue
	       try {
	           queue = (Queue) ctx.lookup(QUEUE_NAME);
	       }
	       catch (NamingException ne) {
	           ne.printStackTrace(System.err);
	           System.exit(0);
	       }
	       System.out.println("Got Queue " + queue.toString());
	       // create QueueSender
	       try {
	           queueSender = queueSession.createSender(queue);
	       }
	       catch (JMSException jmse) {
	           jmse.printStackTrace(System.err);
	           System.exit(0);
	       }
	       System.out.println("Got QueueSender " + queueSender.toString());
	       // create TextMessage
	       try {
	    	   message = queueSession.createTextMessage();
	       }
	       catch (JMSException jmse) {
	           jmse.printStackTrace(System.err);
	           System.exit(0);
	       }
	       System.out.println("Got TextMessage " + message.toString());
	       // set message text in TextMessage
	       try {
	           message.setText(messageText);
	       }
	       catch (JMSException jmse) {
	           jmse.printStackTrace(System.err);
	           System.exit(0);
	       }
	       System.out.println("Set text in TextMessage " + message.toString());
	       // send message
	       try {
	           queueSender.send(message);
	       }
	       catch (JMSException jmse) {
	           jmse.printStackTrace(System.err);
	           System.exit(0);
	       }
	       System.out.println("Sent message ");
	       // clean up
	       try {
	           message = null;
	           queueSender.close();
	           queueSender= null;
	           queue = null;
	           queueSession.close();
	           queueSession = null;
	           queueConnection.close();
	           queueConnection = null;
	           queueConnectionFactory = null;
	           ctx = null;
	       }
	       catch (JMSException jmse) {
	           jmse.printStackTrace(System.err);
	       }
	       System.out.println("Cleaned up and done.");
		
		
		

	}

	public static void main(String[] args) {
		sendMessage("test");
	}

}
