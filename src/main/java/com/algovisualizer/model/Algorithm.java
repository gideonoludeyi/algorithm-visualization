package com.algovisualizer.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
    private final List<Step> steps = new ArrayList<>();
    private int pointer = 0;

    public Algorithm(int[] nodes) {
        applySteps(nodes);
    }

    abstract protected void applySteps(int[] nodes);

    protected void addStep(Step step) {
        steps.add(step);
    }

    public void setPointer(int i) {
        if (i < 0) return;
        if (i >= steps.size()) return;
        pointer = i;
    }

    public int getPointer() {
        return pointer;
    }

    public Step getCurrentStep() {
        return steps.get(pointer);
    }
}
