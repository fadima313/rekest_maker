package com.rekest.controllers.impl;

import java.util.ArrayList;
import java.util.List;

import com.rekest.entities.Departement;
import com.rekest.entities.Service;
import com.rekest.entities.employes.Utilisateur;
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

public class UtilisateurEditDialogController {

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
	private Label labelService;

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

	private ObservableList<Departement> departementsList ;

	private List<String> profilesList = Utilitaire.getProfiles();

	private Stage dialogStage;

	private IFeature feature = Feature.getCurrentInstance();

	private Utilisateur utilisateur;

	private boolean okClicked = false;


	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, String title) {
		this.dialogStage = dialogStage;
		labelFenetre.setText(title);

	}

	public void desactivateFields(Boolean state) {
		labelLogin.setVisible(state);
		labelPassword.setVisible(state);
		textFieldLogin.setVisible(state);
		textFieldPassword.setVisible(state);
	}

	private void setStateDepartement(Boolean state) {
		labelDepartement.setVisible(state);
		comboBoxDepartement.setVisible(state);
	}

	private void setStateService(Boolean state) {
		labelService.setVisible(state);
		comboBoxService.setVisible(state);

	}

	public void addListeners() {
		comboBoxProfil.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue)-> {
					setProperComboByProfil(newValue);
				});
	}


	private void setProperComboByProfil(String profil) {
		if (profil.equals("Employe"))
		{
			desactivateFields(false);
		}
		else {
			desactivateFields(true);

		}

		if(profil.equals("ChefService"))
		{
			setStateDepartement(false);
			setStateService(true);
			getServicetWithoutChief();
		}
		else if (profil.equals("ChefDepartement")) {
			setStateService(false);
			setStateDepartement(true);
			setDepartementsForUser();
		}
		else {
			setStateDepartement(false);
			setStateService(true);

		}
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
		textFieldNom.setText(utilisateur.getNom());
		textFieldPrenom.setText(utilisateur.getPrenom());
		textFieldAdresse.setText(utilisateur.getAdresse());
		textFieldEmail.setText(utilisateur.getEmail());
		textFieldTelephone.setText(utilisateur.getTelephone());
		textFieldLogin.setText(utilisateur.getLogin());
		textFieldPassword.setText(utilisateur.getPassword());
		this.utilisateur.setOldService(utilisateur.getService());
	}

	public void setLoginAndPassword() {
		textFieldLogin.setText(null);
		textFieldPassword.setText(null);
	}

	/**
	 * Initialize the value of combox box service
	 * 
	 */
	public void setServicesForUser(){

		this.setServicesList(feature.loadServicesObservableList());

		comboBoxService.setItems(FXCollections.observableArrayList(serialize(getServicesList())));

		if(this.utilisateur!=null && this.utilisateur.getServiceString()!=null) 
			comboBoxService.setValue(this.utilisateur.getServiceString());

	}

	public void setDepartementsForUser() {

//		this.setDepartementsList(feature.loadDepartementsObservableList());
		getDepartementWithoutChief();

		comboBoxDepartement.setItems(FXCollections.observableArrayList(serializeDepartements(getDepartementsList())));

		if(this.utilisateur!=null && this.utilisateur.getEmployeProfil()!=null && this.utilisateur.getEmployeProfil().equals("ChefDepartement"))
		{
			Departement tempDepartement = feature.findDepartement("WHERE chefDepartement='"+utilisateur.getId()+"'");
			if(tempDepartement != null)
				comboBoxDepartement.setValue(tempDepartement.getNom());

		}
	}

	public void setProfilesForUser(){

		comboBoxProfil.setItems(FXCollections.observableArrayList(getProfilesList()));

		if(this.utilisateur!=null && this.utilisateur.getEmployeProfil()!=null) 
			comboBoxProfil.setValue(this.utilisateur.getEmployeProfil());

	}

	public void getDepartementWithoutChief() {
		this.setDepartementsList(FXCollections.observableArrayList(feature.listDepartements("WHERE chefDepartement is null")));
	}

	public void getServicetWithoutChief() {
		this.setServicesList(FXCollections.observableArrayList(feature.listServices("WHERE chefService is null")));
		
		comboBoxService.setItems(FXCollections.observableArrayList(serialize(getServicesList())));

		if(this.utilisateur!=null && this.utilisateur.getServiceString()!=null) 
			comboBoxService.setValue(this.utilisateur.getServiceString());

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
			utilisateur.setNom(textFieldNom.getText());
			utilisateur.setPrenom(textFieldPrenom.getText());
			utilisateur.setAdresse(textFieldAdresse.getText());
			utilisateur.setEmail(textFieldEmail.getText());
			utilisateur.setTelephone(textFieldTelephone.getText());
			utilisateur.setService(getCurrentComboBoxService());
			utilisateur.setLogin(textFieldLogin.getText());
			utilisateur.setPassword(textFieldPassword.getText());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleClickedAnnuler() {
		//		reset();
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
			errorMessage += "Le nom de l'utilisateur est invalide!\n";
		}

		if (textFieldPrenom.getText() == null || textFieldPrenom.getText().length() == 0) {
			errorMessage += "Le prenom de l'utilisateur est invalide!\n";
		}

		if (textFieldEmail.getText() == null || textFieldEmail.getText().length() == 0) {
			errorMessage += "L'email de l'utilisateur est invalide!\n";
		}

		if (textFieldAdresse.getText() == null || textFieldAdresse.getText().length() == 0) {
			errorMessage += "L'adresse de l'utilisateur est invalide!\n";
		}

		if (textFieldTelephone.getText() == null || textFieldTelephone.getText().length() == 0) {
			errorMessage += "Le telephone de l'utilisateur est invalide!\n";
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

	public ObservableList<Departement> getDepartementsList() {
		return departementsList;
	}

	public void setDepartementsList(ObservableList<Departement> departementsList) {
		this.departementsList = departementsList;
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

	public List<String> serializeDepartements(ObservableList<Departement> departementList){

		List<String> strings = new ArrayList<>();


		for (Departement departement : departementList) {
			strings.add(departement.getId() + " - " + departement.getNom());
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

	public String getCurrentComboBoxProfil() {

		return comboBoxProfil.getValue();
	}

	public Departement getCurrentComboBoxDepartement() {
		Departement tempDepartement = null ;
		for (Departement departement : getDepartementsList()) {
			if ((departement.getId() + " - " + departement.getNom()).equals(comboBoxDepartement.getValue()))
				tempDepartement =  departement;
		}
		return tempDepartement;
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

