package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.controllers.IController;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.utils.Utilitaire;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class UtilisateurRootLayoutController implements Initializable , IController {

	public final static Logger logger = LogManager.getLogger(UtilisateurRootLayoutController.class);

	private MainController mainController;

	private Stage primaryStage;

	private BorderPane rootLayout;

	@FXML
	private AnchorPane anchorPaneCenter;

	@FXML
	private Button btnDemande;

	@FXML
	private Button btnLogOut;

	@FXML
	private Button btnNotifications;

	@FXML
	private Button btnProfil;

    @FXML
    private Button btnRefresh;

	@FXML
	private Circle circleProfil;

	@FXML
	private ImageView imageLogo;

	@FXML
	private Label labelProfil;

	@FXML
	private Label labelUsername;
	
	private Utilisateur userConnected;
	
	private String currentPage = "Accueil";
	
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
	@Override
	public void setUserData(Utilisateur auth) {
		this.userConnected = auth;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Utilitaire.setDimensionStage(primaryStage, 650, 1200);
		this.primaryStage.setMaximized(true);
		this.initData();
	}

	
	@Override
	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
		logger.info("Root layout a été setter");
	}


	@Override
	public void initData() {
		try {
			Utilitaire.initData(userConnected, primaryStage, labelProfil, labelUsername, currentPage);	
			
		} catch (Exception e) {
			logger.info("------------------------------------------------");
			e.printStackTrace();
			logger.error("Error in initData {}" , e.getMessage());
			logger.info("------------------------------------------------");
		}
	}

	@FXML
	void handleClickedAccueil(MouseEvent event) {
		Utilitaire.loadPageInRootLayout(rootLayout, "Demandes" , null);
		Utilitaire.setTiteStage(primaryStage , currentPage, userConnected);
	}
	
	@FXML
	void handleClickedDemande(MouseEvent event) {
		Utilitaire.loadPageInRootLayout(rootLayout, "Demandes", userConnected);
		Utilitaire.setTiteStage(primaryStage , "Demandes", userConnected);
	}

	@FXML
	void handleClickedLogOut(MouseEvent event) {
		Utilitaire.logout(primaryStage);
	}

	@FXML
	void handleClickedProfil(MouseEvent event) {
		Utilitaire.loadPageInRootLayout(rootLayout, "Profil" , userConnected);
		Utilitaire.setTiteStage(primaryStage , "Profil", userConnected);
	}

	@FXML
	void handleClickedNotification(MouseEvent event) {
		Utilitaire.loadPageInRootLayout(rootLayout, "Notifications", userConnected);
		Utilitaire.setTiteStage(primaryStage , "Notifications", userConnected);
	}

    @FXML
    void handleClickedRefresh(MouseEvent event) {

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainController = MainController.getInstance();
	}

	public MainController getMainController() {
		return mainController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

}
