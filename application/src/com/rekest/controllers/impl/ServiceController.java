package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.Service;
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

public class ServiceController implements Initializable{

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnModifier;

	@FXML
	private Button btnRecherche;

	@FXML
	private Button btnSupprimer;

	@FXML
	private TableColumn<Service, String> columnChefService;

	@FXML
	private TableColumn<Service, String> columnDepartementAssocie;

	@FXML
	private TableColumn<Service, String> columnNom;

	@FXML
	private Label countService;

	@FXML
	private TableView<Service> tableViewServices;

	@FXML
	private TextField textRecherche;

	private ObservableList<Service> services = FXCollections.observableArrayList();

	private Stage primaryStage;

	private ServiceEditDialogController serviceEditDialogController;

	private IFeature feature = Feature.getCurrentInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initProperties();
		addDataInTable();
		addListeners();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void refreshCount() {
		countService.setText(String.valueOf(getServices().size()));

	}

	public ObservableList<Service> getServices() {
		return services;
	}

	public void setServices(ObservableList<Service> services) {
		this.services = services;
	}

	public void initProperties() {
		columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		columnChefService.setCellValueFactory(new PropertyValueFactory<>("nomChefService"));
		columnDepartementAssocie.setCellValueFactory(new PropertyValueFactory<>("nomDepartement"));

	}

	private void addDataInTable() {

		setServices(feature.loadServicesObservableList());
		tableViewServices.setItems(getServices());

		if (getServices().size() > 0) 
			tableViewServices.getSelectionModel().select(0);

		refreshCount();
	}

	private void addListeners() {
		// Wrap the ObservaleList in a FilteredList (initially display all data)
		FilteredList<Service> filteredServices = new FilteredList<>(getServices(), b -> true);

		// Set the filter Predicate whenever the filter changes
		textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredServices.setPredicate(service -> {
				// If filter text is blank or null or empty -> display all services
				if(newValue.isBlank() || newValue.isEmpty() || newValue == null) 
					return true;

				// Compare the name of every service with keyword
				String keyword = newValue.toLowerCase();

				if(service.getNom().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches name
				}
				else {
					return false; // No matches
				}

			});
		});

		// Wrap the FilteredList in a SortedList 
		SortedList<Service> sortedServices = new SortedList<>(filteredServices);

		// Bind the SortedList comparator to the TableView comparator, otherwise sorting will have no effect
		sortedServices.comparatorProperty().bind(tableViewServices.comparatorProperty());

		// Add sorted (and filtered) data to the table 
		tableViewServices.setItems(sortedServices);
	}

	@FXML
	void handleClickedAjouter(ActionEvent event) {
		Service tempService = new Service();
		boolean okClicked = showServiceEditDialog(tempService, "Creation d'un service ");
		if (okClicked) {
			tempService = serviceEditDialogController.getService();
			Boolean statut =  feature.createService(tempService);
			if (statut) {
				refreshCount();

			}
		}
	}

	@FXML
	void handleClickedModifier(ActionEvent event) {
		Service selectedService = 
				tableViewServices.getSelectionModel().getSelectedItem();        

		if (selectedService != null) {
			boolean okClicked = this.showServiceEditDialog(selectedService, "Modification d'un service ");
			if (okClicked) {
				Service tempService = serviceEditDialogController.getService();
				Boolean statut =  feature.updateService(tempService);
				if (statut) {
					refreshCount();

				}
			}
		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Service Selected", 
					"Please select a service in the table.");
		}
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {

	}

	@FXML
	void handleClickedSupprimer(ActionEvent event) {
		int selectedIndex = Utilitaire
				.getCurrentIndexTableView(tableViewServices, primaryStage);
		if (selectedIndex >=0) {
			Service selectedService = tableViewServices.getSelectionModel().getSelectedItem();

			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Delete",
					"Delete \""+selectedService.getNom()+ "\" ? ",
					"Are you sure you want to delete this service?");

			if(confirmStatus) {
				//				tableViewServices.getItems().remove(selectedIndex);
				Boolean statut = feature.deleteService(selectedService);
				if(statut) {
					refreshCount();

				}
			}
		}
		else {
			// Nothing selected
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"No selection",
					"No Service selected",
					"Please select a service in the table ");

		}
	}

	public boolean showServiceEditDialog(Service service, String title) {
		try {
			FXMLLoader loader = Utilitaire.initFXMLoader("ServiceEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);
			Stage dialogStage = Utilitaire.createDialog(root, 
					this.primaryStage, title);

			// Set the department into the controller.
			serviceEditDialogController = loader.getController();
			serviceEditDialogController.setDialogStage(dialogStage, title);
			serviceEditDialogController.setService(service);
			serviceEditDialogController.setDepartements();

			Utilitaire.showDialog(dialogStage);
			return serviceEditDialogController.isOkClicked();
		} catch (Exception e) {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}

}

