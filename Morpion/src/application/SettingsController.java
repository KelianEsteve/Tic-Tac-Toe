package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsController {

	@FXML
	private MenuItem settings;
	@FXML
	private TextArea f1;
	@FXML
	private TextArea f2;
	@FXML
	private TextArea f3;
	@FXML
	private TextArea m1;
	@FXML
	private TextArea m2;
	@FXML
	private TextArea m3;
	@FXML
	private TextArea d1;
	@FXML
	private TextArea d2;
	@FXML
	private TextArea d3;
	@FXML
	private Button validateButton;
	@FXML
	private Button closeButton;
	
	
	public SettingsController() {
		
	}
	
	

	@FXML
	public void getContent() throws IOException {
		String filePath = "resources/config.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] elements = line.split(":");
            char letter = elements[0].charAt(0);
            switch (letter) {
                case 'F':
                    f1.appendText(elements[1] + "\n");
                    f2.appendText(elements[2] + "\n");
                    f3.appendText(elements[3] + "\n");
                    break;
                case 'M':
                    m1.appendText(elements[1] + "\n");
                    m2.appendText(elements[2] + "\n");
                    m3.appendText(elements[3] + "\n");
                    break;
                case 'D':
                    d1.appendText(elements[1] + "\n");
                    d2.appendText(elements[2] + "\n");
                    d3.appendText(elements[3] + "\n");
                    break;
                default:
                    break;
            }
        }
        reader.close();
	}
	
	
	public boolean isNumeric(String text) {
        if (text == null || text.isEmpty())
            return false;
        
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) {
            	if (c != '.')
            		return false;
            }
        }
        return true;
    }
	
	
	public void validateSettings() {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/config.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("resources/config_temp.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                switch (parts[0]) {
                    case "F":
                        if (isNumeric(f1.getText().trim()))
                        	parts[1] = f1.getText().trim();
                        if (isNumeric(f2.getText().trim()))
                        	parts[2] = f2.getText().trim();
                        if (isNumeric(f3.getText().trim()))
                        	parts[3] = f3.getText().trim();
                        break;
                    case "M":
                    	if (isNumeric(m1.getText().trim()))
                        	parts[1] = m1.getText().trim();
                        if (isNumeric(m2.getText().trim()))
                        	parts[2] = m2.getText().trim();
                        if (isNumeric(m3.getText().trim()))
                        	parts[3] = m3.getText().trim();
                        break;
                    case "D":
                    	if (isNumeric(d1.getText().trim()))
                        	parts[1] = d1.getText().trim();
                        if (isNumeric(d2.getText().trim()))
                        	parts[2] = d2.getText().trim();
                        if (isNumeric(d3.getText().trim()))
                        	parts[3] = d3.getText().trim();
                        break;
                }
                writer.write(String.join(":", parts));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Renommer le fichier temporaire en fichier original
        File tempFile = new File("resources/config_temp.txt");
        File configFile = new File("resources/config.txt");
        if (tempFile.exists()) {
            configFile.delete();
            tempFile.renameTo(configFile);
        }
    }

	
	@FXML
	public void viewSettings() throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Settings.fxml"));
	    Parent settingsRoot = loader.load();
	    SettingsController controller = loader.getController();
	    Stage settingsStage = new Stage();
	    settingsStage.setScene(new Scene(settingsRoot, 300, 200));
	    settingsStage.setTitle("Settings");
	    settingsStage.show();
	    
	    controller.getContent();
	}

    
	@FXML
    public void closeSettings() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
}
