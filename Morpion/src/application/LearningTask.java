package application;

import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LearningTask extends Task<MultiLayerPerceptron> {

    private final HashMap<Integer, Coup> mapTrain;
    private final int size;
    private final int h;
    private final double lr;
    private final int l;
    private final boolean verbose;
    private final double epochs;
    private final ProgressBar progressBar;

    public LearningTask(HashMap<Integer, Coup> mapTrain, int size, int h, double lr, int l, boolean verbose, double epochs, ProgressBar progressBar) {
        this.mapTrain = mapTrain;
        this.size = size;
        this.h = h;
        this.lr = lr;
        this.l = l;
        this.verbose = verbose;
        this.epochs = epochs;
        this.progressBar = progressBar;
    }

    @Override
    protected MultiLayerPerceptron call() throws Exception {
        if (verbose) {
            System.out.println();
            System.out.println("START TRAINING ...");
            System.out.println();
        }

        int[] layers = new int[l + 2];
        layers[0] = size;
        for (int i = 0; i < l; i++) {
            layers[i + 1] = h;
        }
        layers[layers.length - 1] = size;

        double error = 0.0;
        MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());

        if (verbose) {
            System.out.println("---");
            System.out.println("Load data ...");
            System.out.println("---");
        }

        for (int i = 1; i < epochs; i++) {
            double progress = (double) i / epochs;
            updateProgress(progress, 1);

            Coup c = null;
            while (c == null)
                c = mapTrain.get((int) (Math.round(Math.random() * mapTrain.size())));

            error += net.backPropagate(c.in, c.out);

            if (i % 10000 == 0 && verbose)
                System.out.println("Error at step " + i + " is " + (error / (double) i));
        }

        if (verbose)
            System.out.println("Learning completed!");
        
        try {
            FileOutputStream fileOut = new FileOutputStream("./resources/models/model.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(net);
            out.close();
            fileOut.close();
            System.out.println("Le modèle a été sauvegardé avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return net;
    }
}
