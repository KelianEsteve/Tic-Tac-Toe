package application;

import javafx.scene.input.MouseEvent;

import javafx.util.Duration;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;

public class MorpionGridController {
	
	private char currentPlayer = 'X'; // 'X' ou 'O'
    private char[][] grid = new char[3][3];
    private int turn = 0;
    
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
    private Button menuButton;
    
    
    @FXML
    private void backToMenu() {
    	// Chargement de la vue du menu depuis le fichier FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainStage.fxml"));
            Parent root = loader.load();

            //Création de la nouvelle scène 
            Scene scene = new Scene(root);

            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.setScene(scene);
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
        Image image = new Image(getClass().getResourceAsStream("transparent.png"));
        
        cell00Image.setImage(image);
        cell01Image.setImage(image);
        cell02Image.setImage(image);
        
        cell10Image.setImage(image);
        cell11Image.setImage(image);
        cell12Image.setImage(image);
        
        cell20Image.setImage(image);
        cell21Image.setImage(image);
        cell22Image.setImage(image);
        
        
    }


    @FXML
    public void handleCellClick(MouseEvent event) {
    	ImageView cellImage = (ImageView) event.getSource();
        int row = GridPane.getRowIndex(cellImage);
        int col = GridPane.getColumnIndex(cellImage);
        
        if (grid[row][col] == '\0') {
            grid[row][col] = currentPlayer;
            
            // Changer l'image à la fin de la transition
            if (currentPlayer == 'X') {
                cellImage.setImage(new Image(getClass().getResourceAsStream("cross.png")));
            } else {
                cellImage.setImage(new Image(getClass().getResourceAsStream("circle.png")));
            }
            
            // Actualiser le tour
            this.turn++;
            
            // Créer une transition de fondu pour faire apparaître la nouvelle image progressivement
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), cellImage);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();

            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (checkForWinner(currentPlayer)) {
                        // Afficher l'alerte après l'animation
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showAlert("Joueur " + currentPlayer + " a gagné !");
                                resetGame();
                            }
                        });
                    }
                    else {
                        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        if (turn == 9) {
                        	Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    showAlert("Match nul !");
                                    resetGame();
                                }
                            });
                        }
                    }
                }
            });
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
        alert.showAndWait();
    }

    private void resetGame() {
        initialize();
        this.turn = 0;
    }
}
