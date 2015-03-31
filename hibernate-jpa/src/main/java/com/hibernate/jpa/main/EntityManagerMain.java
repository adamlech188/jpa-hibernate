package com.hibernate.jpa.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.hibernate.jpa.domain.Message;

public class EntityManagerMain {

	public static void main(String[] argds) {
		
		//Start EntityManagerFactory
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");
		
		//First unit of work 
		EntityManager em = emf.createEntityManager(); 
		EntityTransaction tx = em.getTransaction(); 
		tx.begin(); 
		
		Message message = new Message("Hello world!"); 
		em.persist(message);
		
		tx.commit(); 
		em.close(); 
		
		// Second unit of work
        EntityManager newEm = emf.createEntityManager();
        EntityTransaction newTx = newEm.getTransaction();
        newTx.begin();

        @SuppressWarnings("rawtypes")
		List messages = newEm
            .createQuery("select m from Message m order by m.text asc")
            .getResultList();

        System.out.println( messages.size() + " message(s) found" );

        for (Object m : messages) {
            Message loadedMsg = (Message) m;
            System.out.println(loadedMsg.getText());
        }

        newTx.commit();
        newEm.close();

        // Shutting down the application
        emf.close();
		
	}
}
