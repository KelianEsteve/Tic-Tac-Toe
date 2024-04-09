package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class Main extends Application {
    
    private SettingsController settingsController;
    private LearningController learningController;

    @FXML
    private MenuItem settings;
    
    @FXML
    private MenuItem learning;
    
    @FXML
    private Button humanVsHuman;
    
    @FXML
    private Button humanVsAI; 

    @FXML
    private void viewSettings() throws IOException{
        this.settingsController = new SettingsController();
        this.settingsController.viewSettings();
    }
    
    @FXML
    private void viewLearning() throws IOException{
        this.learningController = new LearningController();
        this.learningController.viewLearning();
    }


    @FXML
    private void startHumanVsHumanGame() {
    	// Chargement de la vue de la grille de morpion depuis le fichier FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MorpionGrid.fxml"));
            Parent root = loader.load();

            //Création de la nouvelle scène 
            Scene scene = new Scene(root);

            Stage stage = (Stage) humanVsHuman.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void startHumanVsAIGame() {
        // Code pour démarrer un jeu Humain vs IA
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("MainStage.fxml"));
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Morpion");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
