package com.rekest.feature.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.rekest.dao.IDao;
import com.rekest.dao.impl.HibernateDao;
import com.rekest.entities.Demande;
import com.rekest.entities.Departement;
import com.rekest.entities.Note;
import com.rekest.entities.Notification;
import com.rekest.entities.Produit;
import com.rekest.entities.Role;
import com.rekest.entities.Service;
import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.ChefService;
import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Manager;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.exeptions.DAOException;
import com.rekest.feature.IFeature;
import com.rekest.notificationmanager.impl.NotificationManager;
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
import com.rekest.utils.ErrorLogFileManager;
import com.rekest.utils.Utilitaire;
import javafx.collections.ObservableList;

public class Feature implements IFeature {

	public final static Logger logger = LogManager.getLogger(Feature.class);
	
	private static Feature instance = new Feature();
	private static IDao dao = HibernateDao.getCurrentInstance();
	private static NotificationManager notifManager = new NotificationManager();
	
	/**
	 * ObservaList
	 */
	private ObservableListDepartement observableListDepartement;
	private ObservableListEmploye observableListEmploye ;
	private ObservableListProduit observableListProduit ;
	private ObservableListService observableListService ;
	private ObservableListRole observableListRole;
	private ObservableListDemande observableListDemande ;
	private ObservableListManager observableListManager ;
	private ObservableListNote observableListNote ;
	private ObservableListChefDepartement observableListChefDepartement ;
	private ObservableListUtilisateur observableListUtilisateur;
	private ObservableListNotification observableListNotification;
	private ObservableListDemande observableListDemandeByUtilisateur ;
	private ObservableListNote observableListNoteByDemande ;
	private ObservableListEmploye observableListOnlyEmploye ;
	
	private Feature () {
		observableListDemandeByUtilisateur = new ObservableListDemande();
		observableListNotification = new ObservableListNotification();
		
		observableListDepartement = new ObservableListDepartement ();
		observableListEmploye = new ObservableListEmploye ();
		observableListProduit = new ObservableListProduit ();
		observableListService = new ObservableListService ();
		observableListRole = new ObservableListRole ();
		observableListDemande = new ObservableListDemande ();
		observableListManager = new ObservableListManager ();
		observableListNote = new ObservableListNote ();
		observableListUtilisateur = new ObservableListUtilisateur ();
		observableListNotification = new ObservableListNotification ();
		observableListChefDepartement = new ObservableListChefDepartement ();
		observableListOnlyEmploye = new ObservableListEmploye();
		observableListNoteByDemande = new ObservableListNote();
	}

	public static Feature getCurrentInstance () {
		if (instance == null) instance = new Feature ();
		return instance;
	}

	private static void AlertError (Exception e,String context) {
		e.printStackTrace ();

	}

	public ObservableListDemande getObservableListDemande() {
		return observableListDemande;
	}

	public ObservableListDepartement getObservableListDepartement() {
		return observableListDepartement;
	}

	public ObservableListEmploye getObservableListEmploye() {
		return observableListEmploye;
	}

	public ObservableListManager getObservableListManager() {
		return observableListManager;
	}

	public ObservableListNote getObservableListNote() {
		return observableListNote;
	}

	public ObservableListProduit getObservableListProduit() {
		return observableListProduit;
	}

	public ObservableListRole getObservableListRole() {
		return observableListRole;
	}

	public ObservableListService getObservableListService() {
		return observableListService;
	}

	public ObservableListUtilisateur getObservableListUtilisateur() {
		return observableListUtilisateur;
	}

	public ObservableListChefDepartement getObservableListChefDepartement() {
		return observableListChefDepartement;
	}

	public ObservableListNotification getObservableListNotification() {
		return observableListNotification;
	}



	public boolean supprimerEmploye (Employe employe)   {
		try {
			if ( employe.getService() != null )
				dao.dissociateService ( employe, employe.getService());

			dao.delete ( employe);
			observableListEmploye.delete(employe);
			loadEmployesObservableList();
			return true;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			System.err.println("BLEM EMPLOYE");
			e.printStackTrace();
			return false;
		}

	}

