package com.hibernate.jpa.main;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.hibernate.jpa.domain.Message;

/**
 * Hello world!
 *
 */
public class HelloWorld {
	public static void main(String[] args) {
		// First unit of work

		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory factory = configuration.buildSessionFactory(builder
				.build());

		Session session = factory.openSession();

		Transaction transaction = session.beginTransaction();
		Message message = new Message("Hello World!");
		Long msgId = (Long) session.save(message);

		transaction.commit();
		session.close();

		// Second unit of work
		Session newSession = factory.openSession();

		Transaction newTransaction = newSession.beginTransaction();

		// Third unit of work
		Session thirdSession = factory.openSession();
		Transaction thirdTransaction = thirdSession.beginTransaction();
		message = (Message) thirdSession.get(Message.class, msgId);
		message.setText("Greetings Earthling");
		message.setNextMessage(new Message("Take me to your leader (please)"));

		thirdTransaction.commit();
		thirdSession.close();
		List<Message> messages = (List<Message>) newSession.createQuery(
				"from Message m order by m.text asc").list();

		System.out.println(messages.size() + " message(s) found: ");
		for (Iterator<Message> iter = messages.iterator(); iter.hasNext();) {
			Message loadedMsg = iter.next();
			System.out.println(loadedMsg.getText());
		}

		newTransaction.commit();
		newSession.close();

	}
}
