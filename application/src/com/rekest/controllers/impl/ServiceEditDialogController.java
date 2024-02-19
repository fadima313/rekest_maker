package com.rekest.controllers.impl;

import java.util.ArrayList;
import java.util.List;

import com.rekest.entities.Departement;
import com.rekest.entities.Service;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServiceEditDialogController {

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnOk;

    @FXML
    private ComboBox<String> comboBoxDepartement;

    @FXML
    private Label labelService;

    @FXML
    private TextField textFieldNom;
    
	private ObservableList<Departement> departmentsList;
	
	private IFeature feature = Feature.getCurrentInstance();
    
	private Stage dialogStage;
	
	private Service service;
	
	private boolean okClicked = false;
	

	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, String title) {
		this.dialogStage = dialogStage;
		labelService.setText(title);
	}

	public Service getService() {
		return service;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 *
	 * @param person
	 */
	public void setService(Service service) {
		this.service = service;
		textFieldNom.setText(service.getNom());
	}
	
	public void setDepartements() {
		setDepartmentsList(feature.loadDepartementsObservableList());

		comboBoxDepartement.setItems(FXCollections.observableArrayList(serialize(getDepartmentsList())));

		if(this.service!=null && this.service.getDepartementString()!=null) 
			comboBoxDepartement.setValue(this.service.getDepartementString());
		
	}

	public ObservableList<Departement> getDepartmentsList() {
		return departmentsList;
	}

	public void setDepartmentsList(ObservableList<Departement> departmentsList) {
		this.departmentsList = departmentsList;
	}
	
	public List<String> serialize(ObservableList<Departement> departmentList){

		List<String> strings = new ArrayList<>();

		for (Departement department : departmentList) {
			strings.add(department.getId() + " - " + department.getNom());
		}

		return strings;
	}
	
	public Departement getCurrentComboBoxDepartement() {
		Departement tempDepartment = null ;
		for (Departement department : getDepartmentsList()) {
			if ((department.getId() + " - " + department.getNom()).equals(comboBoxDepartement.getValue()))
				tempDepartment =  department;
		}
		return tempDepartment;
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
	 * Called when the service clicks ok.
	 */
	@FXML
	private void handleClickedOk() {
		if (isInputValid()) {
			service.setNom(textFieldNom.getText());
			service.setDepartement(getCurrentComboBoxDepartement());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleClickedAnnuler() {
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
			errorMessage += "Le nom du service est invalid!\n";
		}
		
		if (comboBoxDepartement.getValue() == null || comboBoxDepartement.getValue().length() == 0) {
			errorMessage += "Le nom du departement est invalid!\n";
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


}

