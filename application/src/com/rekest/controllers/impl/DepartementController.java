package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.Departement;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DepartementController implements Initializable {

	@FXML
	private TableColumn<Departement, String> columnNom;
	
	@FXML
	private TableColumn<Departement, String> columnChefDepartement;

	@FXML
	private TableView<Departement> tableViewDepartement;

	@FXML
	private Label countDepartement;

	@FXML
	private TextField textRecherche;

	private ObservableList<Departement> departements =  FXCollections.observableArrayList();

	public Stage primaryStage;

	private IFeature feature = Feature.getCurrentInstance();


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


	private DepartementEditDialogController departementEditDialogController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProperties();
		addDepartmentObservableListToTheTable();
		refreshCount();
		addListeners();
	}
	

	private void initProperties() {
		columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		columnChefDepartement.setCellValueFactory(new PropertyValueFactory<>("nomChefDepartement"));
		
	}
	
	private void addDepartmentObservableListToTheTable() {
		// Add observable list data to the table
		setDepartements(feature.loadDepartementsObservableList());
		tableViewDepartement.setItems(departements);
		if (departements.size() > 0)
			tableViewDepartement.getSelectionModel().select(0);	

	}

	private void addListeners() {
		// Wrap the ObservaleList in a FilteredList (initially display all data)
		FilteredList<Departement> filteredDepartments = new FilteredList<>(getDepartements(), b -> true);

		// Set the filter Predicate whenever the filter changes
		textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredDepartments.setPredicate(department -> {
				// If filter text is blank or null or empty -> display all departments
				if(newValue.isBlank() || newValue.isEmpty() || newValue == null) 
					return true;

				// Compare the name of every department with keyword
				String keyword = newValue.toLowerCase();

				if(department.getNom().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches name
				}    				
				else {
					return false; // No matches
				}

			});
		});

		// Wrap the FilteredList in a SortedList 
		SortedList<Departement> sortedDepartments = new SortedList<>(filteredDepartments);

		// Bind the SortedList comparator to the TableView comparator, otherwise sorting will have no effect
		sortedDepartments.comparatorProperty().bind(tableViewDepartement.comparatorProperty());

		// Add sorted (and filtered) data to the table 
		tableViewDepartement.setItems(sortedDepartments);
	}

	public void refreshCount() {
		countDepartement.setText(String.valueOf(getDepartements().size()));

	}

	public ObservableList<Departement> getDepartements() {
		return departements;
	}

	public void setDepartements(ObservableList<Departement> departements) {
		this.departements.clear();
		this.departements = departements;
	}

	@FXML
	void handleClickedAjouter(ActionEvent event) {
		Departement tempDepartment = new Departement();
		boolean okClicked = showDepartmentEditDialog(tempDepartment, "Creation d'un departement ");
		if (okClicked) {
			Boolean statut = feature.createDepartement(tempDepartment);
			if(statut) {
				refreshCount();
			}
		}
	}

	@FXML
	void handleClickedModifier(ActionEvent event) {
		Departement selectedDepartement = 
				tableViewDepartement.getSelectionModel().getSelectedItem();        

		if (selectedDepartement != null) {
			boolean okClicked = showDepartmentEditDialog(selectedDepartement, "Modification d'un departement ");
			if (okClicked) {
				Boolean statut = feature.updateDepartement(selectedDepartement);
				if(statut) {
					refreshCount();

				}
			}

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Department Selected", 
					"Please select a department in the table.");
		}
	}

	@FXML
	void handleClickedSupprimer(ActionEvent event) {
		int selectedIndex = tableViewDepartement.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && tableViewDepartement.getSelectionModel().isSelected(selectedIndex)) {
			Departement selectedDepartement = tableViewDepartement.getSelectionModel().getSelectedItem();

			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Delete",
					"Delete \""+selectedDepartement.getNom()+ "\" ? ",
					"Are you sure you want to delete this department?");

			if(confirmStatus) {
				//              tableViewDepartement.getItems().remove(selectedIndex);
				Boolean statut = feature.deleteDepartement(selectedDepartement);
				if(statut) {
					refreshCount();

				}

			}

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Department Selected", 
					"Please select a department in the table.");
		}
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {}


	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 *
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showDepartmentEditDialog(Departement department, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = Utilitaire.initFXMLoader("DepartementEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

			// Create the dialog Stage.
			Stage dialogStage = Utilitaire.createDialog(root, primaryStage, title);

			// Set the department into the controller.
			departementEditDialogController = loader.getController();
			departementEditDialogController.setDialogStage(dialogStage, title);
			departementEditDialogController.setDepartement(department);

			// Show the dialog and wait until the user closes it
			Utilitaire.showDialog(dialogStage);

			return departementEditDialogController.isOkClicked();
		} catch (Exception e) {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}


}
