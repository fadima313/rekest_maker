package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.employes.Employe;
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

public class EmployeController implements Initializable {

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnModifier;

	@FXML
	private Button btnRecherche;

	@FXML
	private Button btnSupprimer;

	@FXML
	private TableColumn<Employe, String> columnAdresse;

	@FXML
	private TableColumn<Employe, String> columnEmail;

	@FXML
	private TableColumn<Employe, String> columnLogin;

	@FXML
	private TableColumn<Employe, String> columnNom;

	@FXML
	private TableColumn<Employe, String> columnPrenom;

	@FXML
	private TableColumn<Employe, String> columnProfil;

	@FXML
	private TableColumn<Employe, String> columnService;

	@FXML
	private TableColumn<Employe, String> columnTelephone;

	@FXML
	private Label countEmploye;

	@FXML
	private TableView<Employe> tableViewEmployes;

	@FXML
	private TextField textRecherche;

	private EmployeEditDialogController employeEditDialogController;

	private Stage primaryStage;

	private IFeature feature = Feature.getCurrentInstance();

	private ObservableList<Employe> employees = FXCollections.observableArrayList();

	@FXML
	void handleClickedAjouter(ActionEvent event) {
		Employe tempEmp = new Employe();
		boolean okClicked = showEmployeEditDialog(tempEmp, "Creation d'un employe ");
		if (okClicked) {
			tempEmp = employeEditDialogController.getEmploye();
			Boolean statut =  feature.createEmploye(tempEmp);
			if (statut) {
				refreshCount();
			}
		}
	}

	@FXML
	void handleClickedModifier(ActionEvent event) {
		
		Employe selectedEmploye = tableViewEmployes.getSelectionModel().getSelectedItem();
		if (selectedEmploye != null) {
			boolean okClicked = showEmployeEditDialog(selectedEmploye, "Modification d'un employe ");
			if (okClicked) {
				Employe tempEmp = employeEditDialogController.getEmploye();
				Boolean statut =  feature.updateEmploye(tempEmp);
				if (statut) {
					refreshCount();

				}
			}

		} else {
			// Nothing selected
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"No selection",
					"No Employe selected",
					"Please select an employe in the table ");

		}
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {}

	@FXML
	void handleClickedSupprimer(ActionEvent event) {

		int selectedIndex = tableViewEmployes.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0 && tableViewEmployes.getSelectionModel().isSelected(selectedIndex)) {
			Employe selectedEmploye = tableViewEmployes.getSelectionModel().getSelectedItem();

			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Delete",
					"Delete \""+selectedEmploye.getNom()+ "\" ? ",
					"Are you sure you want to delete this employe?");

			if(confirmStatus) {
//				tableViewEmployes.getItems().remove(selectedIndex);
				Boolean statut = feature.deleteEmploye(selectedEmploye);
				if(statut) {
					refreshCount();
					
				}
			}


		} else {
			// Nothing selected
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"No selection",
					"No Employe selected",
					"Please select an employe in the table ");

		}
	}


	public boolean showEmployeEditDialog(Employe employe, String title) {
		try {
			FXMLLoader loader = Utilitaire.initFXMLoader("EmployeEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);
			Stage dialogStage = Utilitaire.createDialog(root, this.primaryStage, title);

			// Set the employe into the controller.
			employeEditDialogController = loader.getController();
			employeEditDialogController.setDialogStage(dialogStage, title);
			employeEditDialogController.setEmploye(employe);
			employeEditDialogController.setServices();
			employeEditDialogController.setProfiles();

			Utilitaire.showDialog(dialogStage);
			return employeEditDialogController.isOkClicked();
		} catch (Exception e) {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}

	public void loadDataInTable() {
		// Init properties
		initProperties();

		// Add observable list data to the table
		setEmployees(feature.loadEmployesObservableList());
		tableViewEmployes.setItems(FXCollections.observableArrayList(getEmployees()));

		if (getEmployees().size() > 0) 
			tableViewEmployes.getSelectionModel().select(0);
		
		refreshCount();

		// Wrap the ObservaleList in a FilteredList (initially display all data)
		FilteredList<Employe> filteredEmployes = new FilteredList<>(getEmployees(), b -> true);

		// Set the filter Predicate whenever the filter changes
		textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
			
			filteredEmployes.setPredicate(employe -> {
				// If filter text is blank or null or empty -> display all employees
				if(newValue.isBlank() || newValue.isEmpty() || newValue == null) 
					return true;

				// Compare the firstname and lastname of every employe with keyword
				String keyword = newValue.toLowerCase();

				if(employe.getNom().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches lastname
				}
				else if(employe.getPrenom().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches firstname
				}
				else {
					return false; // No matches
				}

			});
		});

		// Wrap the FilteredList in a SortedList 
		SortedList<Employe> sortedEmployes = new SortedList<>(filteredEmployes);

		// Bind the SortedList comparator to the TableView comparator, otherwise sorting will have no effect
		sortedEmployes.comparatorProperty().bind(tableViewEmployes.comparatorProperty());

		// Add sorted (and filtered) data to the table 
		tableViewEmployes.setItems(sortedEmployes);
		
	}

	public void initProperties(){

		columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
//		columnProfil.setCellValueFactory(new PropertyValueFactory<>("employeProfil"));
		columnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
		columnService.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

	}


	public ObservableList<Employe> getEmployees() {
		return employees;
	}

	public void setEmployees(ObservableList<Employe> employees) {
		this.employees.clear();
		this.employees = employees;
	}

	public void refreshCount() {
		countEmploye.setText(String.valueOf(getEmployees().size()));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDataInTable();
	}
}
