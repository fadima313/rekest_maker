package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import com.rekest.entities.Departement;
import com.rekest.entities.Service;
import com.rekest.entities.employes.Administrateur;
import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.ChefService;
import com.rekest.entities.employes.Directeur;
import com.rekest.entities.employes.DirecteurGeneral;
import com.rekest.entities.employes.Employe;
import com.rekest.entities.employes.Gestionnaire;
import com.rekest.entities.employes.Utilisateur;
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

public class UtilisateurController implements Initializable {

	@FXML
	private TableColumn<Utilisateur, String> columnAdresse;

	@FXML
	private TableColumn<Utilisateur, String> columnEmail;

	@FXML
	private TableColumn<Utilisateur, String> columnLogin;

	@FXML
	private TableColumn<Utilisateur, String> columnNom;

	@FXML
	private TableColumn<Utilisateur, String> columnPrenom;

	@FXML
	private TableColumn<Utilisateur, String> columnProfil;

	@FXML
	private TableColumn<Utilisateur, String> columnService;

	@FXML
	private TableColumn<Utilisateur, String> columnTelephone;

	@FXML
	private TableColumn<Utilisateur, String> columnStatus;

	@FXML
	private TextField textRecherche;

	@FXML
	private Label countUtilisateur;

	@FXML
	private TableView<Utilisateur> tableViewUtilisateurs;

	private UtilisateurEditDialogController utilisateurEditDialogController;

	private Stage primaryStage;

	private IFeature feature = Feature.getCurrentInstance();

