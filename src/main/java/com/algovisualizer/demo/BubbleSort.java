package com.algovisualizer.demo;

import com.algovisualizer.model.Algorithm;
import com.algovisualizer.model.Step;
import com.algovisualizer.model.StepNode;

class BubbleSort extends Algorithm {
    public BubbleSort(int[] nodes) {
        super(nodes);
    }

    private void swap(Step step, int i, int j) {
        step = new Step(step);
        // swap positions of step[j] and step[j+1]
        StepNode a = step.get(i);
        StepNode b = step.get(j);
        step.set(i, b);
        step.set(j, a);
        // add the step
        addStep(step);
    }

    @Override
    protected void applySteps(int[] nodes) {
        Step step = new Step(nodes);
        addStep(step);

        for (int i = 0; i < nodes.length - 1; i++) {
            for (int j = 0; j < nodes.length - 1 - i; j++) {
                if (step.get(j).val() > step.get(j + 1).val()) {
                    swap(step, j, j+1);
                }
            }
        }
    }
}
