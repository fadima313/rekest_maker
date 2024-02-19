package com.rekest.controllers.impl;


import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.Demande;
import com.rekest.entities.Note;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NoteEditController implements Initializable {

	
	@FXML private TextArea textAreaMessage;

	private Stage dialogStage;
	private boolean okClicked = false;
	private Note note;
	private Demande demande;

	public void setNote(Note note) {
		this.note = note;
		textAreaMessage.setText(note.getMessage());
	}
	
	public void setDemande(Demande demande) {
		this.demande = demande;
	}

	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}


	@FXML
	void handleClickedAnnuler(ActionEvent event) {
		dialogStage.close();
	}

	@FXML
	void handleClickedOK(ActionEvent event) {
		if (isInputValid()) {
			note.setMessage(textAreaMessage.getText());
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (textAreaMessage.getText() == null || textAreaMessage.getText().length() == 0) {
			errorMessage += "Le messsage est invalid!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			com.rekest.utils.Utilitaire.alert(AlertType.WARNING, 
					dialogStage, 
					"Invalid Field", 
					"Please correct invalid field", 
					errorMessage);

			return false;
		}
	}

}