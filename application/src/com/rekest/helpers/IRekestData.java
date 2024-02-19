package com.rekest.helpers;

import com.rekest.entities.employes.Administrateur;

public interface IRekestData {

	/**
	 * Initialize Admin Data
	*/
	public void initAdmins() ;
	
    /**
	 * Initialize Employe Data
	*/
	public void initEmployes() ;
	
	  /**
		 * Initialize Employe Data
		*/
		public void initUtilisateurs() ;
	
	/**
	 * Initialize Gestionnaire Data
	*/
	public void initGestionnaire();
	
	/**
	 * Initialize Manager Data
	*/
	default public void initManagers() {
		this.initChefServices();
		this.initChefDepartement();
		this.initDirection();
		this.initDirecteurGeneral();
	};

	/**
	 * Initialize Chef Service Data
	*/
	public void initChefServices();

	/**
	 * Initialize Chef Department Data
	 */
	public void initChefDepartement();

	/**
	 * Initialize Directeur General Data
	 */
	public void initDirecteurGeneral();

	/**
	 * Initialize Direction Data 
	 */
	public void initDirection();

	/**
	 * Create defaut Admin
	 */
	public Administrateur createDefaultAdmin();

	/**
	 * Initialize all Entities datas
	 */

	/**
	 * Initialize Department Data
	 */
	public void initDepartement();

	
	/**
	 * Initialize Service Data
	 */
	public void initService() ;

	/**
	 * Initialize Role Data
	 */
	public void initRole() ;

	/**
	 * Initialize Produit Data
	 */
	public void initProduit() ;

	/**
	 * Initialize Demande Data
	 */
	public void initDemande();
	
	/**
	 * Initialize all entities
	 */
	default public void initAllEntity() {
		initDepartement();
		initService();
		initEmployes();
		initAdmins();
		initManagers();
		initRole();
		initProduit();
		initGestionnaire();
		initDemande();
	}

}
