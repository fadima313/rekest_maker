package com.rekest.controllers.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rekest.controllers.IController;
import com.rekest.utils.Utilitaire;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AppMainWindowController implements IController {
	
	public final static Logger logger = LogManager.getLogger(AuthenticationController.class);
	private Stage mainWindowStage;

    @FXML
    private Button btnAuthentification;
    
    @Override
	public void setPrimaryStage(Stage primaryStage) {
		this.mainWindowStage = primaryStage;
	}
    

    @FXML
    void handleClickedQuitter(ActionEvent event) {
 	   
    	System.exit(0);
    }
	
	@FXML
    void handleClickedAuthentification(ActionEvent event) {
		Utilitaire.hideAndCloseStage(mainWindowStage, true);
		MainController.getInstance().initAuthentication(mainWindowStage);
    }
	
	public Stage getMainWindowStage() {
		return mainWindowStage;
	}

}
