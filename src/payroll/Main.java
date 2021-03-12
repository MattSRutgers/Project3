package payroll;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * The Main class stes up and runs the primary javafx stage
 * @author Matthew Schilling and Gordon Miller
 * 
 */

public class Main extends Application {
	
	/**
	 * Sets up the javafx stage from an fxml file
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("PayrollGUI.fxml"));
			Scene scene = new Scene(root,450,460);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Payroll Processing");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the javafx stage and the program
	 */
	public static void main(String[] args) {
		launch(args);
	}
}