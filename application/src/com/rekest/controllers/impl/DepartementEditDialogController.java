package com.rekest.controllers.impl;

import com.rekest.entities.Departement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DepartementEditDialogController {

	@FXML
	private Label labelDepartement;
	
	@FXML
	private TextField textFieldNom;

	private Stage dialogStage;
	private Departement departement;
	private boolean okClicked = false;

	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, String title) {
		this.dialogStage = dialogStage;
		labelDepartement.setText(title);
	}

	/**
	 * Sets the department to be edited in the dialog.
	 *
	 * @param department
	 */
	public void setDepartement(Departement departement) {
		this.departement = departement;
		textFieldNom.setText(departement.getNom());
	}

	public Departement getDepartement() {
		return departement;
	}

	/**
	 * Returns true if the department clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the department clicks ok.
	 */
	@FXML
	private void handleClickedOk() {
		if (isInputValid()) {
			departement.setNom(textFieldNom.getText());
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleClicledAnnuler() {
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

