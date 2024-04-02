package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class LearningController {
	
	@FXML
	private Button closeButton;
	@FXML
	private Button launchButton;
	@FXML
	private ProgressBar progressBar;
	
	
	
	@FXML
	public void viewLearning() throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Learning.fxml"));
	    Parent settingsRoot = loader.load();
	    LearningController controller = loader.getController();
	    Stage settingsStage = new Stage();
	    settingsStage.setScene(new Scene(settingsRoot, 400, 300));
	    settingsStage.setTitle("Learning");
	    settingsStage.show();
	}
	
	
	@FXML
    public void closeLearning() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
	
	
	@FXML
	public void lauchTraining() {
		ConfigFileLoader cfl = new ConfigFileLoader();
		cfl.loadConfigFile("./resources/config.txt");
		Config config = cfl.get("F");
		System.out.println("Test.main() : "+config);
		double epochs = 10000 ;
		HashMap<Integer, Coup> mapTrain = loadCoupsFromFile("./resources/train_dev_test/train.txt");
		MultiLayerPerceptron net = learn(9, mapTrain, config.hiddenLayerSize, config.learningRate, config.numberOfhiddenLayers, true, epochs);
	}
	
	
	public static HashMap<Integer, Coup> loadCoupsFromFile(String file){
		System.out.println("loadCoupsFromFile from "+file+" ...");
		HashMap<Integer, Coup> map = new HashMap<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
			String s = "";
			while ((s = br.readLine()) != null) {
				String[] sIn = s.split("\t")[0].split(" ");
				String[] sOut = s.split("\t")[1].split(" ");

				double[] in = new double[sIn.length];
				double[] out = new double[sOut.length];

				for (int i = 0; i < sIn.length; i++) {
					in[i] = Double.parseDouble(sIn[i]);
				}

				for (int i = 0; i < sOut.length; i++) {
					out[i] = Double.parseDouble(sOut[i]);
				}

				Coup c = new Coup(9, "");
				c.in = in ;
				c.out = out ;
				map.put(map.size(), c);
			}
			br.close();
		} 
		catch (Exception e) {
			System.out.println("Test.loadCoupsFromFile()");
			e.printStackTrace();
			System.exit(-1);
		}
		return map ;
	}
	
	
	public static MultiLayerPerceptron learn(int size, HashMap<Integer, Coup> mapTrain, int h, double lr, int l, boolean verbose, double epochs){
		try {
			if ( verbose ) {
				System.out.println();
				System.out.println("START TRAINING ...");
				System.out.println();
			}
			//
			//			int[] layers = new int[]{ size, 128, 128, size };
			int[] layers = new int[l+2];
			layers[0] = size ;
			for (int i = 0; i < l; i++) {
				layers[i+1] = h ;
			}
			layers[layers.length-1] = size ;
			//
			double error = 0.0 ;
			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());

			if ( verbose ) {
				System.out.println("---");
				System.out.println("Load data ...");
				System.out.println("---");
			}
			//TRAINING ...
			for(int i = 0; i < epochs; i++){

				Coup c = null ;
				while ( c == null )
					c = mapTrain.get((int)(Math.round(Math.random() * mapTrain.size())));

				error += net.backPropagate(c.in, c.out);

				if ( i % 10000 == 0 && verbose) System.out.println("Error at step "+i+" is "+ (error/(double)i));
			}
			if ( verbose ) 
				System.out.println("Learning completed!");

			return net ;
		} 
		catch (Exception e) {
			System.out.println("Test.learn()");
			e.printStackTrace();
			System.exit(-1);
		}

		return null ;
	}

}
