package com.example.sorting.algorithms;

import java.util.List;

public class SortResult {
    private final long runTimeMs;
    private final long comparisons;
    private final long interchanges;
    private final List<sortStep> visualizationSteps;

    public SortResult(long runTimeMs, long comparisons, long interchanges, List<sortStep> visualizationSteps) {
        this.runTimeMs = runTimeMs;
        this.comparisons = comparisons;
        this.interchanges = interchanges;
        this.visualizationSteps = visualizationSteps;
    }

    public long getRunTimeMs() {
        return runTimeMs/1000000;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getInterchanges() {
        return interchanges;
    }

    public List<sortStep> getVisualizationSteps() {
        return visualizationSteps;
    }


}
