package application.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ResourceBundle;

import application.Main;
import application.model.User;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * This shows a graph displaying two lines where one is the annual mortgage cost, and the mortgage cost over time. This is
 * based on the user's time range selection, mortgage loan, and interest.
 * @author Andrew Mancillas eks689
 * @author Kimberly Flores skr257
 * UTSA CS 3443 - Group Project
 * Fall 2022
 *
 */
public class MortgageController implements EventHandler<ActionEvent>, Initializable{
	@FXML
	TextField startYear;
	@FXML
	TextField endYear;
	@FXML
	TextField loanAmt;
	@FXML
	TextField interestRate;
	@FXML
	TextField escgrow;
	@FXML
	TextField downPayment;
	@FXML
	NumberAxis yAxis;
	@FXML
	NumberAxis xAxis;
	@FXML
	LineChart<Number, Number> mortgageChart;
	@FXML
	Label mortgageLabel;
	@FXML
	Rectangle loginRectangle;
	@FXML
	Rectangle fadeRectangle;
	@FXML
	Rectangle buttonShadow;
	@FXML
	Button button_Savings_Growth;
	@FXML
	Button button_Information;
	@FXML
	Label usernameLabel;
	@FXML
	Button logoutButton;
	@FXML
	Button thirtyyears;
	@FXML
	Button fifteenyears;
	Series<Number, Number> series1;
	Series<Number, Number> series2;
	@FXML
	Label optionsLabel;
	@FXML
	Label toLabel;
	@FXML
	Button customRange;
	@FXML
	Label monthlyPayment;
	
