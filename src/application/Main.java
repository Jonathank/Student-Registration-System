package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/student/student.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(event -> {
				event.consume();
				exit(stage);
				
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void exit(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("YOU ARE ABOUT TO EXIT");
		alert.setTitle("EXIT");
		alert.setContentText("DO YOU WANT TO  EXIT BEFORE SAVING?: ");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			
			stage.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
