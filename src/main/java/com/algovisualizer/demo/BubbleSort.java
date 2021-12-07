package com.algovisualizer.demo;

import com.algovisualizer.model.Algorithm;
import com.algovisualizer.model.Step;
import com.algovisualizer.model.StepNode;

class BubbleSort extends Algorithm {
    public BubbleSort(int[] nodes) {
        super(nodes);
    }

    protected void applySteps(int[] nodes) {
        Step step = new Step(nodes);
        addStep(step);

        for (int i = 0; i < nodes.length - 1; i++) {
            for (int j = 0; j < nodes.length - 1 - i; j++) {
                if (step.get(j).val() > step.get(j + 1).val()) {
                    step = new Step(step.stream().toList());
                    // swap positions of step[j] and step[j+1]
                    StepNode a = step.get(j);
                    StepNode b = step.get(j + 1);
                    step.set(j, b);
                    step.set(j + 1, a);
                    // add the step
                    addStep(step);
                }
            }
        }
    }
}
