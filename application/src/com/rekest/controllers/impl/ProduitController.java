package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.Produit;
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

public class ProduitController implements Initializable {

	@FXML
	private Button btnAjouter;

	@FXML
	private Button btnModifier;

	@FXML
	private Button btnRecherche;

	@FXML
	private Button btnSupprimer;


	@FXML
	private TableColumn<Produit, String> columnDescription;

	@FXML
	private TableColumn<Produit, String> columnNom;

	@FXML
	private TableColumn<Produit, Double> columnPrix;

	@FXML
	private TableColumn<Produit, Integer> columnQuantite;

	@FXML
	private TableColumn<Produit, String> columnType;

	@FXML
	private Label countProduit;

	@FXML
	private TableView<Produit> tableViewProduits;

	@FXML
	private TextField textRecherche;

	private ObservableList<Produit> produits =  FXCollections.observableArrayList();

	public Stage primaryStage;

	private IFeature feature = Feature.getCurrentInstance();


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private ProduitEditDialogController produitEditDialogController;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProperties();
		addProduitObservableListToTheTable();
		refreshCount();
		addListeners();

	}

	private void initProperties() {
		columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
		columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		columnQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
		columnType.setCellValueFactory(new PropertyValueFactory<>("type"));

	}

	private void addProduitObservableListToTheTable() {
		setProduits(feature.loadProduitsObservableList());
		tableViewProduits.setItems(getProduits());
		if(getProduits().size() > 0)
			tableViewProduits.getSelectionModel().select(0);	

	}

	private void addListeners() {
		// Wrap the ObservaleList in a FilteredList (initially display all data)
		FilteredList<Produit> filteredProduits = new FilteredList<>(getProduits(), b -> true);

		// Set the filter Predicate whenever the filter changes
		textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredProduits.setPredicate(produit -> {
				// If filter text is blank or null or empty -> display all departments
				if(newValue.isBlank() || newValue.isEmpty() || newValue == null) 
					return true;

				// Compare the name of every produit with keyword
				String keyword = newValue.toLowerCase();

				if(produit.getNom().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches name
				}    				
				else {
					return false; // No matches
				}

			});
		});

		// Wrap the FilteredList in a SortedList 
		SortedList<Produit> sortedProduits= new SortedList<>(filteredProduits);

		// Bind the SortedList comparator to the TableView comparator, otherwise sorting will have no effect
		sortedProduits.comparatorProperty().bind(tableViewProduits.comparatorProperty());

		// Add sorted (and filtered) data to the table 
		tableViewProduits.setItems(sortedProduits);
	}

	private void refreshCount() {
		countProduit.setText(String.valueOf(getProduits().size()));
	}

	public ObservableList<Produit> getProduits() {
		return produits;
	}

	public void setProduits(ObservableList<Produit> produits) {
		this.produits = produits;
	}

	@FXML
	void handleClickedAjouter(ActionEvent event) {
		Produit tempProduit = new Produit();
		boolean okClicked = showProduitEditDialog(tempProduit,"Creation d'un produit ");
		if(okClicked) {
			Boolean statut = feature.createProduit(tempProduit);
			if(statut) {
				refreshCount();

			}
		}

	}

	@FXML
	void handleClickedModifier(ActionEvent event) {
		Produit selectedProduit= 
				tableViewProduits.getSelectionModel().getSelectedItem();        

		if (selectedProduit != null) {
			boolean okClicked = showProduitEditDialog(selectedProduit, "Modification d'un produit ");
			if (okClicked) {
				Boolean statut = feature.updateProduit(selectedProduit);
				if(statut) {
					refreshCount();

				}
			}

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Product Selected", 
					"Please select a product in the table.");
		}
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {

	}

	@FXML
	void handleClickedSupprimer(ActionEvent event) {
		int selectedIndex = tableViewProduits.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0 && tableViewProduits.getSelectionModel().isSelected(selectedIndex)) {
			Produit selectedProduit = tableViewProduits.getSelectionModel().getSelectedItem();

			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Delete",
					"Delete \""+selectedProduit.getNom()+ "\" ? ",
					"Are you sure you want to delete this product?");

			if(confirmStatus) {
				Boolean statut = feature.deleteProduit(selectedProduit);
				if(statut) {
					refreshCount();

				}

			}

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Product Selected", 
					"Please select a product in the table.");
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
	public boolean showProduitEditDialog(Produit produit, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = Utilitaire.initFXMLoader("ProduitEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

			// Create the dialog Stage.
			Stage dialogStage = Utilitaire.createDialog(root, primaryStage, title);

			// Set the department into the controller.
			produitEditDialogController = loader.getController();
			produitEditDialogController.setDialogStage(dialogStage, title);
			produitEditDialogController.setProduit(produit);
			produitEditDialogController.setTypes();

			// Show the dialog and wait until the user closes it
			Utilitaire.showDialog(dialogStage);

			return produitEditDialogController.isOkClicked();
		} catch (Exception e) {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}


}

