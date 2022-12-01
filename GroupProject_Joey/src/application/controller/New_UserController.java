package application.controller;

import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
/**
 * This creates a new user account and checks to see if all account requirements have been metr. Writes information to this user's
 * own csv file.
 * @author Justin Gilberti wye778
 * @author Kimberly Flores skr257
 * UTSA CS 3443 - Group Project
 * Fall 2022
 *
 */
public class New_UserController implements EventHandler<ActionEvent>, Initializable{
	@FXML
	Button button_AddUser;
	@FXML
	Label label_AddUser1;
	@FXML
	Label label_AddUser2;
	@FXML
	Label label_AddUser3;
	@FXML
	Label label_AddUser4;
	@FXML
	TextField usernameField;
	@FXML
	PasswordField passwordField1;
	@FXML
	PasswordField passwordField2;
	@FXML
	Rectangle fadeRectangle;
	@FXML
	Rectangle loginRectangle;
	@FXML
	ImageView koalaPic;
	@FXML
	Label addUserLabel;
	@FXML
	Button backButton;
	
	public static User user;
	
	@Override
	public void handle(ActionEvent event) {
		
		/* For the fading in animation */
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(1000));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		
		Button sourceButton = (Button) event.getSource();
		String buttonID = sourceButton.getId();
		
		if(buttonID != null && buttonID.equals("button_AddUser")) {
			if(passwordField1.getText().equals(passwordField2.getText())) {
				/*
				 * Set user name and password to new user object
				 * Call loadUsers and addUser methods for the user object
				 */
				user = new User();
				user.setUserName(usernameField.getText());
				user.setPassword(passwordField2.getText());
				User.loadUsers("data/User_info.csv");
				User.addUser(user);
				if(user.isValid()) {
					try {
						Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
						Main.stage.setScene( new Scene(root, 800, 800) );
						Main.stage.show();
					}catch(Exception e) {
						e.printStackTrace();
					}
				} else if (user.getUserName().equals("username_empty") || user.getPassword().equals("password_empty")) {
					/*
					 * Set prompt for blank user name or password 
					 */
					label_AddUser1.setFont(new Font(16.0));
					label_AddUser1.setText("Username and/or password cannot be blank, please try again following the username and password requirements below:");
					fade.setNode(label_AddUser1);
					fade.play();
					
				} else if (user.getUserName().equals("username_format") && user.getPassword().equals("password_format")) {
					/*
					 * Set prompt for invalid regex
					 * Clear user name and password fields
					 */
					label_AddUser1.setFont(new Font(16.0));
					label_AddUser1.setText("Username and/or password format incorrect, please try again following the username and password requirements below:");
					fade.setNode(label_AddUser1);
					fade.play();
					usernameField.clear();
					passwordField1.clear();
					passwordField2.clear();
				} else if (user.getUserName().equals("username_exists") && user.getPassword().equals("password_exists")) {
					/*
					 * Set prompt for user name exists
					 * Clear user name and password fields
					 */
					label_AddUser1.setFont(new Font(16.0));
					label_AddUser1.setText("Username already exists, please add a different username.");
					fade.setNode(label_AddUser1);
					fade.play();
					usernameField.clear();
					passwordField1.clear();
					passwordField2.clear();
				}
			} else {
				/*
				 * Set prompt for non-matching passwords
				 * Clear password fields
				 */
				label_AddUser1.setFont(new Font(16.0));
				label_AddUser1.setText("Passwords did not match, please try again following password requirements below:");
				fade.setNode(label_AddUser1);
				fade.play();
				passwordField1.clear();
				passwordField2.clear();
			}
		}
	}
	
	public void backHandle(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
			Main.stage.setScene( new Scene(root, 800, 800) );
			Main.stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Makes the fade transition on this screen
	 */
	public void initialize(URL location, ResourceBundle resources) {
		FadeTransition fade = new FadeTransition();
		
		fade.setDuration(Duration.millis(1500));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		
		fade.setNode(fadeRectangle);
		fade.setNode(loginRectangle);
		fade.play();
		fade.setNode(button_AddUser);
		fade.setNode(usernameField);
		fade.setNode(passwordField1);
		fade.setNode(passwordField2);
		fade.setNode(koalaPic);
		fade.setNode(label_AddUser1);
		fade.setNode(label_AddUser2);
		fade.setNode(label_AddUser3);
		fade.setNode(label_AddUser4);
		fade.setNode(label_AddUser4);
		fade.setNode(addUserLabel);
		fade.setNode(backButton);
		
		fade.play();
		
		
	}
}
