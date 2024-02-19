package com.rekest.controllers;

import com.rekest.entities.employes.Utilisateur;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public interface IController {

	public void setPrimaryStage(Stage primaryStage);
	default public void setRootLayout(BorderPane root) {};
	default public void initData() {}
	default void setUserData(Utilisateur auth) {}
}
