package com.rekest.controllers.impl;

import com.rekest.entities.Role;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoleEditDialogController {

	@FXML
	private Button btnAnnuler;

	@FXML
	private Button btnOk;

	@FXML
	private Label labelRole;

	@FXML
	private TextField textFieldNom;

	private Stage dialogStage;

	private Role role;

	private boolean okClicked = false;

	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, String title) {
		this.dialogStage = dialogStage;
		labelRole.setText(title);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
		textFieldNom.setText(role.getIntitule());
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
			role.setIntitule(textFieldNom.getText());
		
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
			errorMessage += "L'intitule du role est invalid!\n";
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

