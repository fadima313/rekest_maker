package com.rekest.dao;

import java.util.List;

import com.rekest.entities.Demande;
import com.rekest.entities.Departement;
import com.rekest.entities.Service;
import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.exeptions.DAOException;

import javafx.collections.ObservableList;


/**
 * @author Illiassou
 *
 */
public interface IDao {

	/**
	 * Save entity 
	 * @param entity
	 * @throws DAOException 
	 * @ 
	 */
	public void save(Object entity) throws DAOException ;

	/** Delete entity
	 * @param entity
	 * @throws DAOException 
	 * @ 
	 */
	public void delete(Object entity) throws DAOException ;


	public Object find(Object entityClass, Integer primaryKey) throws DAOException ;
	
	/** Find user by email
	 * @param users
	 * @param whereClause
	 * @return
	 * @throws DAOException 
	 * @ 
	 */
	public Object findUserByNumber(String whereClause) throws DAOException ;

	/** Find entity by clause
	 * @param entityClass
	 * @param whereClause
	 * @return
	 * @throws DAOException 
	 * @ 
	 */
	public Object findProductByName(String whereClause) throws DAOException ;
	
	/** Find entity by clause
	 * @param entityClass
	 * @param whereClause
	 * @return
	 * @throws DAOException 
	 * @ 
	 */
	public Object find(Class<?> entityClass, String whereClause) throws DAOException ;
	
	/** List entities
	 * @param entityClass
	 * @return
	 * @ 
	 */
	public List<Object> list(Object entityClass) throws DAOException;

	/** List entities with close
	 * @param entityClass
	 * @return
	 * @throws DAOException 
	 * @ 
	 */
	public List<Object> list(Class<?> entityClass, String whereClause) throws DAOException ;

	/**
	 * List entities
	 * @return
	 * @throws DAOException
	 */
	public List<Object> list() throws DAOException;

	
	/** Update entity
	 * @param entity
	 * @ 
	 */
	public void update(Object entity) throws DAOException;


	/**
	 * 
	 * @param login
	 * @param password
	 * @return
	 * @throws DAOException
	 */
	public default Object validateCredential(String login, String password) throws DAOException{
		return null;
	}

	/**
	 * Enable user account
	 * @param entity
	 * @throws DAOException
	 */
	public default void enableAccount(Utilisateur entity) throws DAOException{};

	/**
	 * Disable user account
	 * @param entity
	 * @throws DAOException
	 */
	public default void disableAccount(Utilisateur entity) throws DAOException{};

	/**
	 * Associate an employe to a service
	 * @param employe
	 * @param service
	 * @throws DAOException
	 */
	public default void associateService(Employe employe, Service service) throws DAOException{};

	
	/**
	 * Dissociate an employe from a service
	 * @param employe
	 * @param service
	 * @throws DAOException
	 */
	public default void dissociateService(Employe employe, Service service) throws DAOException{};

	/**
	 * @param employe
	 * @param departement
	 * @throws DAOException
	 */
	public default void associateDepartement(Service service, Departement departement) throws DAOException{};

	/**
	 * Definir la reponse de la demande (rejeter , clotur�e ...)
	 * @param demande
	 * @param reponse
	 * @throws Exception
	 */
	public void requestResponse(Demande demande, String reponse) throws DAOException;
	
	/**
	 * @return
	 * @throws DAOException
	 */
	public default ObservableList<Departement> departementlistObservable() throws DAOException {
		return null;
	}
}
