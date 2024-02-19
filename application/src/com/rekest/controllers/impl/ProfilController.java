package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.Directeur;
import com.rekest.entities.employes.DirecteurGeneral;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.enums.NotificationType;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;
import com.rekest.utils.Utilitaire;
import com.rekest.utils.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ProfilController implements Initializable {

	/**
	 * Loggers
	 */
	public final static Logger logger = LogManager.getLogger(ProfilController.class);
	
	private Stage primaryStage;

	@FXML
	private Button btnSauveegarder;

	@FXML
	private TextField textFieldAdresse;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private TextField textFieldNom;

	@FXML
	private TextField textFieldPrenom;
	
	@FXML
	private TextField textFieldService;

	@FXML
	private TextField textFieldTelephone;

	private Utilisateur connectedUser;
	
	private IFeature feature = Feature.getCurrentInstance();

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void setConnectedUser(Utilisateur connectedUser) {
		this.connectedUser = connectedUser;
		initUserData();
	}

	public ProfilController() {
		logger.info("Creation du profil controller avec utilisateur {} ");
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("All composants is charged");
	}

	private void initUserData() {
		if (connectedUser != null) {
			Utilitaire.setTextField(textFieldNom,  connectedUser.getNom());
			Utilitaire.setTextField(textFieldPrenom, connectedUser.getPrenom());
			Utilitaire.setTextField(textFieldAdresse, connectedUser.getAdresse());
			Utilitaire.setTextField(textFieldTelephone, connectedUser.getTelephone());
			Utilitaire.setTextField(textFieldEmail, connectedUser.getEmail());
			
			if (connectedUser.equals(Directeur.class.getSimpleName())
					|| connectedUser.equals(DirecteurGeneral.class.getSimpleName()) 
					|| connectedUser.equals(ChefDepartement.class.getSimpleName())		
						) {
				textFieldService.setVisible(false);
			} else {
				Utilitaire.setTextField(textFieldService, connectedUser.getService() != null ? connectedUser.getServiceName() : "Aucun service");
			}
			
			
		} else {
			logger.info("nULL USEERDARA");
		}
	}


	@FXML
	void handleClickedSauvegarder(ActionEvent event) {
		
		if (isInputValid()) {
			this.connectedUser.setNom(textFieldNom.getText());
			this.connectedUser.setPrenom(textFieldPrenom.getText());
			this.connectedUser.setAdresse(textFieldAdresse.getText());
			this.connectedUser.setTelephone(textFieldTelephone.getText());
			this.connectedUser.setEmail(textFieldEmail.getText());
			
			
			Boolean result = feature.updateUtilisateur(connectedUser);
			
			if (result)
				Utilitaire.notification(NotificationType.INFO, "Update profile", 
						"Votre profil a été modifié avec success !");
		}
	}
	
	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (textFieldNom.getText() == null || textFieldNom.getText().length() == 0) {
			errorMessage += "Le nom de l'employe est invalide!\n";
		}

		if (textFieldPrenom.getText() == null || textFieldPrenom.getText().length() == 0) {
			errorMessage += "Le prenom de l'employe est invalide!\n";
		}

		if (textFieldEmail.getText() == null || textFieldEmail.getText().length() == 0) {
			errorMessage += "L'email de l'employe est invalide!\n";
		}

		if (textFieldAdresse.getText() == null || textFieldAdresse.getText().length() == 0) {
			errorMessage += "L'adresse de l'employe est invalide!\n";
		}

		if (textFieldTelephone.getText() == null || textFieldTelephone.getText().length() == 0) {
			errorMessage += "Le telephone de l'employe est invalide!\n";
		}
		

		if (!Validator.validateText(errorMessage)) {
			return true;
		} else {
			Utilitaire.alert(AlertType.WARNING, 
					primaryStage, 
					"Invalid Fields", 
					"Please correct invalid fields", 
					errorMessage);

			return false;
		}
	}
}
