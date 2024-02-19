package com.rekest.controllers.impl;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.rekest.entities.Produit;
import com.rekest.utils.Utilitaire;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProduitEditDialogController {

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnOk;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private Label labelNomFenetre;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldPrix;

    @FXML
    private TextField textFieldQuantite;
    
    private List<String> types = Utilitaire.getProduitTypes();

    private Stage dialogStage;
    
	private Produit produit;
	
	private boolean okClicked = false;

	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage, String title) {
		this.dialogStage = dialogStage;
		labelNomFenetre.setText(title);
	}

    public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
		textFieldNom.setText(produit.getNom());
		textAreaDescription.setText(produit.getDescription());
		textFieldPrix.setText(String.valueOf(produit.getPrix()));
		textFieldQuantite.setText(String.valueOf(produit.getQuantite()));
		
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes() {
		comboBoxType.setItems(FXCollections.observableArrayList(getTypes()));
		
		if(produit!=null && produit.getType()!=null)
			comboBoxType.setValue(produit.getType());
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
			produit.setNom(textFieldNom.getText());
			produit.setDescription(textAreaDescription.getText());
			produit.setPrix(Double.parseDouble(textFieldPrix.getText()));
			produit.setQuantite(Integer.parseInt(textFieldQuantite.getText()));
			produit.setType(comboBoxType.getValue());
			
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
			errorMessage += "Le nom du produit est invalid!\n";
		}
		
		if (textAreaDescription.getText() == null || textAreaDescription.getText().length() == 0) {
			errorMessage += "La description du produit est invalid!\n";
		}
		
		if (textFieldPrix.getText() == null || textFieldPrix.getText().length() == 0 
				|| !NumberUtils.isParsable(textFieldPrix.getText()) || Double.parseDouble(textFieldPrix.getText()) <=0 ) {
			errorMessage += "Le prix du produit est invalid!\n";
		}
		
		if (textFieldQuantite.getText() == null || textFieldQuantite.getText().length() == 0 
				|| !NumberUtils.isParsable(textFieldQuantite.getText()) || Integer.parseInt(textFieldQuantite.getText()) <=0) {
			errorMessage += "La quantite du produit est invalid!\n";
		}
		
		if (comboBoxType.getValue() == null || comboBoxType.getValue().length() == 0) {
			errorMessage += "Le type du produit est invalid!\n";
			
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

