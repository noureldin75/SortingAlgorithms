package com.example.sorting.model;

import javafx.beans.property.*;


public class ComparisonResult {

    private final StringProperty algorithm;
    private final IntegerProperty size;
    private final StringProperty mode;
    private final IntegerProperty runs;
    private final DoubleProperty avgTime;
    private final LongProperty minTime;
    private final LongProperty maxTime;
    private final LongProperty comparisons;
    private final LongProperty interchanges;

    public ComparisonResult(String algorithm, int size, String mode, int runs,
            double avgTime, long minTime, long maxTime,
            long comparisons, long interchanges) {
        this.algorithm = new SimpleStringProperty(algorithm);
        this.size = new SimpleIntegerProperty(size);
        this.mode = new SimpleStringProperty(mode);
        this.runs = new SimpleIntegerProperty(runs);
        this.avgTime = new SimpleDoubleProperty(avgTime);
        this.minTime = new SimpleLongProperty(minTime);
        this.maxTime = new SimpleLongProperty(maxTime);
        this.comparisons = new SimpleLongProperty(comparisons);
        this.interchanges = new SimpleLongProperty(interchanges);
    }

    // ── Property accessors (required by PropertyValueFactory) ──

    public String getAlgorithm() {
        return algorithm.get();
    }

    public int getSize() {
        return size.get();
    }

    public String getMode() {
        return mode.get();
    }

    public int getRuns() {
        return runs.get();
    }

    public double getAvgTime() {
        return avgTime.get();
    }

    public long getMinTime() {
        return minTime.get();
    }

    public long getMaxTime() {
        return maxTime.get();
    }

    public long getComparisons() {
        return comparisons.get();
    }

    public long getInterchanges() {
        return interchanges.get();
    }

    public StringProperty algorithmProperty() {
        return algorithm;
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public StringProperty modeProperty() {
        return mode;
    }

    public IntegerProperty runsProperty() {
        return runs;
    }

    public DoubleProperty avgTimeProperty() {
        return avgTime;
    }

    public LongProperty minTimeProperty() {
        return minTime;
    }

    public LongProperty maxTimeProperty() {
        return maxTime;
    }

    public LongProperty comparisonsProperty() {
        return comparisons;
    }

    public LongProperty interchangesProperty() {
        return interchanges;
    }
}
