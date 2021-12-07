package com.algovisualizer.demo;

import com.algovisualizer.model.Algorithm;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    private int[] createNodes() {
        return new int[] { 3, 3, 4, 4, 7, 2 };
    }

    private Algorithm[] getAlgorithms(int[] nodes) {
        return new Algorithm[] {
                new BubbleSort(nodes)
        };
    }

    private Parent createContent() {
        int[] nodes = createNodes();
        Algorithm[] algorithms = getAlgorithms(nodes);
        Algorithm algorithm = new BubbleSort(nodes);
        Visualizer viz = new Visualizer();
        viz.setAlgorithm(algorithm);
        Button previousStepBtn = new Button("Prev");
        Button nextStepBtn = new Button("Next");

        previousStepBtn.setOnMouseClicked(mouseEvent -> viz.prev());
        nextStepBtn.setOnMouseClicked(mouseEvent -> viz.next());

        BorderPane borderPane = new BorderPane();

        HBox top = new HBox();
        HBox bottom = new HBox();
        VBox left = new VBox();
        VBox right = new VBox();

        top.setMinHeight(50);
        bottom.setMinHeight(50);
        left.setMinWidth(20);
        right.setMinWidth(20);

        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(previousStepBtn, nextStepBtn);

        borderPane.setCenter(viz);
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setLeft(left);
        borderPane.setRight(right);

        return borderPane;
    }

    @Override
    public void start(Stage stage) {
        Parent root = createContent();
        Scene scene = new Scene(root, 800, 640, Color.WHITESMOKE);
        stage.setTitle("Algorithm Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

