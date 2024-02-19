package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.rekest.entities.Notification;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.enums.NotificationType;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;
import com.rekest.utils.PropertyManager;
import com.rekest.utils.Utilitaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NotificationController implements Initializable {

	/**
	 * Composant FXML
	 */
	@FXML private Label lableCountNotificationsLus;	
	@FXML private Label labelDate;
	@FXML private Label labelHeure;
	@FXML private Label labelNoteEmisse;
	@FXML private Label labelNumeroDemande;
	@FXML private ListView<Notification> listViewNotifications;
	private Stage primaryStage;

	/**
	 * Observable List
	 */
	private ObservableList<Notification> notificationList = FXCollections.observableArrayList();

	/**
	 * Features
	 */
	private IFeature feature = Feature.getCurrentInstance();

	/**
	 * Entities
	 */
	private Utilisateur auth;
	private Notification selectNotification;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setConnectedUser(Utilisateur auth) {
		this.auth = auth;
		loadNotifications();
		refreshCount();
	}

	private void refreshCount() {
		Utilitaire.setLabel(lableCountNotificationsLus, String.valueOf(feature.getNumberNotificationUnread(auth)));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listenerNotification();	
		initDetailsNotification();
	}

	private void initDetailsNotification() {
		Utilitaire.setLabel(labelDate, "");
		Utilitaire.setLabel(labelHeure, "");
		Utilitaire.setLabel(labelNumeroDemande,"");
		Utilitaire.setLabel(labelNoteEmisse,"");
	}

	private void listenerNotification() {
		listViewNotifications.getSelectionModel().selectedItemProperty()
		.addListener(ov -> {
			if (notificationList.size() > 0)
				selectNotification = listViewNotifications.getSelectionModel().getSelectedItem();

			this.loadNotificationDetail();
		});
	}

	private void loadNotificationDetail() {

		if (selectNotification != null) {
			Utilitaire.setLabel(labelDate, Utilitaire.getFullDate(selectNotification.getCreatedAt()));
			Utilitaire.setLabel(labelHeure, Utilitaire.getHourFromDate(selectNotification.getCreatedAt()));
			Utilitaire.setLabel(labelNumeroDemande,String.valueOf(selectNotification.getDemande().getId()));
			Utilitaire.setLabel(labelNoteEmisse,selectNotification.isRead() ? " Lu " : "Non lu");

			selectNotification.setRead(true);
			feature.updateNotification(selectNotification);
			refreshCount();

			if (!selectNotification.isRead())
				Utilitaire.notification(NotificationType.INFO, "Lecture de notification", 
						"Le statut de la notification à été mise a jour");
			loadNotifications();

		} else {
			Utilitaire.alert(AlertType.WARNING, 
					null, 
					"No selection",
					"No Notification selected",
					"Please select an notifications into the list ");

		}

	}


	public void loadNotifications(){
		notificationList.clear();
		
		feature.listNotificationsByUtilisateur(auth).forEach(notif -> notificationList.add(notif));

		listViewNotifications.setCellFactory(new Callback<ListView<Notification>, ListCell<Notification>>() {

			@Override
			public ListCell<Notification> call(ListView<Notification> param) {
				ListCell<Notification> cell = new ListCell<Notification>() {

					protected void updateItem(Notification item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setGraphic(new ImageView(new Image(PropertyManager.getInstance().getApplicationIcon())));
							setText("Demande N°" + 
									item.getDemande().getId() + " - " + 
									item.getMessage() + "\n" + 
									(item.isRead() ? " (Lu) " : " (Non lu) "));
						}
					};
				};
				return cell;
			}
		});
		listViewNotifications.setItems(notificationList);
	}


	/**
	 * Serialize a observablelist of notifications 
	 * @param notifications list
	 * @return list of strings with notification id ,message and state
	 */
	public List<String> serialize(List<Notification> notificationList){

		List<String> strings = new ArrayList<>();

		for (Notification notification : notificationList) {
			strings.add(notification.getId()+ " " + notification.getMessage() + " - le " + 
					Utilitaire.parseState(notification.getCreatedAt())
			+ (notification.isRead() ? " (Lu) " : " (Non lu) "));
		}

		return strings;
	}

	public ObservableList<Notification> getNotificationList() {
		return notificationList;
	}

}
