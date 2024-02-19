package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.entities.Demande;
import com.rekest.entities.employes.Administrateur;
import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.ChefService;
import com.rekest.entities.employes.Directeur;
import com.rekest.entities.employes.DirecteurGeneral;
import com.rekest.entities.employes.Gestionnaire;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.enums.NotificationType;
import com.rekest.exeptions.DAOException;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;
import com.rekest.notificationmanager.impl.NotificationManager;
import com.rekest.utils.Utilitaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DemandeController implements Initializable {

	/**
	 * Loggers
	 */
	public final static Logger logger = LogManager.getLogger(DemandeController.class);
	
	/**
	 * Composant FXML
	 */
	@FXML private Button btnCloturer;
	@FXML private Button btnExporter;
	@FXML private Button btnImprimer;
	@FXML private Button btnRecherche;
	@FXML private Button btnRejeter;
	@FXML private Button btnSoumettre;
	@FXML private Button btnValider;
	@FXML private TableColumn<Demande, String> colomnProduitID;
	@FXML private TableColumn<Demande, String> columnEmploye;
	@FXML private TableColumn<Demande, String> columnEmployeID;
	@FXML private TableColumn<Demande, String> columnEtat;
	@FXML private TableColumn<Demande, String> columnReference;
	@FXML private TableColumn<Demande, String> columnUtilisateur;
	@FXML private TableColumn<Demande, String> columnUtilisateurID;
	@FXML private ComboBox<String> comboBoxFiltre;
	@FXML private Label countDemandes;
	@FXML private TableView<Demande> tableViewDemandes;
	@FXML private TextField textRecherche;

	/**
	 * Stage
	 */
	private Stage primaryStage;

	/**
	 * ATtributs
	 */
	private IFeature feature = Feature.getCurrentInstance();
	private Utilisateur auth;
	private DetailsDemandesController detailsDemandesController;

	private ObservableList<Demande> demandes = FXCollections.observableArrayList();
	private NotificationManager notificationManager = new NotificationManager();
	private DemandeEditDialogController demandeEditDialogController;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProperties();
		refreshCount();
		addListeners();
	}

	private void addListeners() {}

	private void initProperties() {
		columnReference.setCellValueFactory(new PropertyValueFactory<>("id"));
		colomnProduitID.setCellValueFactory(new PropertyValueFactory<>("produitId"));
		columnEmployeID.setCellValueFactory(new PropertyValueFactory<>("employeId"));
		columnEmploye.setCellValueFactory(new PropertyValueFactory<>("nomEmploye"));
		columnUtilisateurID.setCellValueFactory(new PropertyValueFactory<>("utilisateurId"));
		columnUtilisateur.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
		columnEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void initHideField() {
		if(auth != null) {
			String profil = auth.getEmployeProfil();
			if (
					profil.equals(Administrateur.class.getSimpleName()) ||
					profil.equals(Utilisateur.class.getSimpleName()) 
					) {
				Utilitaire.hideButton(btnCloturer , btnExporter , btnImprimer ,btnValider , btnRejeter);
			}
			if (profil.equals(Gestionnaire.class.getSimpleName())) {
				Utilitaire.hideButton(btnExporter , btnImprimer ,btnValider , btnRejeter);
			}
			
			if (profil.equals(ChefService.class.getSimpleName()) 
					||
					profil.equals(ChefDepartement.class.getSimpleName()) ||
					profil.equals(Directeur.class.getSimpleName()) ||
					profil.equals(DirecteurGeneral.class.getSimpleName())||
					profil.equals(ChefDepartement.class.getSimpleName())) {
				Utilitaire.hideButton(btnCloturer);
			}
		}
	}

	public void setConnectedUser(Utilisateur auth) {
		this.auth = auth;
		initHideField();
		addDemandeObservableListToTheTable();
	}

	private void addDemandeObservableListToTheTable() {
		if (
			auth.getEmployeProfil().equals(Directeur.class.getSimpleName())
			|| auth.getEmployeProfil().equals(DirecteurGeneral.class.getSimpleName())
			|| auth.getEmployeProfil().equals(Gestionnaire.class.getSimpleName())
		) {
			setDemandes(feature.loadDemandesObservableList());
		}else {
			setDemandes(feature.loadDemandeByUtilisateurObservableList(auth));
		}
		
		tableViewDemandes.setItems(getDemandes());

		if (getDemandes().size() > 0) 
			tableViewDemandes.getSelectionModel().select(0);

		refreshCount();	

	}

	public void refreshCount() {
		countDemandes.setText(String.valueOf(tableViewDemandes.getItems().size()));
	}

	@FXML
	void handleClickedCloturer(ActionEvent event) {
		responseDemande("Cloturer");
	}

	@FXML
	void handleClickedExporter(ActionEvent event) {
		Utilitaire.notification(NotificationType.INFO, 
				"Fonctionnalité Indisponible", 
				"Desolé cette fonctionnalité n'est pas disponible pour le moment");
	}

	@FXML
	void handleClickedImprimer(ActionEvent event) {
		Utilitaire.notification(NotificationType.INFO, 
				"Fonctionnalité Indisponible", 
				"Desolé cette fonctionnalité n'est pas disponible pour le moment");
	}

	@FXML
	void handleClickedRecherche(ActionEvent event) {
		Utilitaire.notification(NotificationType.INFO, 
				"Fonctionnalité Indisponible", 
				"Desolé cette fonctionnalité n'est pas disponible pour le moment");
	}

	@FXML
	void handleClickedRejecter(ActionEvent event) {
		responseDemande("Rejeter");
	}

	private void responseDemande(String response) {
		Demande selectedDemande = tableViewDemandes.getSelectionModel().getSelectedItem();        

		if (selectedDemande != null) {
			String state  = selectedDemande.getEtat();
			if (!state.equals("crée")) {
				logger.info("Auth : {}" , auth);
				//return;
				sendReponseDemand(auth.getEmployeProfil() , response , selectedDemande);
			} else {
				Utilitaire.notification(NotificationType.ERROR, 
						"Statut de la demande", 
						"La demande doit etre à l'etat soumis pour etre valider");
			}
		} else {
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Demande Selected", 
					"Please select a Demand in the table.");
		}	
	}

	private void sendReponseDemand(String employeProfil , String response , Demande demand) {
		String changeStateText = "";

		if (response.equals("Rejeter"))
			changeStateText =  "Rejetée";

		else if (response.equals("Valider"))
			changeStateText  = "Approuvée";
		
		else if (response.equals("Cloturer"))
			changeStateText = "Cloturée";

		else {
			Utilitaire.notification(NotificationType.ERROR, "Statut Invalid", 
					"Seule Les actions Valider | Rejeter | Cloturer sont authorisées");
			return ;
		}

		if(employeProfil.equals(ChefService.class.getSimpleName())) {
			feature.requestDemande(demand, changeStateText + " N1");
			
			if (changeStateText.equals("Approuvée")) {
				Utilitaire.notification(NotificationType.INFO, 
						"Approbation de la demande", 
						"Vous avez approuvé la demande N° " + demand.getId());
					feature.loadDemandeByUtilisateurObservableList(auth);
				sendNotificationToChefDepartement(demand ,"Une demandé a été " + changeStateText + " par vous !");
				
			}
			else if (changeStateText.equals("Rejetée")) {
				Utilitaire.notification(NotificationType.INFO, 
						"Rejet de la demande", 
						"Vous avez rejeté la demande N° " + demand.getId());
					feature.loadDemandeByUtilisateurObservableList(auth);
				sendNotificationToUser(demand , "Votre demande a été " + changeStateText);	
			}
		}

		if(employeProfil.equals(ChefDepartement.class.getSimpleName())) {
			feature.requestDemande(demand,changeStateText+ " N2");
			if (changeStateText.equals("Approuvée")) {
				sendNotificationToUser(demand ,"Une demandé a été " + changeStateText + " par vous !");
				Utilitaire.notification(NotificationType.INFO, 
					"Approbation de la demande", 
					"Vous avez approuvé la demande N° " + demand.getId());
				feature.loadDemandeByUtilisateurObservableList(auth);
			}
			else  if (changeStateText.equals("Rejetée"))  {
				Utilitaire.notification(NotificationType.INFO, 
						"Rejet de la demande", 
						"Vous avez rejeté la demande N° " + demand.getId());
					feature.loadDemandeByUtilisateurObservableList(auth);
				sendNotificationToUser(demand , "Votre demande a été " + changeStateText);	
			}
			
		} 

		if(employeProfil.equals(Directeur.class.getSimpleName())) {
			feature.requestDemande(demand,changeStateText+ " N3");
			if (changeStateText.equals("Approuvée")) {
				sendNotificationToUser(demand ,"Une demandé a été " + changeStateText + " par vous !");
				Utilitaire.notification(NotificationType.INFO, 
					"Approbation de la demande", 
					"Vous avez approuvé la demande N° " + demand.getId());
				feature.loadDemandesObservableList();
			}
			else if (changeStateText.equals("Rejetée")){
				Utilitaire.notification(NotificationType.INFO, 
						"Rejet de la demande", 
						"Vous avez rejeté la demande N° " + demand.getId());
					feature.loadDemandeByUtilisateurObservableList(auth);
				sendNotificationToUser(demand , "Votre demande a été " + changeStateText);	
			}
		} 

		if(employeProfil.equals(DirecteurGeneral.class.getSimpleName())) {
			feature.requestDemande(demand,changeStateText+ " N4");
			if (changeStateText.equals("Approuvée")) {
				sendNotificationToUser(demand ,"Une demandé a été " + changeStateText + " par vous !");
				Utilitaire.notification(NotificationType.INFO, 
					"Approbation de la demande", 
					"Vous avez approuvé la demande N° " + demand.getId());
				feature.loadDemandesObservableList();
			}
			else if (changeStateText.equals("Rejetée")) {
				Utilitaire.notification(NotificationType.INFO, 
						"Rejet de la demande", 
						"Vous avez rejeté la demande N° " + demand.getId());
					feature.loadDemandeByUtilisateurObservableList(auth);
				sendNotificationToUser(demand , "Votre demande a été " + changeStateText);	
			}
		}
		
		if(employeProfil.equals(Gestionnaire.class.getSimpleName())) {
			
			if (
					demand.getEtat().contains("Rejetée") || 
					demand.getEtat().equals("Approuvée N4") || 
					demand.getEtat().equals("Créé") 
					) {
				feature.requestDemande(demand,changeStateText);
				if(changeStateText.equals("Cloturée")) {
					Utilitaire.notification(NotificationType.INFO, 
							"Cloture de la demande", 
							"Vous avez cloturer la demande N° " + demand.getId());
						feature.loadDemandesObservableList();
						sendNotificationToUser(demand , "Votre demande a été " + changeStateText);
				}
			} else {
				Utilitaire.notification(NotificationType.ERROR, 
						"Statut de la demande", 
						"Le statut de la demande doit etre à approuvée N4 ou Rejété pour cloturer la demande");
					feature.loadDemandesObservableList();
			}
			
		
		}
	}
	
	private void sendNotificationToUser(Demande demand , String message) {
		try {
			notificationManager.createNotification(demand.getUtilisateur(), demand, 
					message);
		} catch (DAOException e) {
			Utilitaire.notification(NotificationType.ERROR, 
					"Notification Non envoyé", 
					"La notification n'a pas pu etre tramsie");
			e.printStackTrace();
		}
	}

	private void sendNotificationToChefDepartement(Demande demand , String message) {
		try {
			notificationManager.createNotification(auth, demand, message);
			logger.info("Auth ;;;; {}" ,auth.getService());
			
			ChefDepartement chefDepartement = 
					auth.getService().getDepartement().getChefDepartement();

			if (chefDepartement != null) {
				notificationManager.createNotification(chefDepartement, demand, 
						"Une nouvelle demandé a été soumise a votre appreciation");

			} else {
				Utilitaire.notification(NotificationType.ERROR, 
						"Chef De departement Non definis", 
						"Le chef de departement n'a pas été definis");
			}
		} catch (DAOException e) {
			Utilitaire.notification(NotificationType.ERROR, 
					"Error de validation ou rejet", 
					"Une erreur est arrivé lors de la validation de la demande");
			e.printStackTrace();
		}

	}


	@FXML
	void handleClickedSoumettre(ActionEvent event) {
		Demande tempDemande = new Demande();
		boolean okClicked = showDemandeEditDialog(tempDemande, "Creation d'une demande");
		if (okClicked) {
			Boolean statut = feature.createDemande(auth, tempDemande,tempDemande.getEmploye());
			if(statut) {
				refreshCount();
				Utilitaire.notification(NotificationType.INFO, 
						"Creation de demande","Une demande a été crée avec success");
			} else {
				Utilitaire.notification(NotificationType.ERROR, "Creation d'une demane", 
						"Une erreur est arrivé dans la creation de la demande .");
			}
		}

	}

	@FXML
	void handleClickedValider(ActionEvent event) {
		responseDemande("Valider");
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 *
	 * @param person the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showDemandeEditDialog(Demande demande, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = Utilitaire.initFXMLoader("DemandeEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

			// Create the dialog Stage.
			Stage dialogStage = Utilitaire.createDialog(root, primaryStage, title);

			// Set the demande into the controller.
			demandeEditDialogController = loader.getController();
			demandeEditDialogController.setDialogStage(dialogStage);
			demandeEditDialogController.setDemande(demande);
			demandeEditDialogController.setAuth(auth);

			demandeEditDialogController.setEmployes();
			demandeEditDialogController.setProduits();

			// Show the dialog and wait until the user closes it
			Utilitaire.showDialog(dialogStage);

			return demandeEditDialogController.isOkClicked();
		} catch (Exception e) {
			e.printStackTrace();
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}

	@FXML
	void handleClickedDetails(ActionEvent event) {
		Demande selectedDemande = 
				tableViewDemandes.getSelectionModel().getSelectedItem();        

		if (selectedDemande != null) {
			System.out.println(selectedDemande);

			showDetailsDemandeEditDialog(selectedDemande,"Details de la demande " + selectedDemande.getId());

		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, primaryStage,
					"No Selection", 
					"No Demande Selected", 
					"Please select a Demand in the table.");
		}

	}


	private boolean showDetailsDemandeEditDialog(Demande demand, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = Utilitaire.initFXMLoader("DetailsDemandes");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

			// Create the dialog Stage.
			Stage dialogStage = Utilitaire.createDialog(root, primaryStage, title);

			//System.out.println("DEMANDE "+ demande.getEtat() + " " + demande.getId());

			// Set the demande into the controller.
			detailsDemandesController = loader.getController();
			detailsDemandesController.setDialogStage(dialogStage);
			detailsDemandesController.setDemande(demand);
			detailsDemandesController.setAuth(auth);
			//detailsDemandesController.setNotes();
			// Show the dialog and wait until the user closes it
			Utilitaire.showDialog(dialogStage);

			return detailsDemandesController.isOkClicked();
		} catch (Exception e) {
			e.printStackTrace();
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;
	}


	public void setDemandes(ObservableList<Demande> demandes) {
		this.demandes = demandes;
	}


	public ObservableList<Demande> getDemandes() {
		return demandes;
	}



}

