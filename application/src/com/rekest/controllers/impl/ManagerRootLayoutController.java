package com.rekest.controllers.impl;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.controllers.IController;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.utils.Utilitaire;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ManagerRootLayoutController implements Initializable , IController {

	/**
	 * Loggers
	 */
	public final static Logger logger = LogManager.getLogger(AdminRootLayoutController.class);

	
	/**
	 * Controllers
	 */     
	private MainController mainController;

	/**
	 * Stage and Node
	 */
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
	 * Composants
	 */
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
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		Utilitaire.setDimensionStage(primaryStage, 600, 1200);
		this.initData();
	}
	
	@Override
	public void setUserData(Utilisateur auth) {
		this.userConnected = auth;
	}

	@FXML
	void handleClickedAccueil(MouseEvent event) {
		Utilitaire.loadPageInRootLayout(rootLayout, "ManagerOverview" , userConnected);
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
		Utilitaire.loadPageInRootLayout(rootLayout, "Profil", userConnected);
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


	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
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
}
