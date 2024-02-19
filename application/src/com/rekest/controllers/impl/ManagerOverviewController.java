package com.rekest.controllers.impl;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ManagerOverviewController implements Initializable {

	private Stage primaryStage;

	@FXML
	private BarChart<?, ?> barChartDemandes;

	@FXML
	private Label countDemandes;

	@FXML
	private Label countJour;

	@FXML
	private Label countNonValides;

	@FXML
	private Label countSemaine;

	@FXML
	private Label countValides;

	@FXML
	private PieChart pieChartDemandes;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		countDemandes.setText("0");
		countJour.setText("0");
		countValides.setText("0");
		countNonValides.setText("0");
		countSemaine.setText("0");
	}
}
