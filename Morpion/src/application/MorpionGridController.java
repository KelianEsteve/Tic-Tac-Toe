package application;

import javafx.scene.input.MouseEvent;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;



import javafx.fxml.FXMLLoader;

public class MorpionGridController {
	
	private char currentPlayer = 'X'; // 'X' ou 'O'
    private char[][] grid = new char[3][3];
    
    @FXML
    private ImageView cell00Image;

    @FXML
    private ImageView cell01Image;

    @FXML
    private ImageView cell02Image;

    @FXML
    private ImageView cell10Image;

    @FXML
    private ImageView cell11Image;

    @FXML
    private ImageView cell12Image;

    @FXML
    private ImageView cell20Image;

    @FXML
    private ImageView cell21Image;

    @FXML
    private ImageView cell22Image;
    
    @FXML
    private Label turnLabel;
    
    @FXML
    private Button btnRetour;
    
    private Media gridMedia;
    
    private MediaPlayer gridPlayer;
    
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
    
    @FXML
    private void initialize() {
        // Initialisation de la grille avec des cases vides ('\0')
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = '\0';
            }
        }
        
        // Mise en place d'images "vierges" pour rendre les cases cliquable
        Image image = new Image(getClass().getResourceAsStream("Images/transparent.png"));
        
        cell00Image.setImage(image);
        cell01Image.setImage(image);
        cell02Image.setImage(image);
        
        cell10Image.setImage(image);
        cell11Image.setImage(image);
        cell12Image.setImage(image);
        
        cell20Image.setImage(image);
        cell21Image.setImage(image);
        cell22Image.setImage(image);
        
        updateTurnLabel();
        
        String gridFilePath = getClass().getResource("Sounds/gridSound.mp3").toExternalForm();
		gridMedia = new Media(gridFilePath);
		gridPlayer = new MediaPlayer(gridMedia);
    }


    @FXML
    public void handleCellClick(MouseEvent event) {
    	ImageView cellImage = (ImageView) event.getSource();
        int row = GridPane.getRowIndex(cellImage);
        int col = GridPane.getColumnIndex(cellImage);
        
        System.out.println("Row: " + row + ", Col: " + col);


        if (grid[row][col] == '\0') {
            grid[row][col] = currentPlayer;
            
            if (currentPlayer == 'X') {
            	cellImage.setImage(new Image(getClass().getResourceAsStream("Images/cross.png")));
            	playGridSound();
            	
            	FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), cellImage);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            } else {
            	cellImage.setImage(new Image(getClass().getResourceAsStream("Images/circle.png")));
            	playGridSound();
            	
            	FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), cellImage);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }

            if (checkForWinner(currentPlayer)) {
                showAlert("Joueur " + currentPlayer + " a gagné !");
                resetGame();
            }else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                updateTurnLabel();
            }
        }
    }


    private boolean checkForWinner(char player) {
        // Vérification des lignes
        for (int row = 0; row < 3; row++) {
            if (grid[row][0] == player && grid[row][1] == player && grid[row][2] == player) {
                return true;
            }
        }
        
        // Vérification des colonnes
        for (int col = 0; col < 3; col++) {
            if (grid[0][col] == player && grid[1][col] == player && grid[2][col] == player) {
                return true;
            }
        }
        
        // Vérification de la diagonale de haut gauche à bas droite
        if (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player) {
            return true; 
        }
        
        // Vérification de la diagonale de bas gauche à haut droite
        if (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player) {
            return true;
        }
        
        return false;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultat du jeu");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType retourMenuButton = new ButtonType("Retour au menu", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().add(retourMenuButton);

        Button retourMenu = (Button) alert.getDialogPane().lookupButton(retourMenuButton);
        retourMenu.setOnAction(event -> {
        	retourAuMenu();
        });

        alert.showAndWait();
    }
    
    private void retourAuMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainStage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            Scene currentScene = cell00Image.getScene();
            
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.close();
            
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void updateTurnLabel() {
        turnLabel.setText("C'est le tour du joueur " + currentPlayer);
    }

    private void resetGame() {
        initialize();
    }
    
    private void playGridSound() {
    	gridPlayer.setOnEndOfMedia(() -> {
    	    gridPlayer.stop(); // Arrête la lecture
    	    gridPlayer.seek(Duration.ZERO); // Remet la tête de lecture au début
    	    // Vous pouvez ajouter d'autres actions ici, selon vos besoins
    	});
    	
    	gridPlayer.play();
    }
    
}