	@Override
	public void handle(ActionEvent event) {
		Button sourceButton = (Button) event.getSource();
		String buttonID = sourceButton.getId();
		
		if(buttonID != null && buttonID.equals("button_Information")) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Information.fxml"));
				Main.stage.setScene( new Scene(root, 800, 800) );
				Main.stage.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else if (buttonID != null && buttonID.equals("button_Savings_Growth")) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Savings_Growth.fxml"));
				Main.stage.setScene( new Scene(root, 800, 800) );
				Main.stage.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Logs out of the app
	 * @param event
	 */
	public void handleLogout(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * handles thirty years
	 * @param event
	 */
	public void calThirty(ActionEvent event) {
		clearLines();
		xAxis.setLowerBound(0);
		yAxis.setLowerBound(0);
		double userLoan = Double.parseDouble(loanAmt.getText());
		double userInterest = Double.parseDouble(interestRate.getText());
		yAxis.setUpperBound(userLoan);
		double loan = userLoan * (1+userInterest/100)-calMortgage(userLoan,userInterest,30);
		System.out.println(loan);
		
		DecimalFormat df = new DecimalFormat("#####.##");
		monthlyPayment.setText("Monthly Payment: $" + df.format(calMortgage(userLoan,userInterest,30)/12 ));
		for(int i =1; i<=30;i++) {
			series2.getData().add(new XYChart.Data<>(i, calMortgage(userLoan,userInterest,30)));
			series1.getData().add(new XYChart.Data<>(i, 
					loan
					));
			if(i==1) {
				continue;
			}
			else
				loan=loan*(1+userInterest/100)-calMortgage(userLoan,userInterest,30);
			System.out.println(loan);
			
		}
		xAxis.setUpperBound(30);
		
	}
	/**
	 * handles 15 years
	 * @param event
	 */
	public void calFifteen(ActionEvent event) {
		clearLines();
		double userLoan = Double.parseDouble(loanAmt.getText());
		double userInterest = Double.parseDouble(interestRate.getText());
		yAxis.setUpperBound(userLoan);
		xAxis.setLowerBound(0);
		yAxis.setLowerBound(0);
		double loan = userLoan * (1+userInterest/100)-calMortgage(userLoan,userInterest,15);
		System.out.println(loan);
		
		DecimalFormat df = new DecimalFormat("#####.##");
		monthlyPayment.setText("Monthly Payment: $" + df.format(calMortgage(userLoan,userInterest,15)/12 ));
		for(int i =1; i<=15;i++) {
			series2.getData().add(new XYChart.Data<>(i, calMortgage(userLoan,userInterest,15)));
			series1.getData().add(new XYChart.Data<>(i, 
					loan
					));
			if(i==1) {
				continue;
			}
			else
				loan=loan*(1+userInterest/100)-calMortgage(userLoan,userInterest,15);
			System.out.println(loan);
			
		}
		xAxis.setUpperBound(15);
	}
	/**
	 * handles a custom range provided by the user
	 * @param event
	 */
	public void calRange(ActionEvent event) {
		clearLines();
		//used to randomly generate points for testing
		//Random rand = new Random();
		int k = Integer.parseInt(startYear.getText());
		int j = Integer.parseInt(endYear.getText());
		double userLoan = Double.parseDouble(loanAmt.getText());
		double userInterest = Double.parseDouble(interestRate.getText());
		xAxis.setLowerBound(k);
		xAxis.setUpperBound(j);
		yAxis.setUpperBound(userLoan);
		
		DecimalFormat df = new DecimalFormat("#####.##");
		double loan = userLoan * (1+userInterest/100)-calMortgage(userLoan,userInterest,j-k);
		monthlyPayment.setText("Monthly Payment: $" + df.format(calMortgage(userLoan,userInterest,j-k)/12 ));
		for(int i =1; i<=j-k;i++) {
			series2.getData().add(new XYChart.Data<>(k+i, calMortgage(userLoan,userInterest,j-k)));
			series1.getData().add(new XYChart.Data<>(k+i, 
					loan
					));
			if(i==1) {
				continue;
			}
			else
				loan=loan*(1+userInterest/100)-calMortgage(userLoan,userInterest,j-k);
			
		}
	}
	@Override
	/**
	 * Sets fade animations and sets up chart with some information
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// changes label text to reflect username
		User currUser = MainController.user;
		usernameLabel.setText(currUser.getUserName());
		FadeTransition fade = new FadeTransition();
		series2 = new XYChart.Series<Number,Number>();
		series1 = new XYChart.Series<Number,Number>();
		//change nums to affect the graph line, change labels to affect axis titles
		series1.getData().add(new XYChart.Data<>(5,5));
		series1.getData().add(new XYChart.Data<>(10,13));
		series1.getData().add(new XYChart.Data<>(15,23));
		series1.getData().add(new XYChart.Data<>(20,23));
		series1.getData().add(new XYChart.Data<>(25,33));
		series2.getData().add(new XYChart.Data<>(5,25));
		series2.getData().add(new XYChart.Data<>(10,21));
		series2.getData().add(new XYChart.Data<>(15,32));
		series2.getData().add(new XYChart.Data<>(20,15));
		series2.getData().add(new XYChart.Data<>(25,16));
		mortgageChart.getData().add(series1);
		mortgageChart.getData().add(series2);
		series1.getNode().setStyle("-fx-stroke: #093D5B");
		series2.getNode().setStyle("-fx-stroke: red");
		//make sure to set ranges for both axis in button calls
		xAxis.setAutoRanging(false);
		yAxis.setAutoRanging(false);
		// animation stuff
		fade.setDuration(Duration.millis(1500));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		fade.setNode(loginRectangle);
		fade.setNode(fadeRectangle);
		fade.setNode(buttonShadow);
		fade.setNode(mortgageLabel);
		fade.play();
		fade.setNode(button_Information);
		fade.setNode(button_Savings_Growth);
		fade.setNode(usernameLabel);
		fade.setNode(logoutButton);
		fade.setNode(customRange);
		fade.setNode(endYear);
		fade.play();
		fade.setNode(fifteenyears);
		fade.setNode(mortgageChart);
		fade.setNode(optionsLabel);
		fade.setNode(startYear);
		fade.setNode(toLabel);
		fade.play();
		
	}
	/**
	 * clears both series of existing data
	 */
	public void clearLines() {
		series1.getData().clear();
		series2.getData().clear();
	}

	/**
	 * 
	 * @param loanAmount
	 * @param interest
	 * @param escgrow
	 * @param downPayment
	 * @return mortgage annual payment
	 */
	public double calMortgage(double loanAmount, double interest, double loanDuration) {
		double mort = (loanAmount*(Math.pow(1+interest/100,loanDuration)*interest/100)/((Math.pow(1+interest/100,loanDuration)-1)));
		return mort;
		
	}
}