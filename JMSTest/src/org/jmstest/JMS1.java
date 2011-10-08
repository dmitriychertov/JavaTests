package org.jmstest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
public class JMS1 {
	public static void main(String[] args) {
		new JMSImpl();
	}
}
class JMSImpl implements MessageListener {
	private Context initialContext;
	private TopicConnectionFactory topicConnectionFactory;
	private TopicConnection topicConnection = null;
	public JMSImpl() {
		System.setProperty(Context.PROVIDER_URL,
				"t3://localhost:7001");
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		process();
	}
	private void process() {
		try {
			initialContext = new InitialContext();
			// get the topic factory
			topicConnectionFactory = (TopicConnectionFactory) initialContext
					.lookup("jms/testConnectionFactory");
			// create a topic connection and session
			topicConnection = topicConnectionFactory.createTopicConnection();
			TopicSession session = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			TopicSession pub = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			// finds the topic and build a publisher:
			Topic topic = (Topic) initialContext
					.lookup("jms/sampetopic");
			TopicPublisher publisher = session.createPublisher(topic);
 
			TextMessage message = session.createTextMessage();
			message.setText("Sample message");
			publisher.publish(message);
			TopicSubscriber topicSubscriber = pub.createSubscriber(topic);
			topicConnection.start();
			while (true) {
				Message receiveMsg = topicSubscriber.receive();
				if (receiveMsg != null) {
					if (receiveMsg instanceof TextMessage) {
						TextMessage textMessage = (TextMessage) receiveMsg;
						System.out.println(textMessage.getText());
						break;
					}
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (topicConnection != null)
				try {
					topicConnection.close();
					System.out.println("Connection closed.");
				} catch (JMSException e) {
					e.printStackTrace();
				}
		}
	}
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (Exception jmse) {
			jmse.printStackTrace();
		}
	}
}
