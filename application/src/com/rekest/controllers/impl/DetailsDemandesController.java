package com.rekest.controllers.impl;


import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.entities.Demande;
import com.rekest.entities.Note;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;
import com.rekest.utils.Utilitaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DetailsDemandesController implements Initializable {

	/**
	 * Loggers
	 */
	public final static Logger logger = LogManager.getLogger(DetailsDemandesController.class);
	
	@FXML private Label demandeID;
	@FXML private Label demandeState;
	@FXML private Button btnSoumettre;
	@FXML private Label labelNomProduit;
	@FXML private Label labelNomEmploye;
	@FXML private Label labelNomUtilisateur;
	@FXML private Label labelEtat;
	@FXML private ListView<Note> listViewNotes;
	@FXML private TableColumn<Note, String> columnEmetteur;
	@FXML private TableColumn<Note, String> columnNote;
	@FXML private TableView<Note> tableViewNotes;

	/**
	 * ObservableList
	*/
	private ObservableList<Note> notes =  FXCollections.observableArrayList();

	public void setNoteList(ObservableList<Note> noteList) {
		this.notes = noteList;
	}
	
	public ObservableList<Note> getNoteList() {
		return notes;
	}
	
	private Stage dialogStage;
	private Demande demande;
	private Utilisateur auth;
	
	private boolean okClicked = false;

	public Stage primaryStage;
	
	private IFeature feature = Feature.getCurrentInstance();

	
	public Stage getDialogStage() {
		return dialogStage;
	}
	
	public Utilisateur getAuth() {
		return auth;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void setAuth(Utilisateur auth) {
		this.auth = auth;
	}

	private NoteEditController noteEditController;

	/*
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}


	/*
	 * Sets the department to be edited in the dialog.
	 *
	 * @param department
	 */
	public void setDemande(Demande demande) {
		this.demande = demande;
		Utilitaire.setLabel(demandeID, String.valueOf(demande.getId()));
		Utilitaire.setLabel(labelNomProduit, demande.getProduit().getNom());
		Utilitaire.setLabel(labelNomEmploye, demande.getNomEmploye());
		Utilitaire.setLabel(labelNomUtilisateur, demande.getNomUtilisateur());
		Utilitaire.setLabel(labelEtat, demande.getEtat());
		Utilitaire.setLabel(demandeState, "[" + demande.getEtat() + "]");
		
		addNoteObservableListToTheTable();
	}

	/**
	 * Returns true if the department clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	void handleClickedRejecter(ActionEvent event) {}

	@FXML
	void handleClickedSoumettre(ActionEvent event) {
		Note tempNote = new Note();
		
		boolean okClicked = showNoteEditDialog(tempNote, "Creation d'une note ");
		if (okClicked) {
			logger.info("Note created {}" , tempNote.getMessage());
			logger.info("Note associe a la demande {} ", demande.getId());
			Boolean statut = feature.createNote(tempNote , demande);
			
			if(statut) {
				Utilitaire.displayMessage(Note.class.getSimpleName(), true, "creation");
			}
			
		}
	}


	private boolean showNoteEditDialog(Note note, String title) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = Utilitaire.initFXMLoader("NoteEditDialog");
			AnchorPane root = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

			// Create the dialog Stage.
			Stage dialogStage = Utilitaire.createDialog(root, primaryStage, title);

			// Set the demande into the controller.
			noteEditController = loader.getController();
			noteEditController.setDialogStage(dialogStage);
			noteEditController.setNote(note);
		
			// Show the dialog and wait until the user closes it
			Utilitaire.showDialog(dialogStage);

			return noteEditController.isOkClicked();
		} catch (Exception e) {
			e.printStackTrace();
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"REKEST ERROR","Echec ",e.getMessage());
		}

		return false;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProperties();	
	}
	
	private void addNoteObservableListToTheTable() {
		setNotes(feature.loadNoteByDemandObservableList(demande));
		tableViewNotes.setItems(notes);
		if (notes.size() > 0)
			tableViewNotes.getSelectionModel().select(0);
		
	}
	
	public void setNotes(ObservableList<Note> notes) {
		this.notes.clear();
		this.notes = notes;
	}

	private void initProperties() {
		columnEmetteur.setCellValueFactory(new PropertyValueFactory<>("emetteur"));
		columnNote.setCellValueFactory(new PropertyValueFactory<>("message"));
	}


	public void loadNotifications() {
		
	}

}