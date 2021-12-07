package com.algovisualizer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Step extends ArrayList<StepNode> {
    public Step(List<StepNode> stepNodes) {
        super();
        addAll(stepNodes);
    }

    public Step(int[] values) {
        this(Arrays.stream(values)
                .mapToObj(StepNode::new)
                .collect(Collectors.toList()));
    }
}