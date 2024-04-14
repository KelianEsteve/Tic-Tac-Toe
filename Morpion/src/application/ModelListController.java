package application;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModelListController {

    @FXML
    private ListView<String> modelListView;

    public void initialize() {
        // Obtenir la liste des fichiers de modèle à partir du dossier
        File folder = new File("./resources/models");
        File[] listOfFiles = folder.listFiles();

        // Créer une liste observable pour stocker les noms de fichiers
        ObservableList<String> modelNames = FXCollections.observableArrayList();

        // Parcourir les fichiers et ajouter les noms à la liste observable
        for (File file : listOfFiles) {
            if (file.isFile()) {
                modelNames.add(file.getName());
            }
        }

        // Remplir la ListView avec les noms des modèles
        modelListView.setItems(modelNames);
    }
    
    @FXML
    private void supprimerElement() {
        // Récupérer l'indice de l'élément sélectionné
        int selectedIndex = modelListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Récupérer le nom du fichier sélectionné
            String selectedModel = modelListView.getSelectionModel().getSelectedItem();
            
            // Supprimer l'élément sélectionné de la ListView
            modelListView.getItems().remove(selectedIndex);
            
            // Supprimer également le fichier correspondant dans le dossier des modèles
            File fileToDelete = new File("./resources/models/" + selectedModel);
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("Le fichier " + selectedModel + " a été supprimé.");
                } else {
                    System.out.println("Impossible de supprimer le fichier " + selectedModel);
                }
            } else {
                System.out.println("Le fichier " + selectedModel + " n'existe pas.");
            }
        } else {
            System.out.println("Aucun élément sélectionné.");
        }
    }

}
