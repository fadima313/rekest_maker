package com.rekest.feature;

import java.util.List;

import com.rekest.entities.Demande;
import com.rekest.entities.Departement;
import com.rekest.entities.Note;
import com.rekest.entities.Notification;
import com.rekest.entities.Produit;
import com.rekest.entities.Role;
import com.rekest.entities.Service;
import com.rekest.entities.employes.Administrateur;
import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.ChefService;
import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Manager;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.observableList.impl.ObservableListChefDepartement;
import com.rekest.observableList.impl.ObservableListDemande;
import com.rekest.observableList.impl.ObservableListDepartement;
import com.rekest.observableList.impl.ObservableListEmploye;
import com.rekest.observableList.impl.ObservableListManager;
import com.rekest.observableList.impl.ObservableListNote;
import com.rekest.observableList.impl.ObservableListNotification;
import com.rekest.observableList.impl.ObservableListProduit;
import com.rekest.observableList.impl.ObservableListRole;
import com.rekest.observableList.impl.ObservableListService;
import com.rekest.observableList.impl.ObservableListUtilisateur;

import javafx.collections.ObservableList;

/** 
 * @author Pape Omar Sylla
 */
public interface IFeature {

	
	/**
	 * Returns a list of all 'services'.
	 * 
	 * @return
	 */
	public List<Service> listServices ()  ;

	/**
	 * Returns a list of all 'services' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Service> listServices ( String whereClause)   ;
	
	/**
	 * Delete the 'service' set in parameter.
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 */
	public boolean deleteService (Service service)  ;

	/**
	 * Update the 'service' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 */
	public boolean updateService (Service service)  ;

	/**
	 * Save the 'service' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 */
	public boolean createService (Service service)  ;
	


	/**
	 * Find and return the 'service' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Service findService( String whereClause)   ;

	/**
	 * Find and return the 'service' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Service findService( Integer primaryKey)   ;	
	

	/**
	 * Returns a list of all 'departements'.
	 * 
	 * @param 
	 * @return
	 */
	public List<Departement> listDepartements ()   ;

	/**
	 * 
	 * Returns a list of 'departements' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Departement> listDepartements ( String whereClause)   ;
	
	/**
	 * Delete the 'departement' set in parameter.
	 * Returns true if it succeeded, else false.
	 * 
	 * @param departement
	 */
	public boolean deleteDepartement (Departement departement)   ;

	/**
	 * Update the 'departement' set in parameter.
	 * Returns true if it succeeded, else false.
	 * 
	 * @param departement
	 */
	public boolean updateDepartement (Departement departement)   ;

	/**
	 * Save the 'departement' set in parameter.
	 * 
	 * 
	 * @param departement
	 */
	public boolean createDepartement (Departement departement)   ;


	/**
	 * Find the 'departement' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Departement findDepartement ( String whereClause)   ;

	/**
	 * Find the 'departement' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Departement findDepartement ( Integer primaryKey)   ;	
	
	

	/**
	 * Returns a list of all 'managers'.
	 * 
	 * @return
	 */
	public List<Manager> listManagers ()   ;

	/**
	 * Returns a list of all 'manager' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Manager> listManagers ( String whereClause)    ;
	
	/**
	 * Delete the 'manager' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 */
	public boolean deleteManager (Manager manager)   ;

	/**
	 * Update the 'manager' set in parameter. 
	 * Returns true if it succeeded, else false.
	 *  
	 * 
	 * @param service
	 */
	public boolean updateManager (Manager manager)   ;

	/**
	 * Save the 'manager' set in parameter. 
	 *  
	 * 
	 * @param service
	 */
	public boolean createManager (Manager manager)   ;
	


	/**
	 * Find and return the 'manager' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Manager findManager( String whereClause)    ;

	/**
	 * Find and return the 'manager' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Manager findManager( Integer primaryKey)   ;
	
	
	
	/**
	 * Enable the user set in parameter.
	 * Returns true if it succeeded, else false.
	 *  
	 * 
	 * @param utilisateur
	 * @return
	 */
	public boolean enableUtilisateur  (Utilisateur utilisateur)  ;	
	
	/**
	 * Disable the user set in parameter.
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param utilisateur
	 * @return
	 */
	public boolean disableUtilisateur  (Utilisateur utilisateur)  ;	
	