	public boolean modifierEmploye (Employe employe)   {

		try {
			if ( employe.getService() != null && employe.getOldService() != employe.getService()) {
				if ( employe.getOldService() != null)
					dao.dissociateService ( employe, employe.getOldService());
				dao.associateService ( employe, employe.getService());
			}

			dao.update ( employe);
			loadEmployesObservableList();
			return true;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean creerEmploye (Employe employe)   {

		try {
			if ( employe.getService() != null)
				dao.associateService ( employe, employe.getService());

			dao.save ( employe);

			loadEmployesObservableList();
			return true;
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}




	@Override
	public List<Departement> listDepartements ()   {

		List<Object> objects =  null;
		List<Departement> departements = new ArrayList<> ();
		try {
			objects = dao.list ( new Departement ());

			for (Object obj : objects) {
				if (obj instanceof Departement) {
					departements.add ( (Departement) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get departements");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return departements;
	}

	@Override
	public List<Departement> listDepartements (String whereClause)  {

		List<Object> objects;
		List<Departement> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Departement.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Departement) {
					objs.add ( (Departement) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get departements");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return objs;
	}

	@Override
	public boolean deleteDepartement (Departement departement)   {
		try {
			dao.delete ( departement);
			loadDepartementsObservableList ();


			return true;
		} catch (DAOException e) {
			AlertError (e,"delete departement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateDepartement (Departement departement)   {

		try {
			dao.update ( departement);
			loadDepartementsObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update departement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createDepartement (Departement departement)   {

		try {
			dao.save ( departement);
			loadDepartementsObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create departement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}



	@Override
	public Departement findDepartement (String whereClause)   {

		try {
			return (Departement) dao.find (  Departement.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find departement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Departement findDepartement (Integer primaryKey)  {
		try {
			return (Departement) dao.find ( new Departement ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find departement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}



	@Override
	public boolean enableUtilisateur (Utilisateur utilisateur)   {

		try {
			dao.enableAccount (utilisateur);
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"enabling user");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean disableUtilisateur (Utilisateur utilisateur)   {

		try {
			dao.disableAccount (utilisateur);
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"desabling user");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	/*
	@Override
	public boolean rafraichirUtilisateur (Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		return dao.;
	}
	 */

	@Override
	public List<Utilisateur> listUtilisateurs ()   {

		List<Object> objects;
		List<Utilisateur> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Utilisateur ());

			for (Object obj : objects) {
				if (obj instanceof Utilisateur) {
					objs.add ( (Utilisateur) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get users");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Utilisateur> listUtilisateurs (String whereClause)  {

		List<Object> objects;
		List<Utilisateur> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Utilisateur.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Utilisateur) {
					objs.add ( (Utilisateur) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get users");
			ErrorLogFileManager.appendError (e.getMessage ());
		}


		return objs;
	}

	@Override
	public boolean deleteUtilisateur (Utilisateur utilisateur)   {
		try {

			List<Notification> notifs= utilisateur.getNotification();
			while(notifs.isEmpty() != true) {
				deleteNotificationFeature(notifs.get(0), null , null);
				notifs.remove(0);
			}
			utilisateur.setNotifications(notifs);

			List<Demande> demandes= utilisateur.getDemandesCreees();
			while(demandes.isEmpty() != true) {
				deleteDemande(demandes.get(0));
				demandes.remove(0);
			}
			utilisateur.setDemandesCreees(demandes);

			
			if ( utilisateur.getService() != null )
				dao.dissociateService ( utilisateur, utilisateur.getService());

			if ( utilisateur.getEmployeProfil() == "ChefService") {
				Service service = findService("WHERE chefService = '"+utilisateur.getId()+"'");
				if(service != null) {
					service.setChefService(null);
					dao.update(service);

				}

			}
			else
				if ( utilisateur.getEmployeProfil() == "ChefDepartement") {
					Departement departement = findDepartement("WHERE chefDepartement = '"+utilisateur.getId()+"'");
					if(departement != null) {
						departement.setChefDepartement(null);;
						dao.update(departement);

					}

				}

			dao.delete ( utilisateur);
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete user");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateUtilisateur (Utilisateur utilisateur)   {

		try {
			
//			String passwdHash = Utilitaire.hashPassword(utilisateur.getPassword());
//			utilisateur.setPassword(passwdHash);
			
			dao.update ( utilisateur);
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update user");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createUtilisateur (Utilisateur utilisateur)   {

		try {
			String passwdHash = Utilitaire.hashPassword(utilisateur.getPassword());
			utilisateur.setPassword(passwdHash);
			dao.save ( utilisateur);
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create user");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Utilisateur findUtilisateur (String whereClause)   {

		try {
			return (Utilisateur) dao.find (  Utilisateur.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find user");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Utilisateur findUtilisateur (Integer primaryKey)  {
		try {
			return (Utilisateur) dao.find ( new Utilisateur ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find user");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public List<Notification> listNotifications ()   {

		List<Object> objects;
		List<Notification> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Notification ());

			for (Object obj : objects) {
				if (obj instanceof Notification) {
					objs.add ( (Notification) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get notifications");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Notification> listNotifications (String whereClause)  {

		List<Object> objects;
		List<Notification> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Notification.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Notification) {
					objs.add ( (Notification) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get notifications");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public boolean deleteNotificationFeature  (Notification notification, Utilisateur utilisateur, Demande demande)   {
		try {
			notifManager.deleteNotification(notification, utilisateur, demande);
			loadNotificationObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete notification");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateNotification (Notification notification)   {

		try {
			dao.update ( notification);
			loadNotificationObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update notification");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createNotificationFeature (Utilisateur utilisateur, Demande demande, String message)    {

		try {
			notifManager.createNotification(utilisateur, demande, message);
			loadNotificationObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create notification");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Notification findNotification (String whereClause)   {

		try {
			return (Notification) dao.find (  Notification.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find notification");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Notification findNotification (Integer primaryKey)  {
		try {
			return (Notification) dao.find ( new Notification ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find notification");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public List<Role> listRoles ()   {

		List<Object> objects;
		List<Role> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Role ());

			for (Object obj : objects) {
				if (obj instanceof Role) {
					objs.add ( (Role) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get roles");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Role> listRoles (String whereClause)  {

		List<Object> objects;
		List<Role> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Role.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Role) {
					objs.add ( (Role) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get roles");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public boolean deleteRole (Role role)   {
		try {
			dao.delete ( role);
			loadRoleObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete role");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateRole (Role role)   {

		try {
			dao.update ( role);
			loadRoleObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update role");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createRole (Role role)   {

		try {
			dao.save ( role);
			loadRoleObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create role");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Role findRole (String whereClause)   {

		try {
			return (Role) dao.find (  Role.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find role");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Role findRole (Integer primaryKey)  {
		try {
			return (Role) dao.find ( new Role ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find role");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}



	@Override
	public List<Manager> listManagers ()   {

		List<Object> objects;
		List<Manager> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Manager ());

			for (Object obj : objects) {
				if (obj instanceof Manager) {
					objs.add ( (Manager) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get managers");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Manager> listManagers (String whereClause)  {

		List<Object> objects;
		List<Manager> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Manager.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Manager) {
					objs.add ( (Manager) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get managers");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public boolean deleteManager (Manager manager)   {
		try {
			dao.delete ( manager);
			loadManagerObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete manager");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateManager (Manager manager)   {

		try {
			dao.update ( manager);
			loadManagerObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update manager");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createManager (Manager manager)   {

		try {
			String passwdHash = Utilitaire.hashPassword(manager.getPassword());
			manager.setPassword(passwdHash);
			dao.save ( manager);
			loadManagerObservableList ();
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create manager");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Manager findManager (String whereClause)   {

		try {
			return (Manager) dao.find (  Manager.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find manager");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Manager findManager (Integer primaryKey)  {
		try {
			return (Manager) dao.find ( new Manager ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find manager");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public List<Employe> listEmployes ()   {

		List<Object> objects;
		List<Employe> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Employe ());

			for (Object obj : objects) {
				if (obj instanceof Employe) {
					objs.add ( (Employe) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get employes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Employe> listEmployes (String whereClause)  {

		List<Object> objects;
		List<Employe> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Employe.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Employe) {
					objs.add ( (Employe) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get employes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}



		return objs;
	}

	@Override
	public boolean deleteEmploye (Employe employe)   {
		try {

			if ( employe.getService() != null )
				dao.dissociateService ( employe, employe.getService());

			if ( employe.getEmployeProfil() == "ChefService") {
				Service service = findService("WHERE chefService = '"+employe.getId()+"'");
				if(service != null) {
					service.setChefService(null);
					dao.update(service);

				}

			}
			else
				if ( employe.getEmployeProfil() == "ChefDepartement") {
					Departement departement = findDepartement("WHERE chefDepartement = '"+employe.getId()+"'");
					if(departement != null) {
						departement.setChefDepartement(null);;
						dao.update(departement);

					}

				}

			dao.delete ( employe);
			loadEmployesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete employe");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateEmploye (Employe employe)   {

		try {

			if ( employe.getService() != null && employe.getOldService() != employe.getService() && employe.getEmployeProfil()!="ChefService") {
				if ( employe.getOldService() != null)
					dao.dissociateService ( employe, employe.getOldService());
				dao.associateService ( employe, employe.getService());
			}

			dao.update ( employe);
			loadEmployesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update employe");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createEmploye (Employe employe)   {

		try {
			dao.save ( employe);
			loadEmployesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create employe");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Employe findEmploye (String whereClause)   {

		try {
			return (Employe) dao.find (  Employe.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find employe");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Employe findEmploye (Integer primaryKey)  {
		try {
			return (Employe) dao.find ( new Employe ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find employe");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public List<Service> listServices ()   {

		List<Object> objects;
		List<Service> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Service ());

			for (Object obj : objects) {
				if (obj instanceof Service) {
					objs.add ( (Service) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get services");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Service> listServices (String whereClause)  {

		List<Object> objects;
		List<Service> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Service.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Service) {
					objs.add ( (Service) obj);
				}
			}

		} catch (DAOException e) {
			AlertError (e,"get services");
			ErrorLogFileManager.appendError (e.getMessage ());
		}



		return objs;
	}

	@Override
	public boolean deleteService (Service service)   {
		try {

			List<Employe> emps= service.getEmployes();
			while(emps.isEmpty() != true) {
				;
				deleteEmploye(emps.remove(0));

			}

			service.setChefService(null);
			service.setEmployes(emps);


			dao.delete ( service);
			loadServicesObservableList ();

			loadEmployesObservableList ();
			loadManagerObservableList ();
			loadUtilisateursObservableList ();


			return true;
		} catch (DAOException e) {
			AlertError (e,"delete service");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateService (Service service)   {

		try {
			dao.update ( service);
			loadServicesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update service");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createService (Service service)   {

		try {
			dao.save ( service);
			loadServicesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"create service");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Service findService (String whereClause)   {

		try {
			return (Service) dao.find (  Service.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find service");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Service findService (Integer primaryKey)  {
		try {
			return (Service) dao.find ( new Service ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find service");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public List<ChefDepartement> listChefDepartements ()   {

		List<Object> objects;
		List<ChefDepartement> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new ChefDepartement ());

			for (Object obj : objects) {
				if (obj instanceof ChefDepartement) {
					objs.add ( (ChefDepartement) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get chefDepartements");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<ChefDepartement> listChefDepartements (String whereClause)  {

		List<Object> objects;
		List<ChefDepartement> objs = new ArrayList<> ();
		try {
			objects = dao.list ( ChefDepartement.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof ChefDepartement) {
					objs.add ( (ChefDepartement) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get chefDepartements");
			ErrorLogFileManager.appendError (e.getMessage ());
		}



		return objs;
	}

	@Override
	public boolean deleteChefDepartement (ChefDepartement chefDepartement)   {
		try {
			dao.delete ( chefDepartement);
			loadChefDepartementObservableList();
			loadDemandesObservableList();
			loadNoteObservableList();
			loadNotificationObservableList();

			return true;
		} catch (DAOException e) {
			AlertError (e,"delete chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateChefDepartement (ChefDepartement chefDepartement)   {

		try {
			dao.update ( chefDepartement);
			loadChefDepartementObservableList();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createChefDepartement (ChefDepartement chefDepartement)   {

		try {
			String passwdHash = Utilitaire.hashPassword(chefDepartement.getPassword());
			chefDepartement.setPassword(passwdHash);
			dao.save ( chefDepartement);
			loadChefDepartementObservableList ();
			loadUtilisateursObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"cretae chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public ChefDepartement findChefDepartement (String whereClause)   {

		try {
			return (ChefDepartement) dao.find (  ChefDepartement.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public ChefDepartement findChefDepartement (Integer primaryKey)  {
		try {
			return (ChefDepartement) dao.find ( new ChefDepartement ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}
	
	public ChefService findChefService (String whereClause)   {

		try {
			return (ChefService) dao.find (  ChefService.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find ChefService");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	public ChefService findChefService (Integer primaryKey)  {
		try {
			return (ChefService) dao.find ( new ChefService ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public List<Note> listNotes ()    {

		List<Note> objs = new ArrayList<> ();
		List<Object> objects;
		try {
			objects = dao.list ( new Note ());
			for (Object obj : objects) {
				if (obj instanceof Note) {
					objs.add ( (Note) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get notes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Note> listNotes (String whereClause)   {

		List<Object> objects;
		List<Note> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Note.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Note) {
					objs.add ( (Note) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get notes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;

	}

	@Override
	public boolean deleteNote (Note note)   {

		try {
			dao.delete ( note);
			loadNoteObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete note");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}

	@Override
	public boolean updateNote (Note note)   {

		try {
			dao.update ( note);
			loadNoteObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update note");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean createNote (Note note , Demande demande)   {

		try {
			dao.save(note);
			
			// update demande
			demande.addNote(note);
			this.updateDemande(demande);
			
			loadNoteByDemandObservableList(demande);
			return true;
		} catch (DAOException e) {
			AlertError (e,"create note");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public Note findNote (String whereClause)   {

		try {
			return (Note) dao.find ( Note.class, whereClause);
		} catch (DAOException e) {

			AlertError (e,"find note");
			ErrorLogFileManager.appendError (e.getMessage ());

			AlertError(e,"find note");
			ErrorLogFileManager.appendError(e.getStackTrace().toString());

		}
		return null;
	}

	@Override
	public Note findNote (Integer primaryKey)   {
		try {
			return (Note) dao.find ( new Note (), primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find note");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}


	@Override
	public List<Demande> listDemandes ()    {

		List<Demande> objs = new ArrayList<> ();
		List<Object> objects;
		try {
			objects = dao.list ( new Demande ());
			for (Object obj : objects) {
				if (obj instanceof Demande) {
					objs.add ( (Demande) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get demandes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}


		return objs;
	}

	@Override
	public List<Demande> listDemandes (String whereClause)   {

		List<Object> objects;
		List<Demande> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Demande.class, whereClause);

			for (Object obj : objects) {
				if (obj instanceof Demande) {
					objs.add ( (Demande) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get demandes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return objs;

	}

	@Override
	public boolean deleteDemande (Demande demande)   {

		try {



			List<Notification> notifications= demande.getNotifications();

			while(notifications.isEmpty() != true) {
				deleteNotificationFeature(notifications.remove(0), null , demande);

			}
			demande.setNotifications(notifications);



			dao.delete ( demande);
			loadDemandesObservableList ();
			loadNoteObservableList();
			loadNotificationObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"delete demande");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateDemande (Demande demande)   {

		try {
			dao.update ( demande);
			loadDemandesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update demande");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createDemande (Utilisateur utilisateur, Demande demande , Employe employe)   {
		try {
			demande.setEtat("Crée");
			utilisateur.addDemandeCreee(demande);
			
			if (employe != null)
				employe.addDemandeSoumise(demande);
			else
				utilisateur.addDemandeSoumise(demande);
			
			notifManager.createNotification(utilisateur ,demande , "Une demande a été créé par vous !");
		
			loadNotificationObservableList();
			loadDemandeByUtilisateurObservableList(utilisateur);
			loadDemandesObservableList();
			logger.info("Service of user {} " , utilisateur.getService()  );
			
			if (utilisateur.getService() != null) {
				Service service = utilisateur.getService();
				ChefService chef = service.getChefService();
				
				chef.addDemandeSoumise(demande);

				demande.setEtat("soumise");
				notifManager.createNotification(chef , 
						demande ,
						"Une nouvelle demande a été soumise a votre appreciation !");

				

				return true;
			} else 
				return false;
			
		} catch (DAOException e) {
			AlertError (e,"create demande");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}  	
	}

	@Override
	public Demande findDemande (String whereClause)   {
		try {
			return (Demande) dao.find ( Demande.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find demande");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Demande findDemande (Integer primaryKey)   {

		try {
			return (Demande) dao.find ( new Demande (), primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find demande");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}


	@Override
	public List<Notification> listerNotifications ()    {

		List<Notification> objs = new ArrayList<> ();
		List<Object> objects;
		try {
			objects = dao.list ( new Notification());
			for  (Object obj : objects) {
				if  (obj instanceof Notification) {
					objs.add (  (Notification) obj);
				}
			}
		} catch (DAOException e) {
			AlertError(e,"get notificatons");
			ErrorLogFileManager.appendError(e.getMessage());
		}
		return objs;
	}

	@Override
	public List<Notification> listerNotifications (String whereClause)   {

		List<Object> objects;
		List<Notification> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Notification.class, whereClause);
			for  (Object obj : objects) {
				if  (obj instanceof Notification) {
					objs.add (  (Notification) obj);
				}
			}
		} catch (DAOException e) {
			AlertError(e,"get notes");
			ErrorLogFileManager.appendError(e.getMessage());
		}
		return objs;

	}

	@Override
	public boolean supprimerNotification (Notification notification)   {

		try {
			dao.delete ( notification);
			loadNotificationObservableList();
			return true;
		} catch (DAOException e) {
			AlertError(e,"delete notification");
			ErrorLogFileManager.appendError(e.getMessage());
			return false;
		}


	}

	@Override
	public boolean modifierNotification (Notification notification)   {

		try {
			dao.update ( notification);
			loadNotificationObservableList();
			return true;
		} catch (DAOException e) {
			AlertError(e,"update notification");
			ErrorLogFileManager.appendError(e.getMessage());
			return false;
		}

	}

	@Override
	public boolean creerNotification (Notification notification)   {

		try {
			dao.save ( notification); 
			loadNotificationObservableList();
			return true;
		} catch (DAOException e) {
			AlertError(e,"create notification");
			ErrorLogFileManager.appendError(e.getMessage());
			return false;
		}

	}

	@Override
	public Service rechercherNotification (String whereClause)   {

		try {
			return   (Service) dao.find ( Notification.class, whereClause);
		} catch (DAOException e) {
			AlertError(e,"find notification");
			ErrorLogFileManager.appendError(e.getMessage());
		}
		return null;
	}

	@Override
	public Service rechercherNotification (Integer primaryKey)   {
		try {
			return   (Service) dao.find ( new Notification(), primaryKey);
		} catch (DAOException e) {
			AlertError(e,"find notification");
			ErrorLogFileManager.appendError(e.getMessage());
		}
		return null;
	}


	@Override
	public List<Produit> listProduits ()   {

		List<Object> objects;
		List<Produit> objs = new ArrayList<> ();
		try {
			objects = dao.list ( new Produit ());

			for (Object obj : objects) {
				if (obj instanceof Produit) {
					objs.add ( (Produit) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get produits");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return objs;
	}

	@Override
	public List<Produit> listProduits (String whereClause)  {

		List<Object> objects;
		List<Produit> objs = new ArrayList<> ();
		try {
			objects = dao.list ( Produit.class, whereClause);
			for (Object obj : objects) {
				if (obj instanceof Produit) {
					objs.add ( (Produit) obj);
				}
			}
		} catch (DAOException e) {
			AlertError (e,"get produits");
			ErrorLogFileManager.appendError (e.getMessage ());
		}



		return objs;
	}

	@Override
	public boolean deleteProduit (Produit produit)   {
		try {
			dao.delete ( produit);
			loadProduitsObservableList ();
			loadDemandesObservableList();
			loadNoteObservableList();
			loadNotificationObservableList();

			return true;
		} catch (DAOException e) {
			AlertError (e,"delete produit");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public boolean updateProduit (Produit produit)   {

		try {
			dao.update ( produit);
			loadProduitsObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"update produit");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean createProduit (Produit produit)   {

		try {
			dao.save ( produit);
			loadProduitsObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"cretae produit");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}


	}



	@Override
	public Produit findProduit (String whereClause)   {

		try {
			return (Produit) dao.find (  Produit.class, whereClause);
		} catch (DAOException e) {
			AlertError (e,"find produit");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}

	@Override
	public Produit findProduit (Integer primaryKey)  {
		try {
			return (Produit) dao.find ( new Produit ( ) , primaryKey);
		} catch (DAOException e) {
			AlertError (e,"find produit");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return null;
	}



	@Override
	public String getTheDaoImplementationClassname () {
		return null;
	}


	@Override
	public Integer getNumberDemandes () {
		return listDemandes ().size ();
	}
	
	@Override
	public Integer getNumberNotificationUnread (Utilisateur auth) {
		int cmpt = 0;
		List<Notification> notifs =  listNotificationsByUtilisateur(auth);
		for (Notification notification : notifs) {
			if (!notification.isRead())
				cmpt ++;
		}
		return cmpt;
	}

	@Override
	public Integer getNumberRoles() {
		return listRoles ().size ();
	}


	@Override
	public Integer getNumberEmployes () {
		return listEmployes ().size ();
	}

	@Override
	public Integer getNumberDepartements () {
		return listDepartements ().size ();
	}

	@Override
	public Integer getNumberServices () {
		return listServices ().size ();
	}

	@Override
	public Integer getNumberProduits () {
		return listProduits ().size ();
	}

	@Override
	public Integer RetournerNombreNotificationsTotal () {
		return listerNotifications().size();
	}

	@Override
	public ObservableList<Role> loadRoleObservableList ()   {
		try {
			observableListRole.clear ();
			observableListRole.addAll (  dao.list ( new Role () ));
		} catch (DAOException e) {
			AlertError (e,"loading roles");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return observableListRole.getData ();
	}


	@Override
	public ObservableList<Notification> loadNotificationObservableList ()   {
		try {
			observableListNotification.clear ();
			observableListNotification.addAll (  dao.list ( new Notification () ));
		} catch (DAOException e) {
			AlertError (e,"loading notifications");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return observableListNotification.getData ();
	}


	@Override
	public ObservableList<Produit> loadProduitsObservableList ()   {

		try {
			observableListProduit.clear ();
			observableListProduit.addAll (  dao.list ( new Produit () ));
		} catch (DAOException e) {
			AlertError (e,"loading produits");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return observableListProduit.getData ();

	}

	@Override
	public ObservableList<Service> loadServicesObservableList ()   {

		try {
			observableListService.clear ();
			observableListService.addAll (  dao.list ( new Service () ));
		} catch (DAOException e) {
			AlertError (e,"loading services");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return observableListService.getData ();
	}

	@Override
	public ObservableList<Departement> loadDepartementsObservableList ()   {


		try {
			observableListDepartement.clear ();
			observableListDepartement.addAll (  dao.list ( new Departement () ));
		} catch (DAOException e) {
			AlertError (e,"loading departement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return observableListDepartement.getData ();
	}

	@Override
	public ObservableList<Demande> loadDemandesObservableList ()  {

		try {
			observableListDemande.clear ();
			observableListDemande.addAll (  dao.list ( new Demande () ));
		} catch (DAOException e) {
			AlertError (e,"loading demandes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return observableListDemande.getData ();
	}

	@Override
	public ObservableList<Employe> loadEmployesObservableList ()  {

		try {
			observableListEmploye.clear ();
			observableListEmploye.addAll (  dao.list ( new Employe () ));
		} catch (DAOException e) {
			AlertError (e,"loading employes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return observableListEmploye.getData ();
	}


	@Override
	public ObservableList<Demande> loadDemandesByServiceObservableList (Service service)  {
		
		return null;
	}


	@Override
	public ObservableList<Demande> loadDemandesByDirectionObservableList (Object direction)   {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ObservableList<Manager> loadManagerObservableList () {

		try {
			observableListManager.clear ();
			observableListManager.addAll (  dao.list ( new Manager () ));
		} catch (DAOException e) {
			AlertError (e,"loading manages");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return observableListManager.getData ();
	}


	@Override
	public ObservableList<ChefDepartement> loadChefDepartementObservableList () {

		try {
			observableListChefDepartement.clear ();
			observableListChefDepartement.addAll (  dao.list ( new ChefDepartement () ));
		} catch (DAOException e) {
			AlertError (e,"loading chefDepartement");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return observableListChefDepartement.getData ();
	}


	@Override
	public ObservableList<Note> loadNoteObservableList () {

		try {
			observableListNote.clear ();
			observableListNote.addAll (  dao.list ( new Note () ));
		} catch (DAOException e) {
			AlertError (e,"loading notes");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return observableListNote.getData ();
	}

	@Override
	public ObservableList<Utilisateur> loadUtilisateursObservableList () {

		try {
			observableListUtilisateur.clear ();
			observableListUtilisateur.addAll (  dao.list ( new Utilisateur () ));
		} catch (DAOException e) {
			AlertError (e,"loading utilisateurs");
			ErrorLogFileManager.appendError (e.getMessage ());
		}

		return observableListUtilisateur.getData ();
	}



	@Override
	public boolean requestDemande (Demande demande, String reponse)    {
		try {
			dao.requestResponse (demande, reponse);
			return true;
		} catch (DAOException e) {
			AlertError (e,"requesting demande");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}	
	}

	@Override
	public Object validateCredential (String login, String password)   {

		Object obj = new Object ();

		try {
			obj = HibernateDao.getCurrentInstance ().validateCredential ( login,  password);

		} catch (DAOException e) {
			AlertError (e,"validating credentials");
			ErrorLogFileManager.appendError (e.getMessage ());
		}
		return obj;
	}


	@Override
	public boolean associateService (Employe employe, Service service)   {

		try {
			HibernateDao.getCurrentInstance ().associateService ( employe, service) ;
			loadEmployesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"associating service");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}
	}

	@Override
	public boolean associateDepartement(Service service, Departement departement)  {

		try {
			HibernateDao.getCurrentInstance ().associateDepartement(service, departement);
			loadEmployesObservableList ();
			return true;
		} catch (DAOException e) {
			AlertError (e,"associating service to departement");
			ErrorLogFileManager.appendError (e.getMessage ());
			return false;
		}

	}

	@Override
	public ObservableList<Demande> loadDemandeByUtilisateurObservableList(Utilisateur auth) {
		observableListDemandeByUtilisateur.clear ();
		if (
				auth.getEmployeProfil().equals(ChefService.class.getSimpleName()) ||
				auth.getEmployeProfil().equals(ChefDepartement.class.getSimpleName())
				
				) {
			observableListDemandeByUtilisateur.addAllVersion2(auth.getDemandes_soumises());
		} else {
			observableListDemandeByUtilisateur.addAllVersion2(auth.getDemandesCreees());
		}
		

		return observableListDemandeByUtilisateur.getData ();
	}

	@Override
	public Boolean createNote(Utilisateur auth, Note tempNote, Demande demande) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notification> listNotificationsByUtilisateur(Utilisateur auth) {
		return auth.getNotification();
	}

	@Override
	public ObservableList<Employe> loadOnlyEmployesObservableList() {
         
	  List<Employe> employees =	 this.listEmployes("where employeProfil ='Employe'");
	  employees.forEach(employe -> observableListOnlyEmploye.add(employe));
	  
	  return observableListOnlyEmploye.getData();
	}

	@Override
	public ObservableList<Note> loadNoteByDemandObservableList(Demande demande) {
		observableListNoteByDemande.clear ();
		observableListNoteByDemande.addAllVersion2(demande.getNotes());
		return observableListNoteByDemande.getData ();
	}



}