package com.rekest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Alert.AlertType;

public class PropertyManager {
	
	public final static Logger logger = LogManager.getLogger(PropertyManager.class);
	
	private static Properties properties = new Properties();
	private static PropertyManager instance  = new PropertyManager();
	
	private PropertyManager() {
		logger.info("An instance of PropertyManger was created !");
	}
	
	public static PropertyManager getInstance() {
		return instance;
	}
	
	public String getApplicationIcon() {
		return this.getProperties("application.icon");
	}
	
	public String getApplicationBrand() {
		return this.getProperties("application.brand");
	}
	
	public String getApplicationName() {
		return this.getProperties("application.name");
	}
	
	public String getApplicationPathViews() {
		return this.getProperties("application.pathViews");
	}
	
	public String getApplicationLoginError() {
		return this.getProperties("application.error.login");
	}
	
	public String getApplicationPasswordError() {
		return this.getProperties("application.error.password");
	}
	
	public String getApplicationErrorFields() {
		return this.getProperties("application.error.fields.message");
	}
	
	public String getApplicationErrorFieldHeader() {
		return this.getProperties("application.error.fields.header");
	}
	
	public String getApplicationAdminFirstname() {
		return this.getProperties("application.admin.firstname");
	}
	
	public String getApplicationAdminLastname() {
		return this.getProperties("application.admin.lastname");
	}
	
	public String getApplicationAdminPhone() {
		return this.getProperties("application.admin.phone");
	}
	
	public String getApplicationAdminEmail() {
		return this.getProperties("application.admin.email");
	}
	
	public String getApplicationAdminAdresse() {
		return this.getProperties("application.admin.address");
	}
	
	public String getApplicationAdminLogin() {
		return this.getProperties("application.admin.login");
	}
	
	public String getApplicationAdminPassword() {
		return this.getProperties("application.entity.admin.password");
	}
	
	public String getApplicationUserProfilAdmin() {
		return this.getProperties("application.user.profile.admin");
	}
	
	public String getApplicationUserProfilSimple() {
		return this.getProperties("application.user.profile.simple");
	}
	
	public String getApplicationUserProfilDirecteur() {
		return this.getProperties("application.user.profile.directeur");
	}
	
	
	public String getApplicationUserProfilChefService() {
		return this.getProperties("application.user.profile.chefService");
	}
	
	
	public String getApplicationUserProfilGestionnaire() {
		return this.getProperties("application.user.profile.gestionnaire");
	}
	
	
	/**
	 * Get property from file application.property
	 * @param property Property string to get
	 * @return String 
	 */
	public String getProperties(String property) {
        try {            
            properties.load(new FileInputStream("application.properties"));
            String propertyFound = (String) properties.get(property);
            logger.info("Property {} was founded and this value is #{}#" , property , propertyFound);
            return propertyFound;

        } catch (IOException ioe) {
            Utilitaire.alert(AlertType.ERROR, null,
                "Error", ioe.getClass() +
                "Error while loading Property file",
                ioe.getMessage());
            logger.error("Property {} not found {}" , property, ioe.getMessage());
            return null;
        }
		
	}
	
	
}