	/**
	 * 
	 */
	//public boolean rafraichirUtilisateur (Utilisateur utilisateur)  ;


	/**
	 * Returns a list of all 'utilisteurs'.
	 * 
	 * @param persons
	 * @return
	 */
	public List<Utilisateur> listUtilisateurs  ()  ;

	/**
	 * Returns a list of all 'utilisteurs' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Utilisateur> listUtilisateurs  ( String whereClause)   ;
	
	/**
	 * 
	 * Delete the 'utilisateur' set in parameter.
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param utilisateur
	 */
	public boolean deleteUtilisateur  (Utilisateur utilisateur)  ;

	/**
	 * Update the 'utilisateur' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param utilisateur
	 */
	public boolean updateUtilisateur  (Utilisateur utilisateur)  ;

	/**
	 * Save the 'utilisateur' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param utilisateur
	 */
	public boolean createUtilisateur  (Utilisateur utilisateur)  ;


	/**
	 * Find and return the 'utilisateur' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Utilisateur findUtilisateur ( String whereClause)   ;

	/**
	 * Find and return the 'utilisateur' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Utilisateur findUtilisateur ( Integer primaryKey)   ;	
	
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public String getTheDaoImplementationClassname ()  ;
	
	/**
	 * @return
	 * @  
	 */
	//public ObservableList<Utilisateur> chargerUtilisateurObservableList ()  ;

	/**
	 * @return
	 */
	//public ObservableList<Utilisateur> getCurrentUtilisateurObservableList ()  ;


	/**
	 * Returns a list of all 'chefDepartements'.
	 * 
	 * @param persons
	 * @return
	 */
	public List<ChefDepartement> listChefDepartements  ()  ;

	/**
	 * Returns a list of all 'chefDepartements' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<ChefDepartement> listChefDepartements  ( String whereClause)   ;
	
	/**
	 * Delete the 'chefDepartement' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 *  
	 * @param chefDepartement
	 */
	public boolean deleteChefDepartement  (ChefDepartement chefDepartement)  ;

	/**
	 * Update the 'chefDepartement' set in parameter. 
	 *  Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param chefDepartement
	 */
	public boolean updateChefDepartement  (ChefDepartement chefDepartement)  ;

	/**
	 * Save the 'chefDepartement' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param chefDepartement
	 */
	public boolean createChefDepartement  (ChefDepartement chefDepartement)  ;


	/**
	 * Find and return the 'chefDepartement' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public ChefDepartement findChefDepartement ( String whereClause)   ;

	/**
	 * Find and return the 'chefDepartement' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public ChefDepartement findChefDepartement ( Integer primaryKey)   ;	

	
	public ChefService findChefService (String whereClause) ;
	
	public ChefService findChefService (Integer primaryKey) ;



	/**
	 * Returns a list of all 'produits'.
	 * 
	 * @param persons
	 * @return
	 */
	public List<Produit> listProduits  ()  ;

	/**
	 * Returns a list of all 'produits' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Produit> listProduits  ( String whereClause)   ;
	
	/**
	 * Delete the 'produit' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 *  
	 * @param produit
	 */
	public boolean deleteProduit  (Produit produit)  ;

	/**
	 * Update the 'produit' set in parameter. 
	 *  Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param produit
	 */
	public boolean updateProduit  (Produit produit)  ;

	/**
	 * Save the 'produit' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param produit
	 */
	public boolean createProduit  (Produit produit)  ;


	/**
	 * Find and return the 'produit' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Produit findProduit ( String whereClause)   ;

	/**
	 * Find and return the 'produit' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Produit findProduit ( Integer primaryKey)   ;	

	
	
	
	

	


	/**
	 * Returns a list of all 'roles'.
	 * 
	 * @param persons
	 * @return
	 */
	public List<Role> listRoles  ()  ;

	/**
	 * Returns a list of all 'roles' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Role> listRoles  ( String whereClause)   ;
	
	/**
	 * Delete the 'role' set in parameter. 
	 * Returns true if it succeeded, else false.
	 *  
	 * @param role
	 */
	public boolean deleteRole  (Role role)  ;

	/**
	 * Update the 'role' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param role
	 */
	public boolean updateRole  (Role role)  ;

	/**
	 * Save the 'role' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param role
	 */
	public boolean createRole  (Role role)  ;


