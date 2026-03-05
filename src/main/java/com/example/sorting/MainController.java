package com.example.sorting;

import com.example.sorting.algorithms.*;
import com.example.sorting.model.ComparisonResult;
import com.example.sorting.util.*;
import com.example.sorting.util.ArrayGenerator.Arraytype;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    // Comparison Tab Controls
    @FXML private RadioButton rbAutoGenerate, rbLoadFile;
    @FXML private ToggleGroup dataSourceToggle;
    @FXML private VBox dataGenPanel, fileInputPanel;
    @FXML private ComboBox<String> arrayTypeCombo;
    @FXML private TextField arraySizeField, numRunsField;
    @FXML private Button loadFilesBtn, runComparisonBtn, clearResultsBtn;
    @FXML private Label fileStatusLabel;
    @FXML private CheckBox chkSelection, chkInsertion, chkBubble, chkMerge, chkHeap, chkQuick;

    @FXML private TableView<ComparisonResult> resultsTable;
    @FXML private TableColumn<ComparisonResult, String> colAlgorithm, colMode;
    @FXML private TableColumn<ComparisonResult, Integer> colSize, colRuns;
    @FXML private TableColumn<ComparisonResult, Double> colAvgTime;
    @FXML private TableColumn<ComparisonResult, Long> colMinTime, colMaxTime, colComparisons, colSwaps;
    @FXML private ProgressBar comparisonProgress;

    // Visualization Tab Controls
    @FXML private ComboBox<String> vizAlgorithmCombo, vizArrayTypeCombo;
    @FXML private Slider vizSpeedSlider;
    @FXML private Button vizStartBtn, vizPauseBtn, vizGenerateBtn, vizResetBtn;
    @FXML private HBox visualizationPane;
    @FXML private TextField vizArraySizeField;
    @FXML private Label vizComparisonsLabel, vizInterchangesLabel, vizStepLabel;

    // State Variables
    private static final String[] ALGORITHM_NAMES = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Heap Sort", "Quick Sort"};
    private static final String[] ARRAY_TYPE_LABELS = {"Random", "Sorted", "Reversed"};
    private final List<File> loadedFiles = new ArrayList<>();

    private int[] visCurrentArray;
    private int[] visOriginalArray;
    private Timeline sortTimeline;
    private boolean isPaused = false;

    @FXML
    public void initialize() {
        initComparisonTab();
        initVisualizationTab();
    }

    private void initComparisonTab() {
        arrayTypeCombo.getItems().addAll(ARRAY_TYPE_LABELS);
        arrayTypeCombo.getSelectionModel().selectFirst();
        arraySizeField.setText("10000");
        numRunsField.setText("5");

        dataSourceToggle.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            boolean autoGen = newToggle == rbAutoGenerate;
            dataGenPanel.setDisable(!autoGen);
            fileInputPanel.setDisable(autoGen);
            if (autoGen) {
                loadedFiles.clear();
                fileStatusLabel.setText("Status: No files loaded");
            }
        });

        colAlgorithm.setCellValueFactory(new PropertyValueFactory<>("algorithm"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colMode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        colRuns.setCellValueFactory(new PropertyValueFactory<>("runs"));
        colAvgTime.setCellValueFactory(new PropertyValueFactory<>("avgTime"));
        colMinTime.setCellValueFactory(new PropertyValueFactory<>("minTime"));
        colMaxTime.setCellValueFactory(new PropertyValueFactory<>("maxTime"));
        colComparisons.setCellValueFactory(new PropertyValueFactory<>("comparisons"));
        colSwaps.setCellValueFactory(new PropertyValueFactory<>("interchanges"));
    }

    private void initVisualizationTab() {
        vizAlgorithmCombo.getItems().addAll(ALGORITHM_NAMES);
        vizAlgorithmCombo.getSelectionModel().selectFirst();
        vizArrayTypeCombo.getItems().addAll(ARRAY_TYPE_LABELS);
        vizArrayTypeCombo.getSelectionModel().selectFirst();
        vizArraySizeField.setText("50");
    }

    @FXML
    void onLoadFiles(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select integer data files");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text / CSV files", "*.txt", "*.csv"),
                new FileChooser.ExtensionFilter("All files", "*.*"));

        List<File> chosen = chooser.showOpenMultipleDialog(loadFilesBtn.getScene().getWindow());
        if (chosen != null && !chosen.isEmpty()) {
            loadedFiles.clear();
            loadedFiles.addAll(chosen);
            fileStatusLabel.setText("Status: " + chosen.size() + " file(s) loaded");
        }
    }

    @FXML
    void onRunComparison(ActionEvent event) {
        List<String> selectedAlgos = getSelectedAlgorithms();
        if (selectedAlgos.isEmpty()) {
            showAlert("No Algorithm Selected", "Please select at least one sorting algorithm.");
            return;
        }

        int size, runs;
        try {
            size = Integer.parseInt(arraySizeField.getText().trim());
            runs = Integer.parseInt(numRunsField.getText().trim());
        } catch (NumberFormatException ex) {
            showAlert("Invalid Input", "Array size and number of runs must be valid integers.");
            return;
        }

        List<int[]> arraysToSort = new ArrayList<>();
        List<String> arrayLabels = new ArrayList<>();

        if (rbLoadFile.isSelected()) {
            if (loadedFiles.isEmpty()) {
                showAlert("No Files Loaded", "Please browse and load at least one data file.");
                return;
            }
            for (File file : loadedFiles) {
                try {
                    arraysToSort.add(FileHandler.parseFile(file));
                    arrayLabels.add(file.getName());
                } catch (IOException | NumberFormatException ex) {
                    showAlert("File Error", "Failed to read: " + file.getName() + "\n" + ex.getMessage());
                    return;
                }
            }
        } else {
            String mode = arrayTypeCombo.getValue();
            arraysToSort.add(ArrayGenerator.generateArray(size, toArraytype(mode)));
            arrayLabels.add(mode);
        }

        comparisonProgress.setVisible(true);
        int totalWork = arraysToSort.size() * selectedAlgos.size();
        int workDone = 0;

        for (int arrIdx = 0; arrIdx < arraysToSort.size(); arrIdx++) {
            int[] baseArray = arraysToSort.get(arrIdx);
            String label = arrayLabels.get(arrIdx);

            for (String algoName : selectedAlgos) {
                long totalTime = 0, totalComparisons = 0, totalInterchanges = 0;
                long minTime = Long.MAX_VALUE, maxTime = Long.MIN_VALUE;

                for (int r = 0; r < runs; r++) {
                    SortingAlgorithms algo = AlgorithmFactory.createAlgorithm(algoName);
                    SortResult result = algo.sort(baseArray.clone(), false);

                    long t = result.getRunTimeMs();
                    totalTime += t;
                    if (t < minTime) minTime = t;
                    if (t > maxTime) maxTime = t;
                    totalComparisons += result.getComparisons();
                    totalInterchanges += result.getInterchanges();
                }

                resultsTable.getItems().add(new ComparisonResult(
                        algoName, baseArray.length, label, runs,
                        (double) totalTime / runs, minTime, maxTime,
                        totalComparisons / runs, totalInterchanges / runs));

                workDone++;
                comparisonProgress.setProgress((double) workDone / totalWork);
            }
        }
        comparisonProgress.setProgress(1.0);
    }

    @FXML
    void onClearResults(ActionEvent event) {
        resultsTable.getItems().clear();
        comparisonProgress.setVisible(false);
        comparisonProgress.setProgress(0);
    }

    @FXML
    void onExportCsv(ActionEvent event) {
        if (resultsTable.getItems().isEmpty()) {
            showAlert("Nothing to Export", "The results table is empty. Run a comparison first.");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Results as CSV");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files", "*.csv"));
        chooser.setInitialFileName("sorting_results.csv");

        File file = chooser.showSaveDialog(resultsTable.getScene().getWindow());
        if (file == null) return;

        try {
            FileHandler.exportCsv(file, resultsTable.getItems());
            showInfo("Export Successful", "Results saved to:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            showAlert("Export Failed", "Could not write file:\n" + ex.getMessage());
        }
    }

    @FXML
    void onVizGenerate(ActionEvent event) {
        stopAnimation();

        int size = 50;
        try {
            size = Math.max(5, Math.min(Integer.parseInt(vizArraySizeField.getText().trim()), 100));
        } catch (NumberFormatException ignored) {}

        visCurrentArray = ArrayGenerator.generateArray(size, toArraytype(vizArrayTypeCombo.getValue()));
        visOriginalArray = visCurrentArray.clone();
        DrawHelper.drawArray(visualizationPane, visCurrentArray);
        resetVisLabels();
    }

    @FXML
    void onVizReset(ActionEvent event) {
        stopAnimation();
        if (visOriginalArray != null) {
            visCurrentArray = visOriginalArray.clone();
            DrawHelper.drawArray(visualizationPane, visCurrentArray);
        }
        resetVisLabels();
    }

    @FXML
    void onVizStart(ActionEvent event) {
        if (isPaused && sortTimeline != null) {
            sortTimeline.play();
            isPaused = false;
            vizPauseBtn.setDisable(false);
            vizStartBtn.setDisable(true);
            return;
        }

        stopAnimation();

        if (visOriginalArray != null) visCurrentArray = visOriginalArray.clone();
        if (visCurrentArray == null) {
            showAlert("No Array", "Please generate an array first.");
            return;
        }

        SortingAlgorithms algo = AlgorithmFactory.createAlgorithm(vizAlgorithmCombo.getValue());
        SortResult result = algo.sort(visCurrentArray.clone(), true);
        List<sortStep> steps = result.getVisualizationSteps();

        if (steps == null || steps.isEmpty()) return;

        vizComparisonsLabel.setText("Comparisons: " + result.getComparisons());
        vizInterchangesLabel.setText("Interchanges: " + result.getInterchanges());

        sortTimeline = new Timeline();
        double delayMs = Math.max(5, 200 - (vizSpeedSlider.getValue() * 1.95));

        for (int i = 0; i < steps.size(); i++) {
            final sortStep step = steps.get(i);
            final int stepNum = i + 1;

            KeyFrame frame = new KeyFrame(Duration.millis(i * delayMs), e -> {
                DrawHelper.drawArrayHighlighted(visualizationPane, step.getArrayState(), step.getCurrentidx(), step.getCompareidx());
                vizStepLabel.setText("Step: " + stepNum + " / " + steps.size());
            });
            sortTimeline.getKeyFrames().add(frame);
        }

        sortTimeline.setOnFinished(e -> {
            DrawHelper.drawArray(visualizationPane, steps.get(steps.size() - 1).getArrayState());
            vizStepLabel.setText("Done ✓  (" + steps.size() + " steps)");
            vizPauseBtn.setDisable(true);
            vizStartBtn.setDisable(false);
            isPaused = false;
        });

        vizPauseBtn.setDisable(false);
        vizStartBtn.setDisable(true);
        sortTimeline.play();
    }

    @FXML
    void onVizPause(ActionEvent event) {
        if (sortTimeline != null) {
            sortTimeline.pause();
            isPaused = true;
            vizPauseBtn.setDisable(true);
            vizStartBtn.setDisable(false);
        }
    }

    private void stopAnimation() {
        if (sortTimeline != null) {
            sortTimeline.stop();
            sortTimeline = null;
        }
        isPaused = false;
        vizPauseBtn.setDisable(true);
        vizStartBtn.setDisable(false);
    }

    private void resetVisLabels() {
        vizComparisonsLabel.setText("Comparisons: 0");
        vizInterchangesLabel.setText("Interchanges: 0");
        vizStepLabel.setText("Step: 0");
    }

    private Arraytype toArraytype(String label) {
        switch (label) {
            case "Sorted": return Arraytype.SORTED;
            case "Reversed": return Arraytype.REVERSED;
            default: return Arraytype.RANDOM;
        }
    }

    private List<String> getSelectedAlgorithms() {
        List<String> selected = new ArrayList<>();
        if (chkSelection.isSelected()) selected.add("Selection Sort");
        if (chkInsertion.isSelected()) selected.add("Insertion Sort");
        if (chkBubble.isSelected()) selected.add("Bubble Sort");
        if (chkMerge.isSelected()) selected.add("Merge Sort");
        if (chkHeap.isSelected()) selected.add("Heap Sort");
        if (chkQuick.isSelected()) selected.add("Quick Sort");
        return selected;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}