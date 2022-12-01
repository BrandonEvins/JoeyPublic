package application.controller;

import java.io.File;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * This takes care of displaying all of the credits and debits onto the screen for this specific user. The user can add
 * or remove credits and debits as they need.
 * @author Justin Gilberti wye778
 * @author Sai Ananthula emw832
 * UTSA CS 3443 - Group Project
 * Fall 2022
 */
public class InformationController implements EventHandler<ActionEvent>, Initializable{
	
	@FXML
	Rectangle fadeRectangle;
	@FXML
	Rectangle loginRectangle;
	@FXML
	Label financesLabel;
	@FXML
	Button button_Savings_Growth;
	@FXML
	Button button_Mortgage;
	@FXML
	Rectangle buttonShadow;
	@FXML
	Label usernameLabel;
	@FXML
	Button logoutButton;
	@FXML
	Button saveData;
	@FXML
	TextField valueText;
	@FXML
	TextField descriptionText;
	@FXML
	Label creditsLabel;
	@FXML
	Label debitsLabel;

	public static Financial_Data financialData = null;
	public String creditsString = "";
	public String debitsString = "";
	@SuppressWarnings("static-access")
	@Override
	public void handle(ActionEvent event) {
		Button sourceButton = (Button) event.getSource();
		String buttonID = sourceButton.getId();
		
		if(buttonID != null && buttonID.equals("button_Savings_Growth")) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Savings_Growth.fxml"));
				Main.stage.setScene( new Scene(root, 800, 800) );
				Main.stage.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(buttonID != null && buttonID.equals("button_Mortgage")) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Mortgage.fxml"));
				Main.stage.setScene( new Scene(root, 800, 800) );
				Main.stage.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else if(buttonID != null && buttonID.equals("addCredit")) {
			Credit credit = new Credit(descriptionText.getText(), Double.valueOf(valueText.getText()));
			Financial_Data.credits.add(credit);
			creditsLabel.setText("");
			creditsString = "";
			for(int i = 0; i < InformationController.financialData.credits.size(); ++i) {
				creditsString += InformationController.financialData.credits.get(i).toString();
				if(i < InformationController.financialData.credits.size() - 1)
					creditsString += "\n";
			}
			creditsLabel.setText(creditsString);
		}else if(buttonID != null && buttonID.equals("removeCredit")) {
			Credit removeMe = new Credit(descriptionText.getText(), Double.valueOf(valueText.getText()));
			for(int i = 0; i < InformationController.financialData.credits.size(); ++i) {
				if(InformationController.financialData.credits.get(i).getDescription().equals(removeMe.getDescription()) &&
						InformationController.financialData.credits.get(i).getValue() == removeMe.getValue()) {
					Financial_Data.credits.remove(i);
					creditsLabel.setText("");
					creditsString = "";
					for(int j = 0; j < InformationController.financialData.credits.size(); ++j) {
						creditsString += InformationController.financialData.credits.get(j).toString();
						if(j < InformationController.financialData.credits.size() - 1)
							creditsString += "\n";
					}
					creditsLabel.setText(creditsString);
				}
			}
		}else if(buttonID != null && buttonID.equals("addDebit")) {
			Debit debit = new Debit(descriptionText.getText(), Double.valueOf(valueText.getText()));
			Financial_Data.debits.add(debit);
			debitsLabel.setText("");
			debitsString = "";
			for(int i = 0; i < InformationController.financialData.debits.size(); ++i) {
				debitsString += InformationController.financialData.debits.get(i).toString();
				if(i < InformationController.financialData.debits.size() - 1)
					debitsString += "\n";
			}
			debitsLabel.setText(debitsString);
		}else if(buttonID != null && buttonID.equals("removeDebit")) {
			Debit removeMe = new Debit(descriptionText.getText(), Double.valueOf(valueText.getText()));
			for(int i = 0; i < InformationController.financialData.debits.size(); ++i) {
				if(InformationController.financialData.debits.get(i).getDescription().equals(removeMe.getDescription()) &&
						InformationController.financialData.debits.get(i).getValue() == removeMe.getValue()) {
					Financial_Data.debits.remove(i);
					debitsLabel.setText("");
					debitsString = "";
					for(int j = 0; j < InformationController.financialData.debits.size(); ++j) {
						debitsString += InformationController.financialData.debits.get(j).toString();
						if(j < InformationController.financialData.debits.size() - 1)
							debitsString += "\n";
					}
					debitsLabel.setText(debitsString);
				}
			}
		}else if(buttonID != null && buttonID.equals("saveData")) {
			financialData.saveFinancialData();
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

	@SuppressWarnings({ "static-access" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// changes label text to reflect username
		User currUser = MainController.user;
		usernameLabel.setText(currUser.getUserName());
		
		FadeTransition fade = new FadeTransition();
		
		fade.setDuration(Duration.millis(1000));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		
		fade.setNode(fadeRectangle);
		fade.setNode(buttonShadow);
		fade.setNode(button_Mortgage);
		fade.setNode(button_Savings_Growth);
		fade.setNode(financesLabel);
		fade.setNode(loginRectangle);
		fade.setNode(usernameLabel);
		fade.setNode(logoutButton);
		
		fade.play();
		financialData = new Financial_Data(currUser.getUserName());
		try {
			File creditsFile = new File("data/financialData/"+currUser.getUserName()+"Credits.csv");
			File debitsFile = new File("data/financialData/"+currUser.getUserName()+"Debits.csv");
			creditsFile.createNewFile();
			debitsFile.createNewFile();
			financialData.loadFinancialData();
			for(int i = 0; i < InformationController.financialData.credits.size(); ++i) {
				creditsString += InformationController.financialData.credits.get(i).toString();
				if(i < InformationController.financialData.credits.size() - 1)
					creditsString += "\n";
			}
			for(int i = 0; i < InformationController.financialData.debits.size(); ++i) {
				debitsString += InformationController.financialData.debits.get(i).toString();
				if(i < InformationController.financialData.debits.size() - 1)
					debitsString += "\n";
			}
			creditsLabel.setText(creditsString);
			debitsLabel.setText(debitsString);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}