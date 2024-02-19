package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.controllers.IController;
import com.rekest.entities.employes.Administrateur;
import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.ChefService;
import com.rekest.entities.employes.Directeur;
import com.rekest.entities.employes.DirecteurGeneral;
import com.rekest.entities.employes.Gestionnaire;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.enums.NotificationType;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;

import com.rekest.utils.PropertyManager;
import com.rekest.utils.Utilitaire;
import com.rekest.utils.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthenticationController implements Initializable , IController {

	public final static Logger logger = LogManager.getLogger(AuthenticationController.class);

	@FXML
	private TextField txtLogin;

	@FXML
	private PasswordField txtPassword;

	private IFeature service = Feature.getCurrentInstance();

	private Stage primaryStage;

	private MainController mainController;

	private Utilisateur connectedUser;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(false);
	}

	public void setConnectedUser(Utilisateur utilisateur) {
		logger.info(utilisateur);
		this.connectedUser = utilisateur;
	}

	@FXML
	void handleClickedConnexion(ActionEvent event) {
		if (isInputValid()) {
			String hash = Utilitaire.hashPassword(Utilitaire.getTextField(txtPassword));
			Utilisateur user = (Utilisateur) service.validateCredential(
					Utilitaire.getTextField(txtLogin), 
					hash);

			if (user!= null) {
				logger.info("{} connecté avec success", user.getEmployeProfil());
				clearField();
				this.setConnectedUser(user);
				this.primaryStage.hide();
				connectUserToSpace();
				//Utilitaire.notification(NotificationType.INFO, "Bienvenue dans le votre ", "Vous etes connectï¿½ avec success !");
			} else {
				Utilitaire.notification(NotificationType.ERROR, 
						"Echec de connexion", "Informations incorrects");
			}
		}
	}
	
	@FXML
	void handleClickedRetour(ActionEvent event) {
		mainController.initApplication(primaryStage);
	}

	/**
	 * Connect a user to this space according user profil
	 */
	private void connectUserToSpace() {

		if (connectedUser == null) {
			Utilitaire.alert(AlertType.ERROR, 
					primaryStage, 
					"Get user Data Error", 
					"User Data", 
					"Error when Application try get user connected data !");
		} else {
			
			String profil = connectedUser.getEmployeProfil();

			logger.info("Employe profil is {}" , profil );

			try {
				if (profil.equals(Administrateur.class.getSimpleName())) {
					mainController.initAdminRootLayout(connectedUser);
				} 
				if (profil.equals(Utilisateur.class.getSimpleName())) {
					mainController.initUtilisateurRootLayout(connectedUser);
				} 

				if (
						profil.equals(ChefService.class.getSimpleName()) 
						||
						profil.equals(ChefDepartement.class.getSimpleName()) ||
						profil.equals(Directeur.class.getSimpleName()) ||
						profil.equals(DirecteurGeneral.class.getSimpleName())||
						profil.equals(ChefDepartement.class.getSimpleName())
						) {
					   mainController.initManagerRootLayout(connectedUser);
				}

				if (profil.equals(Gestionnaire.class.getSimpleName())) {
					mainController.initGestionnaireRootLayout(connectedUser);
				} 

			} catch (Exception e) {
				e.printStackTrace();
				Utilitaire.notification(NotificationType.ERROR, "Error", e.getMessage());
			}
		}

	}

	@FXML
	void handleClickedPasswordForget(ActionEvent event) {}

	@FXML
	void handleClickedQuitter(ActionEvent event) {
		System.exit(0);
	}

	public AuthenticationController() {
		logger.info("Instance of {} is created" , this.getClass().getSimpleName());
		mainController = MainController.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {}


	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (!Validator.validateText(Utilitaire.getTextField(txtLogin)))
			errorMessage += PropertyManager.getInstance().getApplicationLoginError();

		if (!Validator.validateText(Utilitaire.getTextField(txtPassword))) 
			errorMessage += PropertyManager.getInstance().getApplicationPasswordError();

		if (!Validator.validateText(errorMessage)) {
			return true;
		} else {
			Utilitaire.alert(AlertType.WARNING, 
					primaryStage, 
					PropertyManager.getInstance().getApplicationErrorFieldHeader(), 
					PropertyManager.getInstance().getApplicationErrorFields(), 
					errorMessage);

			return false;
		}
	}

	private void clearField() {
		Utilitaire.clear(txtLogin , txtPassword);
	}

}