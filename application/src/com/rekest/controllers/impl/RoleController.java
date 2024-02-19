package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.Role;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;
import com.rekest.utils.Utilitaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RoleController implements Initializable {

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnModifier;

	@FXML
	private Button btnRecherche;

	@FXML
	private Button btnSupprimer;

	@FXML
	private TableColumn<Role, String> columnNom;

	@FXML
	private Label countRole;

	@FXML
	private TableView<Role> tableViewRoles;

	@FXML
	private TextField textRecherche;

	public Stage primaryStage;

	private IFeature feature = Feature.getCurrentInstance();
	
	private ObservableList<Role> roles = FXCollections.observableArrayList();
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private RoleEditDialogController roleEditDialogController;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProperties();
		addProduitObservableListToTheTable();
		refreshCount();
		addListeners();

	}

	private void initProperties() {
		columnNom.setCellValueFactory(new PropertyValueFactory<>("intitule"));

	}

	private void addProduitObservableListToTheTable() {
		setRoles(feature.loadRoleObservableList());
		tableViewRoles.setItems(getRoles());
		if(getRoles().size() > 0)
			tableViewRoles.getSelectionModel().select(0);	

	}

	private void addListeners() {
		// Wrap the ObservaleList in a FilteredList (initially display all data)
		FilteredList<Role> filteredRoles = new FilteredList<>(getRoles(), b -> true);

		// Set the filter Predicate whenever the filter changes
		textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredRoles.setPredicate(role -> {
				// If filter text is blank or null or empty -> display all departments
				if(newValue.isBlank() || newValue.isEmpty() || newValue == null) 
					return true;

				// Compare the name of every produit with keyword
				String keyword = newValue.toLowerCase();

				if(role.getIntitule().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches name
				}    				
				else {
					return false; // No matches
				}

			});
		});

		// Wrap the FilteredList in a SortedList 
		SortedList<Role> sortedRoles= new SortedList<>(filteredRoles);

		// Bind the SortedList comparator to the TableView comparator, otherwise sorting will have no effect
		sortedRoles.comparatorProperty().bind(tableViewRoles.comparatorProperty());

		// Add sorted (and filtered) data to the table 
		tableViewRoles.setItems(sortedRoles);
	}

	private void refreshCount() {
		countRole.setText(String.valueOf(getRoles().size()));
	}

	public ObservableList<Role> getRoles() {
		return roles;
	}

	public void setRoles(ObservableList<Role> roles) {
		this.roles = roles;
	}

	@FXML
	void handleClickedAjouter(ActionEvent event) {
		Role tempRole = new Role();
		boolean okClicked = showRoleEditDialog(tempRole,"Creation d'un role ");
		if(okClicked) {
			Boolean statut = feature.createRole(tempRole);
			if(statut) {
				refreshCount();

			}
		}

	}

	@FXML
	void handleClickedModifier(ActionEvent event) {
		Role selectedRole= 
				tableViewRoles.getSelectionModel().getSelectedItem();        

		if (selectedRole != null) {
			boolean okClicked = showRoleEditDialog(selectedRole, "Modification d'un role ");
			if (okClicked) {
				Boolean statut = feature.updateRole(selectedRole);
				if(statut) {
					refreshCount();

				}
			}

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Role Selected", 
					"Please select a role in the table.");
		}
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {

	}

	@FXML
	void handleClickedSupprimer(ActionEvent event) {
		int selectedIndex = tableViewRoles.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && tableViewRoles.getSelectionModel().isSelected(selectedIndex)) {
			Role selectedRole = tableViewRoles.getSelectionModel().getSelectedItem();

			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Delete",
					"Delete \""+selectedRole.getIntitule()+ "\" ? ",
					"Are you sure you want to delete this role?");

			if(confirmStatus) {
				Boolean statut = feature.deleteRole(selectedRole);
				if(statut) {
					refreshCount();

				}

			}

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Role Selected", 
					"Please select a role in the table.");
		}
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 *
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showRoleEditDialog(Role role, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = Utilitaire.initFXMLoader("RoleEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

			// Create the dialog Stage.
			Stage dialogStage = Utilitaire.createDialog(root, primaryStage, title);

			// Set the department into the controller.
			roleEditDialogController = loader.getController();
			roleEditDialogController.setDialogStage(dialogStage, title);
			roleEditDialogController.setRole(role);

			// Show the dialog and wait until the user closes it
			Utilitaire.showDialog(dialogStage);

			return roleEditDialogController.isOkClicked();
		} catch (Exception e) {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}


}

