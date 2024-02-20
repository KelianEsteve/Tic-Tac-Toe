package application;
	
import java.io.IOException;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("SceneBuilder.fxml"));
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Morpion");
        primaryStage.show();
      
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
