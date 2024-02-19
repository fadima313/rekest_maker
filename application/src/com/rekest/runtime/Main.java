package com.rekest.runtime;

import com.rekest.controllers.impl.MainController;
import com.rekest.enums.NotificationType;
import com.rekest.utils.Utilitaire;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private MainController mainController;
    
    public Main() {
    	mainController = MainController.getInstance();
	}
    
    @Override 
    public void start(Stage primaryStage) {
    	try {
    	     mainController.initApplication(primaryStage);
    	} catch(Exception e) {
    		e.printStackTrace();
    		Utilitaire.notification(NotificationType.ERROR, 
    				"Error when creating main stage", e.getMessage());
    	}
     
    }

    public static void main(String[] args) {
        launch(args);
    }
}