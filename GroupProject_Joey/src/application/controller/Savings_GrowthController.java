package application.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import application.Main;
import application.model.Credit;
import application.model.Debit;
import application.model.Financial_Data;
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
 * This displays a graph on the screen with the user's savings as it grows over time. This uses information taken
 * from a percentage of the credits for this user, along with how many years they want to simulate.
 * @author Brandon Evins
 * UTSA CS 3443 - Group Project
 * Fall 2022
 */
public class Savings_GrowthController implements EventHandler<ActionEvent>, Initializable{
	
	@FXML
	TextField startYear;
	@FXML
	TextField endYear;
	@FXML
	TextField interestRate;
	@FXML
	TextField monthlyMoney;
	@FXML
	Button savingsGo;
	@FXML
	Label savingsGrowthLabel;
	@FXML
	Label errorLabel;
	@FXML
	Rectangle loginRectangle;
	@FXML
	Rectangle fadeRectangle;
	@FXML
	Rectangle buttonShadow;
	@FXML
	Button button_Mortgage;
	@FXML
	Button button_Information;
	@FXML
	Label usernameLabel;
	@FXML
	Button logoutButton;
	@FXML
	LineChart<Number, Number> savingsChart;
	@FXML
	NumberAxis yAxis;
	@FXML
	NumberAxis xAxis;
	@FXML
	Label totalSavings;
	
	private static double userSavings=0;
	private static double userDebits=0;
	private static double userCredits=0;
	
	public static double getUserSavings() {
		return userSavings;
	}

	public static void setUserSavings(double userSavings) {
		Savings_GrowthController.userSavings = userSavings;
	}

	public static double getUserDebits() {
		return userDebits;
	}

	public static void setUserDebits(double userDebits) {
		Savings_GrowthController.userDebits = userDebits;
	}

	public static Series<Number, Number> series = new XYChart.Series<Number,Number>();

	
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
		} else if (buttonID != null && buttonID.equals("button_Mortgage")) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Mortgage.fxml"));
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
	 * generates the graph based on info inputed and user info
	 * @param event
	 */
	@SuppressWarnings("static-access")
	public void GoSavings(ActionEvent event) {
		clearLines();
		//double diff=5000.0;
		//used to randomly generate points for testing
		if(monthlyMoney.getText().equals("") || startYear.getText().equals("") || endYear.getText().equals("") || interestRate.getText().equals("")) {
			errorLabel.setText("PLEASE ENTER IN ALL TEXT FIELDS");
		}
		else {
			errorLabel.setText("");
			//Random rand = new Random();
			int k = Integer.parseInt(startYear.getText());
			int j = Integer.parseInt(endYear.getText());
			//double userSavings = Double.parseDouble(monthlyMoney.getText());
			//userSavings=1+userSavings/100;
			//userSavings*=diff;
			//userSavings+=diff;
			for(int i = 0; i < InformationController.financialData.credits.size(); ++i) {
				userCredits += InformationController.financialData.credits.get(i).getValue();
			}
			for(int i = 0; i < InformationController.financialData.debits.size(); ++i) {
				userDebits += InformationController.financialData.debits.get(i).getValue();
			}
			userSavings=userCredits-userDebits;
			double userInterest = Double.parseDouble(interestRate.getText());
			xAxis.setLowerBound(k);
			xAxis.setUpperBound(j);
			yAxis.setLowerBound(0);
			
			double savings=0;
			DecimalFormat df = new DecimalFormat("#####.##");
			//double savings =calSavings(userSavings,userInterest,j-k);
			for(int i =1; i<=j-k;i++) {
				//System.out.println(i);
				savings =calSavings(userSavings,userInterest,i);
				series.getData().add(new XYChart.Data<>(k+i, calSavings(userSavings,userInterest,i)));
			}
			totalSavings.setText("$"+String.valueOf(df.format(savings)));
		}
	}
	

	@Override
	/**
	 * Sets titles and style for the graph. Plays fade animations
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// changes label text to reflect username
		User currUser = MainController.user;
		usernameLabel.setText(currUser.getUserName());
				
		FadeTransition fade = new FadeTransition();
		//Changes label to x and y axis and sets points on linechart

		savingsChart.setTitle("Savings Growth in Years");
		savingsChart.getData().add(series);
		series.getNode().setStyle("-fx-stroke: #093D5B");
		
		xAxis.setAutoRanging(false);
		yAxis.setAutoRanging(true);
		
		fade.setDuration(Duration.millis(1500));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		
		fade.setNode(loginRectangle);
		fade.setNode(fadeRectangle);
		fade.setNode(buttonShadow);
		fade.setNode(button_Mortgage);
		fade.setNode(button_Information);
		fade.setNode(savingsGrowthLabel);
		fade.setNode(usernameLabel);
		fade.setNode(logoutButton);
		
		fade.play();
		
		
	}
	/**
	 * clears both series of existing data
	 */
	public void clearLines() {
		series.getData().clear();
	}
	public double calSavings(double savingsAmount, double interest, double time) {
		//System.out.println(savingsAmount+" "+interest+" "+time);
		//A=savingsAmount(1+interestRate/1
		
		return savingsAmount*Math.pow(1+(interest/100), time);
	}

	public static double getUserCredits() {
		return userCredits;
	}

	public static void setUserCredits(double userCredits) {
		Savings_GrowthController.userCredits = userCredits;
	}
}
