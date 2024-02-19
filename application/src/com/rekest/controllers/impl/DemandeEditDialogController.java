package com.rekest.controllers.impl;


import java.util.ArrayList;
import java.util.List;

import com.rekest.entities.Demande;
import com.rekest.entities.Produit;
import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DemandeEditDialogController {

	/**
	 * Composants FXML
	 */
	@FXML private Button btnAnnuler;
	@FXML private Button btnSoumettre;
	@FXML private ComboBox<String> comboBoxEmploye;
	@FXML private ComboBox<String> comboBoxProduit;
	@FXML private ComboBox<String> comboBoxRecepteur;

	/**
	 * Stage et les attributs
	 */
	private Stage dialogStage;
	private Demande demande;
	private Utilisateur auth;
	private boolean okClicked = false;

	/**
	 * Features
	 */
	private IFeature feature = Feature.getCurrentInstance();

	/**
	 * ObservableList
	 */
	private ObservableList<Produit> produitList ;
	private ObservableList<Employe> employeList ;
	private ObservableList<Utilisateur> userList ;

	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the department to be edited in the dialog.
	 *
	 * @param department
	 */
	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	
	/**
	 * Set auth user
	 * @param auth
	 */
	public void setAuth(Utilisateur auth) {
		this.auth = auth;
	}
	
	@FXML
	void handleClickedRejecter(ActionEvent event) {
		dialogStage.close();
	}

	@FXML
	void handleClickedSoumettre(ActionEvent event) {
		if (isInputValid()) {
			demande.setProduit(getCurrentComboBoxProduit());
			demande.setEmploye(getCurrentComboBoxEmploye());
			demande.setUtilisateur(auth);
			okClicked = true;
			dialogStage.close();
		}

	}

	/**
	 * Initialize the value of combox box produits
	 * 
	 */
	public void setProduits(){

		this.setProduitList(feature.loadProduitsObservableList());

		comboBoxProduit.setItems(FXCollections.observableArrayList(this.serializeProduits(produitList)));

		if(this.demande!=null && this.demande.getProduitString()!=null) {
			comboBoxProduit.setValue(this.demande.getProduitString());
		}

	}

	/**
	 * Initialize the value of combox box employees
	 * 
	 */
	public void setEmployes(){
		
		if (employeList == null) {
			this.setEmployeList(feature.loadOnlyEmployesObservableList());
			comboBoxEmploye.setItems(FXCollections.observableArrayList(this.serializeEmployes(employeList)));

			if(this.demande!=null && this.demande.getEmployeString()!=null) {
				comboBoxEmploye.setValue(this.demande.getEmployeString());
			}
			
		}
	}


	/**
	 * Serialize a observablelist of produits 
	 * @param service list
	 * @return list of strings with service id and name only
	 */
	public List<String> serializeProduits(ObservableList<Produit> produitList){

		List<String> strings = new ArrayList<>();

		for (Produit produit : produitList) {
			strings.add(produit.getId() + " - " + produit.getNom());
		}

		return strings;
	}

	/**
	 * Serialize a observablelist of employees 
	 * @param service list
	 * @return list of strings with employe id and name only
	 */
	public List<String> serializeEmployes(ObservableList<Employe> employeList){

		List<String> strings = new ArrayList<>();

		for (Employe employe : employeList) {
			strings.add(employe.getId() + " - " + employe.getFullName());
		}

		return strings;
	}

	/**
	 * Serialize a observablelist of employees 
	 * @param service list
	 * @return list of strings with employe id and name only
	 */
	public List<String> serializeUsers(ObservableList<Utilisateur> userList){

		List<String> strings = new ArrayList<>();

		for (Utilisateur user : userList) {
			strings.add(user.getId() + " - " + user.getFullName());
		}

		return strings;
	}

	public ObservableList<Produit> getProduitList() {
		return produitList;
	}

	public ObservableList<Employe> getEmployeList() {
		return employeList;
	}

	public ObservableList<Utilisateur> getUserList() {
		return userList;
	}

	public void setProduitList(ObservableList<Produit> produitList) {
		this.produitList = produitList;
	}

	public void setEmployeList(ObservableList<Employe> employeList) {
		this.employeList = employeList;
	}


	public void setUserList(ObservableList<Utilisateur> userList) {
		this.userList = userList;
	}
	
	
	public Produit getCurrentComboBoxProduit() {
		Produit tempProduit = null ;
		for (Produit produit : getProduitList()) {
			if ((produit.getId() + " - " + produit.getNom()).equals(comboBoxProduit.getValue()))
				tempProduit =  produit;
		}
		return tempProduit;
	}
	
	public Utilisateur getCurrentComboBoxUser() {
		Utilisateur tempUtilisateur = null ;
		for (Utilisateur utilisateur : getUserList()) {
			if ((utilisateur.getId() + " - " + utilisateur.getFullName()).equals(comboBoxRecepteur.getValue()))
				tempUtilisateur =  utilisateur;
		}
		return tempUtilisateur;
	}
	
	public Employe getCurrentComboBoxEmploye() {
		Employe tempEmploye = null ;
		for (Employe employe : getEmployeList()) {
			if ((employe.getId() + " - " + employe.getFullName()).equals(comboBoxEmploye.getValue()))
				tempEmploye =  employe;
		}
		return tempEmploye;
	}

	/**
	 * Returns true if the service clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		/*if (comboBoxEmploye.getValue() == null || comboBoxEmploye.getValue().length() == 0) {
			errorMessage += "L'employé est invalide!\n";
		}*/

		if (comboBoxProduit.getValue() == null || comboBoxProduit.getValue().length() == 0) {
			errorMessage += "Le produit est invalide!\n";
		}

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

	public void setNotes() {
		
	}
	
	
}