	private ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProperties();
		addUtilisateurObservableListToTable();
		refreshCount();
		addListeners();

	}

	private void initProperties(){

		columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		columnProfil.setCellValueFactory(new PropertyValueFactory<>("employeProfil"));
		columnLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("isEnable"));
		columnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
		columnService.setCellValueFactory(new PropertyValueFactory<>("serviceName"));

	}

	private void addUtilisateurObservableListToTable() {
		// Add observable list data to the table
		setUtilisateurs(feature.loadUtilisateursObservableList());
		tableViewUtilisateurs.setItems(utilisateurs);
		if (utilisateurs.size() > 0)
			tableViewUtilisateurs.getSelectionModel().select(0);	
	}

	private void refreshCount() {
		countUtilisateur.setText(String.valueOf(getUtilisateurs().size()));

	}

	private void addListeners() {
		// Wrap the ObservaleList in a FilteredList (initially display all data)
		FilteredList<Utilisateur> filteredUtilisateurs = new FilteredList<>(getUtilisateurs(), b -> true);

		// Set the filter Predicate whenever the filter changes
		textRecherche.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredUtilisateurs.setPredicate(utilisateur -> {
				// If filter text is blank or null or empty -> display all departments
				if(newValue.isBlank() || newValue.isEmpty() || newValue == null) 
					return true;

				// Compare the name of every department with keyword
				String keyword = newValue.toLowerCase();

				if(utilisateur.getNom().toLowerCase().indexOf(keyword) > -1) {
					return true; // Filter matches name
				}    				
				else {
					return false; // No matches
				}

			});
		});

		// Wrap the FilteredList in a SortedList 
		SortedList<Utilisateur> sortedUtlisateurs = new SortedList<>(filteredUtilisateurs);

		// Bind the SortedList comparator to the TableView comparator, otherwise sorting will have no effect
		sortedUtlisateurs.comparatorProperty().bind(tableViewUtilisateurs.comparatorProperty());

		// Add sorted (and filtered) data to the table 
		tableViewUtilisateurs.setItems(sortedUtlisateurs);
	}

	public ObservableList<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(ObservableList<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}


	@FXML
	void handleClickedAjouter(ActionEvent event) {
		Utilisateur tempUtilisateur = new Utilisateur();
		Boolean statut = false;
		boolean okClicked = showUtilisateurEditDialog(tempUtilisateur, "Creation d'un utilisateur ");
		if (okClicked) {
			tempUtilisateur = utilisateurEditDialogController.getUtilisateur();
			String entityName = utilisateurEditDialogController.getCurrentComboBoxProfil();

			switch (entityName) {
			case "Administrateur":
				Administrateur tempAdministrateur = new Administrateur(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse(),
						tempUtilisateur.getLogin(),
						tempUtilisateur.getPassword());
				tempAdministrateur.setService(tempUtilisateur.getService());
				statut = feature.createUtilisateur(tempAdministrateur);

				if (statut) {
					refreshCount();
				}

				break;
			case "Employe":
				Employe tempEmploye = new Employe(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse()
						);
				tempEmploye.setService(tempUtilisateur.getService());
				statut = feature.createEmploye(tempEmploye);


				if (statut) {
					refreshCount();
				}

				break;
			case "ChefService":
				ChefService tempChefService = new ChefService(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse(),
						tempUtilisateur.getLogin(),
						tempUtilisateur.getPassword()
						);
				tempChefService.setService(tempUtilisateur.getService());
				Service tempService = utilisateurEditDialogController.getCurrentComboBoxService();
				if (tempService!=null)
					tempService.setChefService(tempChefService);

				statut = feature.createUtilisateur(tempChefService);


				if (statut) {
					refreshCount();
				}
				break;
			case "ChefDepartement":
				ChefDepartement tempChefDepartement = new ChefDepartement(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse(),
						tempUtilisateur.getLogin(),
						tempUtilisateur.getPassword()
						);

				tempChefDepartement.setService(tempUtilisateur.getService());
				Departement tempDepartement = utilisateurEditDialogController.getCurrentComboBoxDepartement();
				if (tempDepartement!=null)
					tempDepartement.setChefDepartement(tempChefDepartement);

				statut = feature.createChefDepartement(tempChefDepartement);


				if (statut) {
					refreshCount();
				}
				break;
			case "Gestionnaire":
				Gestionnaire tempGestionnaire = new Gestionnaire(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse(),
						tempUtilisateur.getLogin(),
						tempUtilisateur.getPassword()
						);
				tempGestionnaire.setService(tempUtilisateur.getService());
				statut = feature.createUtilisateur(tempGestionnaire);


				if (statut) {
					refreshCount();
				}
				break;
			case "Directeur":
				Directeur tempDirecteur = new Directeur(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse(),
						tempUtilisateur.getLogin(),
						tempUtilisateur.getPassword()
						);
				tempDirecteur.setService(tempUtilisateur.getService());
				statut = feature.createUtilisateur(tempDirecteur);


				if (statut) {
					refreshCount();
				}
				break;
			case "DirecteurGeneral":
				DirecteurGeneral tempDirecteurGeneral = new DirecteurGeneral(
						tempUtilisateur.getNom(),
						tempUtilisateur.getPrenom(),
						tempUtilisateur.getTelephone(),
						tempUtilisateur.getEmail(),
						tempUtilisateur.getAdresse(),
						tempUtilisateur.getLogin(),
						tempUtilisateur.getPassword()
						);
				tempDirecteurGeneral.setService(tempUtilisateur.getService());
				statut = feature.createUtilisateur(tempDirecteurGeneral);


				if (statut) {
					refreshCount();
				}
				break;

			default:
				Utilitaire.alert(AlertType.WARNING, 
						null, 
						"Profile error",
						"Incorrect profil provided",
						"Please select a valid profile while submitting ");

				break;
			}

		}
	}

	@FXML
	void handleClickedModifier(ActionEvent event) {

		Utilisateur selectedUtilisateur = tableViewUtilisateurs.getSelectionModel().getSelectedItem();
		if (selectedUtilisateur != null) {
			boolean okClicked = showUtilisateurEditDialog(selectedUtilisateur, "Modification d'un utilisateur ");
			if (okClicked) {

				Utilisateur tempUtilisateur = utilisateurEditDialogController.getUtilisateur();
				tempUtilisateur = utilisateurEditDialogController.getUtilisateur();
				String entityName = utilisateurEditDialogController.getCurrentComboBoxProfil();
				if (entityName!=null) {
					if(entityName.equals("Employe"))
					{
						Boolean statut = feature.updateEmploye(tempUtilisateur);
						if (statut) {
							refreshCount();
						}
					}
					else if(entityName.equals("ChefService")) {
						ChefService tempChefService = feature.findChefService(tempUtilisateur.getId());
						if (tempChefService != null)
						{
							if (tempChefService.getOldService() == null)
							{
								tempChefService.setService(tempUtilisateur.getService());
								Service tempService = utilisateurEditDialogController.getCurrentComboBoxService();
								if (tempService!=null)
									tempService.setChefService(tempChefService);
							}


							Boolean statut = feature.updateUtilisateur(tempChefService);
							if (statut) {
								refreshCount();
							}
						}
					}
					else {
						Boolean statut = feature.updateUtilisateur(tempUtilisateur);
						if (statut) {
							refreshCount();
						}
					}
				}
				else {
					Utilitaire.alert(AlertType.WARNING, 
							null, 
							"Profile error",
							"Incorrect profil provided",
							"Please select a valid profile while submitting ");

				}

			}

		} else {
			// Nothing selected
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"No selection",
					"No Utilisateur selected",
					"Please select an utlisateur in the table ");

		}
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {}

	@FXML
	void handleClickedSupprimer(ActionEvent event) {

		int selectedIndex = tableViewUtilisateurs.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0 && tableViewUtilisateurs.getSelectionModel().isSelected(selectedIndex)) {
			Utilisateur selectedUtilisateur = tableViewUtilisateurs.getSelectionModel().getSelectedItem();

			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Delete",
					"Delete \""+selectedUtilisateur.getNom()+ "\" ? ",
					"Are you sure you want to delete this utilisateur?");

			if(confirmStatus) {
				Boolean statut = feature.deleteUtilisateur(selectedUtilisateur);
				if(statut) {
					refreshCount();

				}
			}


		} else {
			// Nothing selected
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"No selection",
					"No Utilisateur selected",
					"Please select an utilisateur in the table ");

		}
	}


	@FXML
	void handleClickedActiver(ActionEvent event) {
		int selectedIndex = tableViewUtilisateurs.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0 && tableViewUtilisateurs.getSelectionModel().isSelected(selectedIndex)) {
			Utilisateur selectedUtilisateur = tableViewUtilisateurs.getSelectionModel().getSelectedItem();
			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Activate",
					"Activate \""+selectedUtilisateur.getNom()+ "\" ? ",
					"Are you sure you want to activate this utilisateur?");

			if(confirmStatus) {
				Boolean statut = feature.enableUtilisateur(selectedUtilisateur);
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
					"No Utilisateur selected",
					"Please select an utilisateur in the table ");
		}
	}

	@FXML
	void handleClickedDesactiver(ActionEvent event) {
		int selectedIndex = tableViewUtilisateurs.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0 && tableViewUtilisateurs.getSelectionModel().isSelected(selectedIndex)) {
			Utilisateur selectedUtilisateur = tableViewUtilisateurs.getSelectionModel().getSelectedItem();
			Boolean confirmStatus = Utilitaire.confirm(AlertType.CONFIRMATION,
					null, 
					"Desactivate",
					"Desactivate \""+selectedUtilisateur.getNom()+ "\" ? ",
					"Are you sure you want to desactivate this utilisateur?");

			if(confirmStatus) {
				Boolean statut = feature.disableUtilisateur(selectedUtilisateur);
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
					"No Utilisateur selected",
					"Please select an utilisateur in the table ");
		}
	}

	public boolean showUtilisateurEditDialog(Utilisateur utilisateur, String title) {
		try {
			FXMLLoader loader = Utilitaire.initFXMLoader("UtilisateurEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);
			Stage dialogStage = Utilitaire.createDialog(root, this.primaryStage, title);

			// Set the user into the controller.
			utilisateurEditDialogController = loader.getController();
			utilisateurEditDialogController.setDialogStage(dialogStage, title);
			utilisateurEditDialogController.addListeners();
			utilisateurEditDialogController.setUtilisateur(utilisateur);
			utilisateurEditDialogController.setServicesForUser();
			utilisateurEditDialogController.setProfilesForUser();

			Utilitaire.showDialog(dialogStage);
			return utilisateurEditDialogController.isOkClicked();
		} catch (Exception e) {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}


}
