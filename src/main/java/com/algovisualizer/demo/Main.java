package com.algovisualizer.demo;

import com.algovisualizer.model.Algorithm;
import com.algovisualizer.model.Step;

import com.algovisualizer.model.StepNode;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class VisualizerStepEvent extends Event {
    public static final EventType<? extends VisualizerStepEvent> ANY = new EventType<>(Event.ANY, "VISUALIZER_STEP");
    public static final EventType<? extends VisualizerStepEvent> NEXT_STEP = new EventType<>(ANY, "VISUALIZER_NEXT_STEP");
    public static final EventType<? extends VisualizerStepEvent> PREVIOUS_STEP = new EventType<>(ANY, "VISUALIZER_PREVIOUS_STEP");

    private VisualizerStepEvent(EventType<? extends VisualizerStepEvent> eventType) {
        super(eventType);
    }

    public static VisualizerStepEvent NextStepEvent() {
        return new VisualizerStepEvent(NEXT_STEP);
    }

    public static VisualizerStepEvent PreviousStepEvent() {
        return new VisualizerStepEvent(PREVIOUS_STEP);
    }
}

class Visualizer extends HBox {
    private final ObjectProperty<Step> stepProperty;

    public Visualizer(Algorithm algorithm) {
        super();
        stepProperty = new SimpleObjectProperty<>();

        stepProperty.addListener((observableValue, prev, current) -> {
            // Alternative: animate between prev and current steps
            List<Rectangle> bars;
            if (prev != null) {
                bars = IntStream.range(0, current.size())
                        .mapToObj(idx -> {
                            Color color = Color.BLUE;
                            if (prev.get(idx) != current.get(idx)) // color code nodes that moved
                                color = Color.GREEN;
                            return new Rectangle(20, current.get(idx).val() * 20.0 + 20, color);
                        }).collect(Collectors.toList());
            } else {
                bars = current.stream()
                        .map(node -> new Rectangle(20, node.val() * 20.0 + 20, Color.BLUE))
                        .collect(Collectors.toList());
            }

            spacingProperty().bind(Bindings.divide(widthProperty(), bars.size() + 1));
            getChildren().setAll(bars);
        });

        addEventHandler(VisualizerStepEvent.NEXT_STEP, nextStepEvent -> {
            algorithm.setPointer(algorithm.getPointer() + 1);
            stepProperty.set(algorithm.getCurrentStep());
        });

        addEventHandler(VisualizerStepEvent.PREVIOUS_STEP, previousStepEvent -> {
            algorithm.setPointer(algorithm.getPointer() - 1);
            stepProperty.set(algorithm.getCurrentStep());
        });

        stepProperty.set(algorithm.getCurrentStep());
        setAlignment(Pos.BOTTOM_LEFT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void next() {
        fireEvent(VisualizerStepEvent.NextStepEvent());
    }

    public void prev() {
        fireEvent(VisualizerStepEvent.PreviousStepEvent());
    }
}

public class Main extends Application {
    private int[] createNodes() {
        return new int[] { 3, 3, 4, 4, 7, 2 };
    }

    private Parent createContent() {
        int[] nodes = createNodes();
        Algorithm algorithm = new BubbleSort(nodes);
        Visualizer viz = new Visualizer(algorithm);
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

class BubbleSort extends Algorithm {
    public BubbleSort(int[] nodes) {
        super(nodes);
    }

    protected void applySteps(int[] nodes) {
        Step step = new Step(nodes);
        addStep(step);

        for (int i = 0; i < nodes.length - 1; i++) {
            for (int j = 0; j < nodes.length - 1 - i; j++) {
                if (step.get(j).val() > step.get(j+1).val()) {
                    step = new Step(step.stream().toList());
                    // swap positions of step[j] and step[j+1]
                    StepNode a = step.get(j);
                    StepNode b = step.get(j+1);
                    step.set(j, b);
                    step.set(j+1, a);
                    // add the step
                    addStep(step);
                }
            }
        }
    }
}