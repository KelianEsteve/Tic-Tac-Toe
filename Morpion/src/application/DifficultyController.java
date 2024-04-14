package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.io.File;
import javafx.scene.effect.DropShadow;

public class DifficultyController {

    @FXML
    private Button btnFacile;

    @FXML
    private Button btnMoyen;

    @FXML
    private Button btnDifficile;

    @FXML
    private Button btnRetour;
    
    private SettingsController settingsController;
	private LearningController learningController;
	
	

    @FXML
    private void choisirFacile() throws IOException {
    	System.out.println("Bouton Facile cliqué !");
        
        ConfigFileLoader cfl = new ConfigFileLoader();
        cfl.loadConfigFile("./resources/config.txt");
        Config config = cfl.get("F");
        
        // Récupération des variables de F
        int h = config.hiddenLayerSize;
        double lr = config.learningRate;
        int l = config.numberOfhiddenLayers;
        
        // Vérification de l'existence du fichier modèle
        String fileName = "model_" + h + "_" + lr + "_" + l + ".ser";
        File file = new File("./resources/models/" + fileName);
        if (file.exists()) {
        	try {
                // Chargement de la vue de la grille de morpion AI depuis le fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MorpionGridAI.fxml"));
                Parent root = loader.load();
                MorpionGridAIController controller = loader.getController();
                controller.setDifficulty("F");
                controller.initializeModelAndDifficulty();
                Scene scene = new Scene(root);

                Stage stage = (Stage) btnFacile.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	
            System.out.println("Le modèle n'existe pas dans le dossier models.");
            try {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("Learning.fxml"));
                Parent root = loader.load();
                LearningController controller = loader.getController();
                controller.setDifficulty("F");
                Scene scene = new Scene(root);
                
                Stage stage = (Stage) btnFacile.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } 
        
    }

    @FXML
    private void choisirMoyen() {
    	System.out.println("Bouton Facile cliqué !");
        
        ConfigFileLoader cfl = new ConfigFileLoader();
        cfl.loadConfigFile("./resources/config.txt");
        Config config = cfl.get("M");
        
        // Récupération des variables de M
        int h = config.hiddenLayerSize;
        double lr = config.learningRate;
        int l = config.numberOfhiddenLayers;
        
        // Vérification de l'existence du fichier modèle
        String fileName = "model_" + h + "_" + lr + "_" + l + ".ser";
        File file = new File("./resources/models/" + fileName);
        if (file.exists()) {
        	try {
                // Chargement de la vue de la grille de morpion AI depuis le fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MorpionGridAI.fxml"));
                Parent root = loader.load();
                MorpionGridAIController controller = loader.getController();
                controller.setDifficulty("M");
                controller.initializeModelAndDifficulty();
                Scene scene = new Scene(root);

                Stage stage = (Stage) btnFacile.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	
            System.out.println("Le modèle n'existe pas dans le dossier models.");
            try {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("Learning.fxml"));
                Parent root = loader.load();
                LearningController controller = loader.getController();
                controller.setDifficulty("M");
                Scene scene = new Scene(root);
                
                Stage stage = (Stage) btnMoyen.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    @FXML
    private void choisirDifficile() {
    	System.out.println("Bouton Facile cliqué !");
        
        ConfigFileLoader cfl = new ConfigFileLoader();
        cfl.loadConfigFile("./resources/config.txt");
        Config config = cfl.get("D");
        
        // Récupération des variables de D
        int h = config.hiddenLayerSize;
        double lr = config.learningRate;
        int l = config.numberOfhiddenLayers;
        
        // Vérification de l'existence du fichier modèle
        String fileName = "model_" + h + "_" + lr + "_" + l + ".ser";
        File file = new File("./resources/models/" + fileName);
        if (file.exists()) {
        	try {
                // Chargement de la vue de la grille de morpion AI depuis le fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MorpionGridAI.fxml"));
                Parent root = loader.load();
                MorpionGridAIController controller = loader.getController();
                controller.setDifficulty("D");
                controller.initializeModelAndDifficulty();
                Scene scene = new Scene(root);

                Stage stage = (Stage) btnDifficile.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	
            System.out.println("Le modèle n'existe pas dans le dossier models.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Learning.fxml"));
                Parent root = loader.load();
                LearningController controller = loader.getController();
                controller.setDifficulty("D");
                Scene scene = new Scene(root);

                Stage stage = (Stage) btnDifficile.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

    @FXML
    private void retour() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainStage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnRetour.getScene().getWindow(); 
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
