package com.rekest.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.rekest.dao.IDao;
import com.rekest.entities.Demande;
import com.rekest.entities.Departement;
import com.rekest.entities.Service;
import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.exeptions.DAOException;
import com.rekest.utils.HibernateSession;

public class HibernateDao implements IDao{

	public final static Logger logger = LogManager.getLogger(HibernateDao.class);

	private static Session session = null;
	private static Transaction transaction = null;
	private static HibernateDao daoInstance = null;


	protected HibernateDao() {}

	public static HibernateDao getCurrentInstance () {
		if (daoInstance == null) daoInstance = new HibernateDao();
		return daoInstance;
	}

	@Override
	public void save(Object entity) throws DAOException{
		try {
			session = HibernateSession.getSession();
			transaction = session.beginTransaction();
			logger.info("Begin transaction.");// begin transaction
			session.persist(entity);
			transaction.commit();

			logger.info("Record is Successully created");  //  end transaction
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public void delete(Object entity) throws DAOException{
		try {
			session = HibernateSession.getSession();//Creating Transaction Object
			transaction = session.beginTransaction();
			logger.info("Begin transaction.");
			session.remove(entity);
			transaction.commit(); // Transaction Is Committed To Database
			logger.info("Record is Successfully deleted.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public Object find(Object entityClass, Integer primaryKey) throws DAOException{
		Object entity = null;
		try {
			session = HibernateSession.getSession();
			entity = session.find(entityClass.getClass(), primaryKey);
			if (entity != null) logger.info("Record Successfully read.");
			else logger.info("Record not found.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return entity;
	}

	@Override
	public Object find(Class<?> entityClass, String whereClause) throws DAOException {
		Object entity = null;
		try {
			session = HibernateSession.getSession();							
			@SuppressWarnings("deprecation")
			Query<?> query = session.createQuery("From " + entityClass.getName() + " " + whereClause); 
			entity = query.getSingleResult();
			if (entity != null) logger.info("Record Successfully read.");
			else logger.info("Record not found.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> list(Class<?> entityClass, String whereClause) throws DAOException {
		List<Object> entities = null;
		try {
			session = HibernateSession.getSession();
			@SuppressWarnings("deprecation")
			Query<?> query = session.createQuery("From " + entityClass.getName() + " " + whereClause); 
			entities = (List<Object>) query.getResultList();

			if (entities != null) logger.info("Records Successfully read.");
			else logger.info("Records not found.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> list (Object entityClass) throws DAOException{
		List<Object> entities = null;
		try {
			session = HibernateSession.getSession();
			@SuppressWarnings("deprecation")
			Query<?> query = session.createQuery("From " + entityClass.getClass().getSimpleName());
			entities = (List<Object>) query.getResultList();
			if (entities != null) logger.info("Records Successfully read.");
			else logger.info("Records not found.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return entities;
	}

	@Override
	public void update(Object entity) throws DAOException {
		try {
			session = HibernateSession.getSession();
			//Creating Transaction Object  
			transaction = session.beginTransaction();
			logger.info("Begin transaction.");
			session.persist(entity);
			// Transaction Is Committed To Database
			transaction.commit();
			logger.info("Record Successfully updated.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}


	@Override
	public void enableAccount(Utilisateur entity) throws DAOException{
		entity.setEnable(true);
		this.update(entity);
	}

	@Override
	public void disableAccount(Utilisateur entity) throws DAOException{
		entity.setEnable(false);
		this.update(entity);
	}

	@Override
	public void associateService(Employe employe, Service service) throws DAOException{
		service.addEmploye(employe);
		this.update(service);
	}

	@Override
	public void dissociateService(Employe employe, Service service) throws DAOException {
		service.removeEmploye(employe);
		this.update(service);
	}

	@Override
	public void associateDepartement(Service service, Departement departement) throws DAOException{
		departement.addService(service);
		this.update(departement);
	}

	@Override
	public Utilisateur validateCredential(String login, String password)  throws DAOException{
		Utilisateur utilisateur = null;
		String whereClause = "where login = " + "'"+login+"'"+ " and password = " +"'"+password+"'"; 
		try {
			session = HibernateSession.getSession();
			@SuppressWarnings("deprecation")
			Query<?> query = session.createQuery("From Utilisateur " + whereClause); 
			utilisateur =  (Utilisateur) query.uniqueResult();
			if (utilisateur != null  && utilisateur.isEnable()) {
				logger.info("Utilisateur trouver !");
				return utilisateur;
			}  
			else {
				logger.info("Utilisateur non trouvé !");
				return null;
			}
		} catch (Exception e) {
			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
	}

	@Override
	public List<Object> list() throws DAOException {
		return null;
	}

	@Override
	public void requestResponse(Demande demande, String reponse) throws DAOException {
		demande.setEtat(reponse);
		this.update(demande);
	}


	public static void closeSession() {
		HibernateSession.close();		
	}

	@Override
	public Object findUserByNumber(String whereClause) throws DAOException {
		Object entity = null;
		try {
			session = HibernateSession.getSession();
			@SuppressWarnings("deprecation")
			Query<?> query = session.createQuery("From Employe where telephone = " +whereClause); 
			entity = query.getSingleResult();
			if (entity != null) logger.info("Record Successfully read.");
			else logger.info("Record not found.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return entity;
	}

	@Override
	public Object findProductByName(String whereClause) throws DAOException {
		Object entity = null;
		try {
			session = HibernateSession.getSession();
			@SuppressWarnings("deprecation")
			Query<?> query = session.createQuery("From Produit where nom = :name");
			query.setParameter("name", whereClause);
			entity = query.getSingleResult();
			if (entity != null) logger.info("Record Successfully read.");
			else logger.info("Record not found.");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();

			throw new DAOException("ERROR:" + e.getClass() + ":" + e.getMessage());
		}
		return entity;
	}

}