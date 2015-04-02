package com.hibernate.jpa.main;

import javax.naming.InitialContext;

import org.jboss.ejb3.embedded.EJB3StandaloneBootstrap;

import com.hibernate.jpa.ejb.MessageHandler;

public class EJBMain {

	public static void main(String[] args) throws Exception {
		

		
		EJB3StandaloneBootstrap.boot(null);
		
	    EJB3StandaloneBootstrap
        .deployXmlResource("META-INF/helloworld-beans.xml");
	    
	    EJB3StandaloneBootstrap.scanClasspath("hibernate-jp/");
	    
	    InitialContext initialContext = new InitialContext();

	    // Look up the stateless MessageHandler EJB
	    MessageHandler msgHandler = (MessageHandler) initialContext
	                                .lookup("MessageHandlerBean/local");

	    // Call the stateless EJB
	    msgHandler.saveMessages();
	    msgHandler.showMessages();

	    // Shut down EJB container
	    EJB3StandaloneBootstrap.shutdown();
	}

}
