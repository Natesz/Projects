package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
			Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Monopoly Stats");
			primaryStage.setWidth(880);
			primaryStage.setHeight(680);
			primaryStage.setScene(scene);
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
