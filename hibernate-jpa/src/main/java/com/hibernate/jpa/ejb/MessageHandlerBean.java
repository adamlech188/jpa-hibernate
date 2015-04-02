package com.hibernate.jpa.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.hibernate.jpa.domain.Message;

@Stateless
public class MessageHandlerBean implements MessageHandler {
	
	@PersistenceContext
	EntityManager em; 

	@Override
	public void saveMessages() {
		Message message = new Message("Hello World");
		em.persist(message);

	}

	@Override
	public void showMessages() {
		@SuppressWarnings("rawtypes")
		List messages =  em.createQuery("select  m from Message m order by m.text asc").getResultList(); 

		  System.out.println(messages.size() + " message(s) found:");

	        for (Object m : messages) {
	            Message loadedMsg = (Message) m;
	            System.out.println(loadedMsg.getText());
	        }
	}

}