	/**
	 * Find and return the 'role' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Role findRole ( String whereClause)   ;
	

	/**
	 * Find and return the 'role' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Role findRole ( Integer primaryKey)   ;	


	/**
	 * Returns a list of all 'notifications'.
	 * 
	 * @param persons
	 * @return
	 */
	public List<Notification> listNotifications  ()  ;

	/**
	 * Returns a list of all 'notifications' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Notification> listNotifications  ( String whereClause)   ;
	
	/**
	 * Delete the 'notification' set in parameter. 
	 * Returns true if it succeeded, else false.
	 *  
	 * @param notification
	 */
	public boolean deleteNotificationFeature  (Notification notification, Utilisateur utilisateur, Demande demande) ;

	/**
	 * Update the 'notification' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param notification
	 */
	public boolean updateNotification  (Notification notification)  ;

	/**
	 * Save the 'notification' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param notification
	 */
	public boolean createNotificationFeature   (Utilisateur utilisateur, Demande demande, String message)  ;


	/**
	 * Find and return the 'notification' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Notification findNotification ( String whereClause)   ;
	

	/**
	 * Find and return the 'notification' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Notification findNotification ( Integer primaryKey)   ;	



	/**
	 * Returns a list of all 'employes'.
	 * 
	 * @param persons
	 * @return
	 */
	public List<Employe> listEmployes  ()  ;

	/**
	 * Returns a list of all 'employes' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Employe> listEmployes  ( String whereClause)   ;
	
	/**
	 * Delete the 'employe' set in parameter. 
	 * Returns true if it succeeded, else false.
	 *  
	 * @param employe
	 */
	public boolean deleteEmploye  (Employe employe)  ;

	/**
	 * Update the 'employe' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * 
	 * @param employe
	 */
	public boolean updateEmploye  (Employe employe)  ;

	/**
	 * Save the 'employe' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param employe
	 */
	public boolean createEmploye  (Employe employe)  ;


	/**
	 * Find and return the 'employe' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Employe findEmploye ( String whereClause)   ;

	/**
	 * Find and return the 'employe' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */

	public Employe findEmploye ( Integer primaryKey)   ;	
	

	
	/**
	 * TODO
	 * @param Demande
	 * @return
	 */
	//public boolean rafraichirDemande (Demande Demande) throws Exception 




	/**
	 * Returns a list of all 'Demandes'.
	 * 
	 * @return
	 */

	public List<Demande> listDemandes ()   ;

	

	/**
	 * Returns a list of all 'Demande' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */

	public List<Demande> listDemandes ( String whereClause)    ;

	
	/**
	 * Delete the 'Demande' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 */

	public boolean deleteDemande (Demande Demande)   ;


	/**
	 * Update the 'Demande' set in parameter. 
	 * Returns true if it succeeded, else false. 
	 * 
	 * @param service
	 */

	public boolean updateDemande (Demande Demande)   ;


	/**
	 * Save the 'Demande' set in parameter. 
	 * Returns true if it succeeded, else false. 
	 * 
	 * @param service
	 */
	public boolean createDemande (Utilisateur utilisateur, Demande demande , Employe employe)   ;

	/**
	 * Find and return the 'Demande' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */

	public Demande findDemande( String whereClause)    ;


	/**
	 * Find and return the 'Demande' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */

	public Demande findDemande( Integer primaryKey)   ;

	/**
	 * TODO
	 * @param note
	 * @return
	 */
	

	/**
	 * Returns a list of all 'notes'.
	 * 
	 * @return
	 */
	public List<Note> listNotes ()   ;
	
/**
	 * Returns a list of all 'note' according to the filters.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public List<Note> listNotes ( String whereClause)    ;

	/**
	 * Delete the 'note' set in parameter. 
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 */
	public boolean deleteNote (Note note)   ;

	/**
	 * Update the 'note' set in parameter. 
	 * Returns true if it succeeded, else false. 
	 * 
	 * @param service
	 */

	public boolean updateNote (Note note)   ;


	/**
	 * Save the 'note' set in parameter. 
	 * Returns true if it succeeded, else false. 
	 * @param demande 
	 * 
	 * @param service
	 */
	public boolean createNote (Note note, Demande demande)   ;


