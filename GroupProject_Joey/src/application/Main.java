package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			// Gives any other controllers access to the primary stage
			stage = primaryStage;
			// Connect to XFML document and load in
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation( Main.class.getResource("view/Main.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();
			
			stage.getIcons().add(new Image("file:data/Joey.png"));
			
			// Put layout onto the scene
			Scene scene = new Scene(layout);

			// Set scene to the stage
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
