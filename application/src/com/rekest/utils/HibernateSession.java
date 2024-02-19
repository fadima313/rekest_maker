package com.rekest.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSession {

	private static SessionFactory factory = null ;
	private static Session session = null ;
	private static String hibernateConfiguration = null;
	
	public final static Logger logger = LogManager.getLogger(HibernateSession.class);

	private HibernateSession() throws Exception {
		try {
		// Since Hibernate Version 4.x, Service Registry Is Being Used
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure(hibernateConfiguration).build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		// Creating Hibernate Session Factory Instance
		factory = meta.getSessionFactoryBuilder().build();

		} catch (Exception exception) {
			logger.error("Unable to load Hibernate configuration : " + exception.getMessage());
			throw new Exception (exception);
		}
	}
	
	// Method Used To Create The Hibernate's SessionFactory Object
	public static Session getSession () throws Exception {
		if (session == null) {
			factory = getSessionFactory ();
			session = factory.openSession();
		}
		return session;
	}
	
	public static void close() {
		if ( factory != null ) {
			closeSession ();
			factory.close();
			factory = null;
		}
	}

	/**
	 * @return the hibernateConfiguration
	 */
	public static String getHibernateConfiguration() {
		return hibernateConfiguration;
	}

	private static SessionFactory getSessionFactory() throws Exception {
		init();
		return factory;
	}

	private static void init () throws Exception {		
		if ( factory == null ) {
			setHibernateConfiguration("hibernate.cfg.xml");
			new HibernateSession();
			logger.info("HibernateSession created.");
		}
	}

	private static void closeSession() {
		if (session != null) {
			session.close();
			session = null;
		}
	}
	
	/**
	 * @param hibernateConfiguration the hibernateConfiguration to set
	 */
	private static void setHibernateConfiguration(String hibernateConfiguration) {
		HibernateSession.hibernateConfiguration = hibernateConfiguration;
	}
}