	/**
	 * Find and return the 'note' set in parameter if it exist, else null.
	 * 
	 * @param primaryKey
	 * @return
	 * @ 
	 */
	public Note findNote( Integer primaryKey)   ;
	
	/**
	 * Find and return the 'note' set in parameter if it exist, else null.
	 * 
	 * @param whereClause
	 * @return
	 * @ 
	 */
	public Note findNote (String whereClause)  ;
	
	
///////////////
	
/**
* Returns a list of all 'services'.
* 
* @return
*/
public List<Notification> listerNotifications ()  ;

/**
* Returns a list of all 'notifications' according to the filters.
* 
* @param whereClause
* @return
* @ 
*/
public List<Notification> listerNotifications ( String whereClause)   ;

/**
* Delete the 'notification' set in parameter.
* Returns true if it succeeded, else false.
* 
* @param service
*/
public boolean supprimerNotification (Notification notification)  ;

/**
* Update the 'Notification' set in parameter. 
* Returns true if it succeeded, else false.
* 
* @param service
*/
public boolean modifierNotification (Notification notification)  ;

/**
* Save the 'notification' set in parameter. 
* Returns true if it succeeded, else false.
* 
* @param service
*/
public boolean creerNotification (Notification notification)  ;



/**
* Find and return the 'notification' set in parameter if it exist, else null.
* 
* @param whereClause
* @return
* @ 
*/
public Service rechercherNotification( String whereClause)   ;

/**
* Find and return the 'notification' set in parameter if it exist, else null.
* 
* @param primaryKey
* @return
* @ 
*/
public Service rechercherNotification( Integer primaryKey)   ;


	
	/**
	 * Returns the number of 'Demandes'
	 * 
	 * @return
	 */
	public Integer getNumberDemandes();
	
	/**
	 * Returns the number of 'Roles'
	 * @return
	 */
	public Integer getNumberRoles();
	
	/**
	 * Returns the number of 'Employes'
	 * 
	 * @return
	 */
	public Integer getNumberEmployes ();
	
	/**
	 * Returns the number of 'Departements'
	 * 
	 * @return
	 */
	public Integer getNumberDepartements ();
	
	/**
	 * Returns the number of 'Services'
	 * 
	 * @return
	 */
	public Integer getNumberServices ();
	
	/**
	 * Returns the number of 'Produits'
	 * 
	 * @return
	 */
	public Integer getNumberProduits();
	
	
	
	/**
	 * Returns the current ObservableListEmploye
	 * 
	 * @return
	 */
	public ObservableListEmploye getObservableListEmploye ();
	
	/**
	 * Returns the current ObservableListManager
	 * 
	 * @return
	 */	
	public ObservableListManager getObservableListManager () ;
	
	/**
	 * Returns the current ObservableListChefDepartement
	 * 
	 * @return
	 */
	public ObservableListChefDepartement getObservableListChefDepartement () ;
	
	/**
	 * Returns the current ObservableListNote
	 * 
	 * @return
	 */
	public ObservableListNote getObservableListNote () ;
	
	
	/**
	 * Returns the current ObservableListProduit
	 * 
	 * @return
	 */
	public ObservableListProduit getObservableListProduit ();
	
	/**
	 * Returns the current ObservableListRole
	 * 
	 * @return
	 */
	public ObservableListRole getObservableListRole () ;
	
	/**
	 * Returns the current ObservableListUtilisateur
	 * 
	 * @return
	 */
	public ObservableListUtilisateur getObservableListUtilisateur () ;
	
	/**
	 * Returns the current ObservableListService
	 * 
	 * @return
	 */
	public ObservableListService getObservableListService () ;
	
	/**
	 * Returns the current ObservableListDemande
	 * 
	 * @return
	 */
	public ObservableListDemande getObservableListDemande () ;
	
	/**
	 * Returns the current ObservableListDepartement
	 * 
	 * @return
	 */
	public ObservableListDepartement getObservableListDepartement () ;
	
	
	/**
	 * Returns the current ObservableListNotification
	 * 
	 * @return
	 */
	public ObservableListNotification getObservableListNotification () ;
	
	
	/**
	 * Returns the number of 'Notifications'
	 * 
	 * @return
	 */
	public Integer RetournerNombreNotificationsTotal();
	

