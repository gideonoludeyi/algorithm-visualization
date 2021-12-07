package com.algovisualizer.demo;

import javafx.event.Event;
import javafx.event.EventType;

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
