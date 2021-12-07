package com.algovisualizer.demo;

import com.algovisualizer.model.Algorithm;
import com.algovisualizer.model.Step;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Visualizer extends HBox {
    private Algorithm algorithm;
    private ObjectProperty<Step> stepProperty;

    public Visualizer(Algorithm algorithm) {
        super();
        setAlgorithm(algorithm);
        setAlignment(Pos.BOTTOM_LEFT);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;

        if (stepProperty != null) {
            stepProperty.removeListener(this::onStepPropertyChanged);
        }

        stepProperty = new SimpleObjectProperty<>();
        stepProperty.addListener(this::onStepPropertyChanged);

        algorithm.setPointer(0);
        stepProperty.set(algorithm.getCurrentStep());
    }

    private void onStepPropertyChanged(ObservableValue<? extends Step> observableValue, Step prev, Step current) {
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
    }

    public void next() throws NullPointerException {
        algorithm.setPointer(algorithm.getPointer() + 1);
        stepProperty.set(algorithm.getCurrentStep());
    }

    public void prev() throws NullPointerException {
        algorithm.setPointer(algorithm.getPointer() - 1);
        stepProperty.set(algorithm.getCurrentStep());
    }
}