	/**
	 * Returns a Observable list of the stored 'Demandes'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Demande> loadDemandesObservableList ()  ;


	
	
	/**
	 * Returns a Observable list of the stored 'Employes'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Employe> loadEmployesObservableList ();
	
	/**
	 * Returns a Observable list of the stored 'Employes'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Employe> loadOnlyEmployesObservableList ();	
	
	/**
	 * Returns a Observable list of the stored 'Utilisateurs'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Utilisateur> loadUtilisateursObservableList ()  ;




	/**
	 * Returns a Observable list of the stored 'Roles'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Role> loadRoleObservableList ()  ;

	/**
	 * Returns a Observable list of the stored 'ChefDepartements'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<ChefDepartement> loadChefDepartementObservableList ()  ;



	/**
	 * Returns a Observable list of the stored 'Notifications'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Notification> loadNotificationObservableList ()  ;


	/**
	 * Returns a Observable list of the stored 'Managers'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Manager> loadManagerObservableList ()  ;


	/**
	 * Returns a Observable list of the stored 'Notes'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Note> loadNoteObservableList ()  ;




	/**
	 * Returns a Observable list of the stored 'Produits'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Produit> loadProduitsObservableList ()  ;



	/**
	 * Returns a Observable list of the stored 'Services'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Service> loadServicesObservableList ()  ;




	/**
	 * Returns a Observable list of the stored 'Departement'
	 * 
	 * @return
	 * @ 
	 */
	public ObservableList<Departement> loadDepartementsObservableList ()  ;
	


	
	 
	/**
	 * Todo
	 * Returns a Observable list of the stored 'Demandes'.
	 * 
	 * @return
	 */
	public ObservableList<Demande> loadDemandesByServiceObservableList (Service service)  ;


	/**
	 * Todo
	 * Returns a Observable list of the stored 'Demandes' by direction.
	 * 
	 * @return
	 */
	public ObservableList<Demande> loadDemandesByDirectionObservableList (Object direction)  ;
	
	

	/**
	 * Returns the user identified, else null.
	 * 
	 * @param login
	 * @param password
	 * @return
	 * @ 
	 */
	public  Object validateCredential(String login, String password)  ;
	
	/**
	 * Method qui permet de definir la reponse de la demande (rejeter , cloturée ...).
	 * Returns true if it succeeded, else false.
	 *
	 * @param demande
	 * @param reponse
	 * @ 
	 */
	public boolean requestDemande(Demande demande, String reponse)  ; 
	
	
	/**
	 * Associate a employe to a service.
	 * Returns true if it succeeded, else false.
	 * 
	 * @param employe
	 * @param service
	 * @ 
	 */
	public boolean associateService(Employe employe, Service service)  ;
	
	
	
	/**
	 * Associate a service to a departement.
	 * Returns true if it succeeded, else false.
	 * 
	 * @param service
	 * @param departement
	 * @return
	 */
	public boolean associateDepartement(Service service, Departement departement) ;
	
	/**
	 * 
	 */
	default void refresh() {};
	
	/**
	 * Generate a 'departement'
	 */
	default void initDepartement() {}
	
	/**
	 * Generate 10 'employes'
	 */
	default void initEmploye() {}
	
	/**
	 * Generate 10 'services'
	 */
	default void initService() {}
	
	/**
	 * Generate 10 'roles'
	 */
	default void initRole() {}
	
	/**
	 * Generate 10 'produits'
	 */
	default void initProduit() {}
	
	/**
	 * Generate 3 'admins'
	 */
	default void initAdmin() {}
	
	
	/**
	 * Generate 1 'Chef de service', 1 'directeur' and 1 'directeur general'
	 */
	default  void initManagers() {}
	
	
	/**
	 * Create a default admin with login="admin" and password="admin"
	 * 
	 * @return
	 */
	default Administrateur createDefaultAdmin() {return null;}
	
	
	/**
	 * Call 'init' methods
	 * 
	 */
	default void initAllEntity() {}

	public Boolean createNote(Utilisateur auth, Note tempNote, Demande demande);

	ObservableList<Demande> loadDemandeByUtilisateurObservableList(Utilisateur auth);

	public List<Notification> listNotificationsByUtilisateur(Utilisateur auth);

	public ObservableList<Note> loadNoteByDemandObservableList(Demande demande);
	
	public Integer getNumberNotificationUnread (Utilisateur auth);

	
	
}