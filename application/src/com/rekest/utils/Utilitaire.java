package com.rekest.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;

import com.rekest.controllers.impl.DemandeController;
import com.rekest.controllers.impl.MainController;
import com.rekest.controllers.impl.NotificationController;
import com.rekest.controllers.impl.ProfilController;
import com.rekest.entities.employes.Administrateur;
import com.rekest.entities.employes.ChefDepartement;
import com.rekest.entities.employes.ChefService;
import com.rekest.entities.employes.Directeur;
import com.rekest.entities.employes.DirecteurGeneral;
import com.rekest.entities.employes.Gestionnaire;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.enums.NotificationType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class Utilitaire {

	public final static Logger logger = LogManager.getLogger(Utilitaire.class);

	private static PropertyManager propertyManager = PropertyManager.getInstance();

	/**
	 * Alert Function for javaFX
	 * @param alertType
	 * @param onwer
	 * @param title
	 * @param headerText
	 * @param contentText
	 */
	public static void alert(AlertType alertType, Window onwer, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.initOwner(onwer);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		alert.showAndWait();

		/*
		if (alertType == AlertType.ERROR)
			// Fatal Error, exit System !
			System.exit(-1);
		 */     		
	}

	/**
	 * Confirm Function for javaFX
	 * @param alertType
	 * @param onwer
	 * @param title
	 * @param headerText
	 * @param contentText
	 * @return true when the user clicks YES and false otherwise
	 */
	public static Boolean confirm(AlertType alertType, Window onwer, String title, String headerText, String contentText) {

		Alert alert = new Alert(alertType,null,ButtonType.YES, ButtonType.NO);
		alert.initOwner(onwer);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		alert.showAndWait();
		return (alert.getResult() == ButtonType.YES)? true :  false;

	}
	/*
	 * Notification Push Function
	 * @param notifcationType
	 * @param title
	 * @param message
	 */
	public static void notification(NotificationType notifcationType ,String title , String message) {
		Notifications notification = Notifications.create()
				.title(title)
				.text(message)
				.graphic(new ImageView(new Image(propertyManager.getApplicationIcon())))
				.hideAfter(Duration.seconds(5))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						System.out.println("Cliquer sur la notification");
					}
				});

		if (notifcationType.equals(NotificationType.ERROR)) {
			notification.showError();
		}


		if (notifcationType.equals(NotificationType.INFO)) {
			notification.showInformation();
		}

		if (notifcationType.equals(NotificationType.WARNING)) {
			notification.showWarning();
		}

	}


	/**
	 * Create Scene
	 * @param root
	 * @param stage
	 * @param title
	 * @return scene created
	 */
	public static Stage createScene(Parent root , Stage stage , String title) {
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(title + " - " + propertyManager.getApplicationName());
		return stage;
	}



	/**
	 * Return Random login and password for user
	 * @param utilisateur
	 * @return  Random login and password for user
	 */
	public static Utilisateur generateLoginAndPassword(Utilisateur utilisateur) {
		String login = utilisateur.getNom().toLowerCase() + "." +utilisateur.getPrenom().toLowerCase()+ "@rekest.sn";
		utilisateur.setLogin(login);
		utilisateur.setPassword(Utilitaire.setUserProfil(utilisateur));
		return utilisateur;
	}

	/**
	 * Set password to user according to profil
	 * @param u
	 * @return
	 */
	public static String setUserProfil(Utilisateur u) {
		if (u instanceof Administrateur ) return "admin";
		if (u instanceof Gestionnaire ) return "gestionnaire";
		if (u instanceof ChefService ) return "chefService";
		if (u instanceof Directeur ) return "directeur";
		if (u instanceof DirecteurGeneral ) return "dg";
		if (u instanceof ChefDepartement ) return "chefDepartement";
		return null;
	}
	
	public static String getEntityToCreate(Utilisateur u) {
		if (u.getEmployeProfil() == "Administrateur") return "Administrateur";
		if (u.getEmployeProfil() == "Employe") return "Employe";
		if (u.getEmployeProfil() == "ChefService") return "ChefService";
		if (u.getEmployeProfil() == "ChefDepartement") return "ChefDepartement";
		if (u.getEmployeProfil() == "Gestionnaire") return "Gestionnaire";
		if (u.getEmployeProfil() == "Directeur") return "Directeur";
		if (u.getEmployeProfil() == "DirecteurGeneral") return "DirecteurGeneral";
		return null;
	}

	/**
	 * Charge FXML file into BorderPane 
	 * @param rootLayout
	 * @param filename
	 * @param classe
	 * @throws IOException
	 */
	public static void loadPageInRootLayout(BorderPane rootLayout ,String filename , Utilisateur auth) {
		FXMLLoader loader = Utilitaire.initFXMLoader(filename);
		AnchorPane page   = (AnchorPane) Utilitaire.loadFXMLFile(loader, false);

		if (filename.equals("Profil")) {
			ProfilController profilController = loader.getController();
			profilController.setConnectedUser(auth);
		}

		if (filename.equals("Demandes")) {
			DemandeController demandeController = loader.getController();
			demandeController.setConnectedUser(auth);
		}

		if (filename.equals("Notifications")) {
			NotificationController notificationController = loader.getController();
			notificationController.setConnectedUser(auth);
		}

		if (rootLayout == null) {
			logger.info("RootLayout is null" , rootLayout);
			Utilitaire.notification(NotificationType.WARNING, 
					"Reference Null", "La reference vers rootLayout est null");
		} else
			rootLayout.setCenter(page);
	}

	public static Parent loadFXMLFile(FXMLLoader loader, Boolean isRoot) {
		Parent root = null;
		try {
			if (isRoot) 
				root = (BorderPane) loader.load();
			else
				root = (AnchorPane) loader.load();

			logger.info(root);
			return root;
		} catch (IOException | IllegalStateException | NullPointerException io) {
			io.printStackTrace();
			Utilitaire.alert(AlertType.ERROR, null,
					"Error", io.getClass() +
					" Error while loading fxml file",
					io.getMessage());
			return null;
		}

	}

	/**
	 * Get full path to access to FXML file in views's folder
	 * @param filename
	 * @return
	 */
	public static String getFileInViews(String filename) {
		return propertyManager.getApplicationPathViews() + filename + ".fxml";
	}

	/**
	 * Init FXMLoader setting location
	 * @param filename
	 * @return
	 */
	public static FXMLLoader initFXMLoader(String filename){
		FXMLLoader	fxmlLoader = new FXMLLoader ();
		fxmlLoader.setLocation(MainController.class.getResource(Utilitaire.getFileInViews(filename)));

		if (fxmlLoader.getLocation() != null) 
			logger.info("Chargement de la vue {} avec le chemin {}",filename,fxmlLoader.getLocation().toString()); 

		return fxmlLoader;
	}

	/**
	 * Create Scene
	 * @param root
	 * @param stage
	 * @param title
	 * @return scene created
	 */
	public static Stage createStage(Parent root, String title , Boolean isDecorated) {
		try {
			// create scene
			Scene scene = new Scene(root);
			// create stage
			Stage newStage = new Stage();
			// set scene to stage
			newStage.setScene(scene);

			if (title == null)
				newStage.setTitle(propertyManager.getApplicationName());
			else
				newStage.setTitle(title + " - " + propertyManager.getApplicationName());

			if (isDecorated == true) {
				logger.info("Decorated is called");	
				newStage.initStyle(StageStyle.UNDECORATED);
			}

			Utilitaire.setImageToStage(newStage);
			logger.info("stage [titre={}, scene={} , style={}] " , newStage.getTitle() , newStage.getScene() , newStage.getStyle());
			return newStage;
		} catch (IllegalStateException e) {
			logger.error(e.getLocalizedMessage());
			Utilitaire.notification(NotificationType.ERROR, "Error", e.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * Set Image to Stage
	 * @param stage
	 */
	public static void setImageToStage(Stage stage) {
		stage.getIcons().add(new Image(propertyManager.getApplicationIcon()));
	}

	/**
	 * Create Dialog Box
	 * @param root
	 * @param primaryStage
	 * @param title
	 * @return
	 */
	public static Stage createDialog(Parent root , Stage primaryStage , String title) {
		Stage dialogStage = new Stage();
		dialogStage.setTitle(title + "- Rekest");
		dialogStage.getIcons().add(new Image(propertyManager.getApplicationIcon()));
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(root);
		dialogStage.setScene(scene);
		return dialogStage;
	}

	/**
	 * Show Dialog
	 * @param dialogStage
	 */
	public static void showDialog(Stage dialogStage) {
		dialogStage.showAndWait();
	}

	/**
	 * Show Stage
	 * @param stage
	 */
	public static void showStage(Stage stage) {
		stage.show();
	}

	/**
	 * Show Stage
	 * @param stage
	 */
	public static void hideAndCloseStage(Stage stage , Boolean isHide) {
		if (isHide) stage.hide();
		stage.close();
	}

	/**
	 * Get current index to selected item in table 
	 * @param tableView
	 * @param stage
	 * @return
	 */
	public static int getCurrentIndexTableView(TableView<?> tableView , Stage stage) {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			return selectedIndex;
		} else {
			// Nothing selected.
			Utilitaire.alert(AlertType.WARNING, stage,
					"No Selection", 
					"No Item Selected", 
					"Please select a item in the table.");
			return 0;
		}
	}

	/**
	 * Get value Label value
	 * @param Labeled
	 * @return
	 */
	public static String getLabel(Labeled labeled) {
		return labeled.getText();
	}

	/**
	 * Set label
	 * @param labeled
	 * @param value
	 */
	public static void setLabel(Labeled labeled, String value) {
		labeled.setText(value);
	}


	/**
	 * Get value TextField value
	 * @param textFilField
	 * @return
	 */
	public static String getTextField(TextField textFilField) {
		return textFilField.getText();
	}

	/**
	 * Set TextField
	 * @param textFilField
	 * @param value
	 */
	public static void setTextField(TextField textFilField, String value) {
		textFilField.setText(value);
	}


	/**
	 * Clear all fields
	 * @param fields
	 */
	public static void clear(TextField...fields) {
		for (TextField textField : fields) {
			Utilitaire.setTextField(textField, "");
		}
	}

	/**
	 * Set Windows title for connected user 
	 * @param utilisateur
	 * @param typeSpace
	 * @return
	 */
	public static String setUserWindowTitle(Utilisateur utilisateur , String typeSpace) {
		return utilisateur.getFullName() +" is connected - "+ typeSpace +" Space -  Rekest";
	}

	public static String getFullNameApp() {
		return propertyManager.getApplicationName() + " " + propertyManager.getApplicationBrand(); 
	}

	/**
	 * Center stage on scene
	 * @param stage
	 */
	public static void centrerStage(Stage stage) {
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}

	public static  List<String> getProfiles(){

		List<String> profiles  = new ArrayList<>();
		profiles.add("Administrateur");
		profiles.add("Employe");
		profiles.add("ChefService");
		profiles.add("ChefDepartement");
		profiles.add("Gestionnaire");
		profiles.add("Directeur");
		profiles.add("DirecteurGeneral");

		return profiles;
	}

	public static List<String> getProduitTypes(){
		List<String> types = new ArrayList<>();
		types.add("Materiel");
		types.add("Service");

		return types;
	}

	public static Administrateur getDefaultAdmin() {
		Administrateur admin= new Administrateur(
				propertyManager.getApplicationAdminFirstname(), 
				propertyManager.getApplicationAdminLastname(), 
				propertyManager.getApplicationAdminPhone(), 
				propertyManager.getApplicationAdminEmail(),
				propertyManager.getApplicationAdminAdresse());
		admin.setLogin(propertyManager.getApplicationAdminLogin());
		return admin;
	}

	public static void logout(Stage primaryStage) {
		primaryStage.close();
		if (!primaryStage.isShowing()) {
			MainController.getInstance().initAuthentication(new Stage());
			Utilitaire.notification(NotificationType.INFO, 
					"Deconnexion", "Aurevoir a la prochaine !");
		}	
	}

	/**
	 * Initialize Data for connected user
	 * @param connectedUser 
	 * @param stage
	 * @param labelProfil
	 * @param labelUsername
	 * @param currentPage
	 * @return
	 */
	public static void initLabelData(Utilisateur connectedUser , 
			Stage stage , Labeled labelProfil , Labeled labelUsername , String currentPage) {
		Utilitaire.setLabel(labelProfil, connectedUser.getEmployeProfil());
		Utilitaire.setLabel(labelUsername, connectedUser.getFullName());
	}

	/**
	 * Get connected user getting data in Stage
	 * @param stage
	 * @return
	 */
	public static Utilisateur getConnectedUser(Stage stage) {
		return (Utilisateur) stage.getUserData();
	}

	public static void setDimensionStage(Stage stage ,double height , double width) {
		stage.setHeight(height);
		stage.setWidth(width);
	}

	public static String getTypeManager(Utilisateur utilisateur) {

		String profil = utilisateur.getEmployeProfil();

		if (profil.equals(ChefService.class.getSimpleName())) {
			return "Chef Service";
		}

		if (profil.equals(Directeur.class.getSimpleName())) {
			return "Directeur";
		}

		if (profil.equals(DirecteurGeneral.class.getSimpleName())) {
			return "Directeur General";
		}
		return null;
	}

	public static String setEmployeeProfil(Utilisateur utilisateur) {
		switch (utilisateur.getPassword()) {
		case "admin":
			return Administrateur.class.getSimpleName(); 
		case "chef":
			return ChefService.class.getSimpleName(); 
		case "directeur":
			return Directeur.class.getSimpleName(); 
		case "gestionnaire":
			return Gestionnaire.class.getSimpleName(); 
		case "dg":
			return DirecteurGeneral.class.getSimpleName(); 
		default:
			break;
		}
		return null;
	}


	public static void setTiteStage(Stage stage , String currentPage, Utilisateur userConnected) {
		String space = "";

		if (userConnected.getEmployeProfil().equals(Administrateur.class.getSimpleName()))
			space = "Admin";
		else if (userConnected.getEmployeProfil().equals(Gestionnaire.class.getSimpleName()))
			space = "Gestionnaire";
		else if (userConnected.getEmployeProfil().equals(Utilisateur.class.getSimpleName()))
			space = "Utilisateur";
		else
			space = Utilitaire.getTypeManager(userConnected);

		stage.setTitle(currentPage + " - " + Utilitaire.setUserWindowTitle(userConnected , space)) ;
	}


	public static void initData(Utilisateur userConnected , Stage primaryStage , Label labelProfil , Label labelUsername , String currentPage) {
		Utilitaire.initLabelData(userConnected, primaryStage, labelProfil, labelUsername, currentPage);
		Utilitaire.setTiteStage(primaryStage, currentPage, userConnected);
	}

	public static void hideButton(Button...buttons) {
		for (Button button : buttons) {
			button.setVisible(false);
		}
	}

	public static void setUserProfilePicture(String profile , ImageView imageView) {

		if (profile.equals(Administrateur.class.getSimpleName())) {
			imageView.setImage(new Image(propertyManager.getApplicationUserProfilAdmin()));
		}

	}

	public static String hashPassword(String password) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
	        md.update(password.getBytes());
	        byte[] byteData = md.digest();
	
	        //convertir le tableau de bits en une format hexadï¿½cimal
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	
	        return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public static String parseState(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		return formatter.format(date);  
	}

	public static String getHourFromDate(Date date) {
		return (new SimpleDateFormat("HH:mm:ss")).format(date);  	 
	}

	public static String getFullDate(Date date) {
		return (new SimpleDateFormat("dd/MM/yyyy")).format(date);  

	}

	public static void displayMessage(String classe, boolean status, String operation) {
		String message  = classe;
		if(status)
		{
			message += " a été " + operation + " avec success !";
			Utilitaire.notification(NotificationType.INFO, "Information", message);
		}else
		{
			message += classe + "a , n'été " + operation + " Echec de l'opération!";
			Utilitaire.notification(NotificationType.ERROR, "Information", message);
		}
	}
}
