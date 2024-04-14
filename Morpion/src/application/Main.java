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
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;


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
    private void viewModels() throws IOException {
        //charge la vue XML contenant la liste des modèles
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Models.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        
        //crée un nouveau stage pour afficher la vue
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Liste des modèles");
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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
        try {
            // Chargement de la vue de la grille de morpion AI depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MorpionGridAI.fxml"));
            Parent root = loader.load();
            MorpionGridAIController controller = loader.getController();
            Scene scene = new Scene(root);

            Stage stage = (Stage) humanVsAI.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void chooseDifficulty() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Difficulty.fxml"));
            Parent difficultyRoot = loader.load();
            DifficultyController controller = loader.getController();
            Scene currentScene = humanVsAI.getScene();
            currentScene.setRoot(difficultyRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
