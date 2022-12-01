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
 * This class takes care of input for the login page and directs users to account creation or taking them to their
 * information screen if their username and password is correct.
 * @author Justin Gilberti wye778
 * @author Kimberly Flores skr257
 * UTSA CS 3443 - Group Project
 * Fall 2022
 */
public class MainController implements EventHandler<ActionEvent>, Initializable{
	@FXML
	Label label_Login1;
	@FXML
	Label label_Login2;
	@FXML
	Label label_Login3;
	@FXML
	TextField usernameField;
	@FXML
	PasswordField passwordField;
	@FXML
	Button button_Login;
	@FXML
	Button button_NewAccount;
	@FXML
	Rectangle fadeRectangle;
	@FXML
	Rectangle loginRectangle;
	@FXML
	Label noAccLabel;
	@FXML
	ImageView koalaPic;
	@FXML
	Label addUserLabel;
	
	public static User user;
	
	@Override
	public void handle(ActionEvent event) {
		/* Animations for the different texts to show slowly instead of popping up */
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(1500));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		
		Button sourceButton = (Button) event.getSource();
		String buttonID = sourceButton.getId();
		if(buttonID != null && buttonID.equals("button_Login")) {
			try {
				user = new User();
				user.setUserName(usernameField.getText());
				user.setPassword(passwordField.getText());
				User.loadUsers("data/User_info.csv");
				User.validateUser(user);
				if(user.isValid()) {
					/*
					 * User is a valid user
					 * Load Information.fxml
					 */
					Parent root = FXMLLoader.load(getClass().getResource("../view/Information.fxml"));
					Main.stage.setScene( new Scene(root, 800, 800) );
					Main.stage.show();
				} else if (user.getUserName().equals("username_empty") || user.getPassword().equals("password_empty")) {
					/*
					 * Set prompts for blank user name or password
					 * Clear user name and password fields
					 */
					label_Login1.setFont(new Font(16.0));
					label_Login1.setText("Username and/or password cannot be blank, please try again following the username and password requirements below:");
					label_Login2.setText("Username: abc123");
					label_Login3.setText("Password: 8-16 charcacters with at least one uppercase letter, one lowercase letter, one number and one special character @$!%*?&");
					fade.setNode(label_Login1);
					fade.play();
					fade.setNode(label_Login2);
					fade.setNode(label_Login3);
					fade.play();
					usernameField.clear();
					passwordField.clear();
				} else if(user.getPassword().equals("password_incorrect")) {
					/*
					 * Set prompt for incorrect password
					 * Clear password field
					 */
					label_Login1.setFont(new Font(16.0));
					label_Login1.setText("Password entered for user name was incorrect, please re-enter password.");
					label_Login2.setText("Username: abc123");
					label_Login3.setText("Password: 8-16 charcacters with at least one uppercase letter, one lowercase letter, one number and one special character @$!%*?&");
					fade.setNode(label_Login1);
					fade.play();
					fade.setNode(label_Login2);
					fade.setNode(label_Login3);
					fade.play();
					passwordField.clear();
				} else if (user.getUserName().equals("create_username")) {
					/*
					 * Set prompt to create new account
					 * Clear user name and password fields
					 */
					label_Login1.setFont(new Font(16.0));
					label_Login1.setText("Username does not exist, please create a new account");
					fade.setNode(label_Login1);
					fade.play();
					usernameField.clear();
					passwordField.clear();
				} else if (user.getUserName().equals("username_format") && user.getPassword().equals("password_format")) {
					/*
					 * Set prompts for invalid regex
					 * Clear user name and password fields
					 */
					label_Login1.setFont(new Font(16.0));
					label_Login1.setText("Username and/or password format incorrect, please try again following the username and password requirements below:");
					label_Login2.setText("Username: abc123");
					label_Login3.setText("Password: 8-16 charcacters with at least one uppercase letter, one lowercase letter, one number and one special character @$!%*?&");
					fade.setNode(label_Login1);
					fade.play();
					fade.setNode(label_Login2);
					fade.setNode(label_Login3);
					fade.play();
					usernameField.clear();
					passwordField.clear();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		} else if(buttonID != null && buttonID.equals("button_NewAccount")) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../view/New_User.fxml"));
				Main.stage.setScene( new Scene(root, 800, 800) );
				Main.stage.show();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		FadeTransition fade = new FadeTransition();
		
		fade.setDuration(Duration.millis(1500));
		fade.setToValue(10);
		fade.setFromValue(0.1);
		
		fade.setNode(fadeRectangle);
		fade.setNode(loginRectangle);
		fade.play();
		fade.setNode(button_Login);
		fade.setNode(button_NewAccount);
		fade.setNode(usernameField);
		fade.setNode(passwordField);
		fade.setNode(koalaPic);
		fade.play();
		fade.setNode(noAccLabel);
		fade.setNode(addUserLabel);
		
	}
}
