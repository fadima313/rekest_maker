package com.rekest.controllers.impl;

import java.util.ArrayList;
import java.util.List;

import com.rekest.entities.Service;
import com.rekest.entities.employes.Employe;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;
import com.rekest.utils.Utilitaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class EmployeEditDialogController {

	@FXML
	private Button btnAnnuler;

	@FXML
	private Button btnOk;

	@FXML
	private Circle circlePhoto;

	@FXML
	private ComboBox<String> comboBoxProfil;

	@FXML
	private ComboBox<String> comboBoxService;
	
	@FXML
	private TextField textFieldLogin;
	
	@FXML
	private TextField textFieldPassword;
	
	@FXML
	private Label labelPassword;
	
	@FXML
	private Label labelLogin;
	
	@FXML
	private Label labelDepartement;
	
	@FXML
	private ComboBox<String> comboBoxDepartement;

	@FXML
	private Label labelFenetre;

	@FXML
	private TextField textFieldAdresse;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private TextField textFieldNom;

	@FXML
	private TextField textFieldPrenom;

	@FXML
	private TextField textFieldTelephone;

	private ObservableList<Service> servicesList ;

	private List<String> profilesList = Utilitaire.getProfiles();

	private Stage dialogStage;

	private IFeature feature = Feature.getCurrentInstance();

	private Employe employe;
	
	private boolean okClicked = false;


	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, String title) {
		this.dialogStage = dialogStage;
		labelFenetre.setText(title);

	}
	

	/**
	 * Sets the employe to be edited in the dialog.
	 *
	 * @param employe
	 */
	public void setEmploye(Employe employe) {
		this.employe = employe;
		textFieldNom.setText(employe.getNom());
		textFieldPrenom.setText(employe.getPrenom());
		textFieldAdresse.setText(employe.getAdresse());
		textFieldEmail.setText(employe.getEmail());
		textFieldTelephone.setText(employe.getTelephone());
		this.employe.setOldService(employe.getService());
	}

	public Employe getEmploye() {
		return employe;
	}

	/**
	 * Initialize the value of combox box service
	 * 
	 */
	
	public void setServices(){

		this.setServicesList(feature.loadServicesObservableList());

		comboBoxService.setItems(FXCollections.observableArrayList(serialize(getServicesList())));

		if(this.employe!=null && this.employe.getServiceString()!=null) 
			comboBoxService.setValue(this.employe.getServiceString());

	}

	/**
	 * Initialize the value of combox box profil
	 * 
	 */
	public void setProfiles(){

		comboBoxProfil.setItems(FXCollections.observableArrayList(getProfilesList()));

		if(this.employe!=null && this.employe.getEmployeProfil()!=null) 
			comboBoxProfil.setValue(this.employe.getEmployeProfil());

	}
	

	/**
	 * Returns true if the employe clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the employe clicks ok.
	 */
	@FXML
	private void handleClickedOk() {
		if (isInputValid()) {
			employe.setNom(textFieldNom.getText());
			employe.setPrenom(textFieldPrenom.getText());
			employe.setAdresse(textFieldAdresse.getText());
			employe.setEmail(textFieldEmail.getText());
			employe.setTelephone(textFieldTelephone.getText());
			employe.setService(getCurrentComboBoxService());
			
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleClickedAnnuler() {
		reset();
		dialogStage.close();
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
		
//		if (comboBoxService.getValue() == null || comboBoxService.getValue().length() == 0) {
//			errorMessage += "Le service de l'employe est invalide!\n";
//		}


		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			com.rekest.utils.Utilitaire.alert(AlertType.WARNING, 
					dialogStage, 
					"Invalid Fields", 
					"Please correct invalid fields", 
					errorMessage);

			return false;
		}
	}

	public ObservableList<Service> getServicesList() {
		return servicesList;
	}

	public void setServicesList(ObservableList<Service> servicesList) {
		this.servicesList = servicesList;
	}

	public List<String> getProfilesList() {
		return profilesList;
	}

	/**
	 * Serialize a observablelist of services 
	 * @param service list
	 * @return list of strings with service id and name only
	 */
	public List<String> serialize(ObservableList<Service> serviceList){

		List<String> strings = new ArrayList<>();


		for (Service service : serviceList) {
			strings.add(service.getId() + " - " + service.getNom());
		}

		return strings;
	}
	
	public Service getCurrentComboBoxService() {
		Service tempService = null ;
		for (Service service : getServicesList()) {
			if ((service.getId() + " - " + service.getNom()).equals(comboBoxService.getValue()))
				tempService =  service;
		}
		return tempService;
	}

	public void reset() {
		this.servicesList = null;
		comboBoxService.setItems(null);
		comboBoxProfil.setItems(null);
		textFieldNom.clear();
		textFieldPrenom.clear();
		textFieldAdresse.clear();
		textFieldTelephone.clear();
		textFieldEmail.clear();

	}


}

