package org.jmstest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MainJMSSend implements MessageListener {

	public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	public final static String JMS_FACTORY = "CF1";
	public final static String QUEUE = "Queue1";

	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueReceiver qreceiver;
	private Queue queue;
	private boolean quit = false;

	public void onMessage(Message msg) {
		try {
			String msgText;
			if (msg instanceof TextMessage) {
				msgText = ((TextMessage) msg).getText();
			} else {
				msgText = msg.toString();
			}
			System.out.println("Message Received: " + msgText);
			if (msgText.equalsIgnoreCase("quit")) {
				synchronized (this) {
					quit = true;
					this.notifyAll(); // Notify main thread to quit
				}
			}
		} catch (JMSException jmse) {
			System.err.println("An exception occurred: " + jmse.getMessage());
		}
	}

	public void init(Context ctx, String queueName) throws NamingException,
			JMSException {
		qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
		qcon = qconFactory.createQueueConnection();
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = (Queue) ctx.lookup(queueName);
		qreceiver = qsession.createReceiver(queue);
		
		qreceiver.setMessageListener(this);
		qcon.start();
	}

	public void close() throws JMSException {
		qreceiver.close();
		qsession.close();
		qcon.close();
	}

	public static void main(String[] args) throws Exception {
		/*if (args.length != 1) {
			System.out
					.println("Usage: java examples.jms.queue.QueueReceive WebLogicURL");
			return;
		}*/
		InitialContext ic = getInitialContext(args[0]);
		MainJMSSend qr = new MainJMSSend();
		qr.init(ic, QUEUE);
		System.out
				.println("JMS Ready To Receive Messages (To quit, send a \"quit\" message).");
		synchronized (qr) {
			while (!qr.quit) {
				try {
					qr.wait();
				} catch (InterruptedException ie) {
				}
			}
		}
		qr.close();
	}

	private static InitialContext getInitialContext(String url)
			throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put(Context.PROVIDER_URL, url);
		return new InitialContext(env);
	}

}
