package com.example.sorting.algorithms;

import java.util.ArrayList;
import java.util.List;

public class QuickSort implements SortingAlgorithms {
    private long comparisons;
    private long interchanges;



    @Override
    public SortResult sort(int[] array,boolean recordSteps) {
        this.comparisons = 0;
        this.interchanges = 0;
        List<sortStep>  steps = new ArrayList<>();
        long startTime = System.nanoTime();
        quicksort(array,0,(array.length)-1,recordSteps,steps);
        long endtime=System.nanoTime();
        return new SortResult(endtime-startTime,comparisons,interchanges,steps);

    }

    public void quicksort(int[] array, int lowIndex, int highIndex, boolean recordSteps, List<sortStep> steps) {
        if (lowIndex < highIndex) {
            int pivotIndex = lomutoPartition(array, lowIndex, highIndex, recordSteps, steps);

            quicksort(array, lowIndex, pivotIndex - 1, recordSteps, steps);
            quicksort(array, pivotIndex + 1, highIndex, recordSteps, steps);
        }
    }

    private int lomutoPartition(int[] array, int lowIndex, int highIndex, boolean recordSteps, List<sortStep> steps) {
        int pivot = array[highIndex];
        int i = lowIndex - 1;

        for (int j = lowIndex; j < highIndex; j++) {
            comparisons++;


            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(array).bounds(lowIndex, highIndex)
                        .setCurrentidx(j)
                        .setCompareidx(highIndex)
                        .build());
            }

            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);

                if (recordSteps && i != j) {
                    steps.add(new sortStep.Builder().setArrayState(array).bounds(lowIndex, highIndex)
                            .setCurrentidx(i)
                            .setCompareidx(j)
                            .build());
                }
            }
        }

        swap(array, i + 1, highIndex);

        if (recordSteps) {
            steps.add(new sortStep.Builder().setArrayState(array).bounds(lowIndex, highIndex)
                    .setCurrentidx(i + 1)
                    .setCompareidx(highIndex)
                    .build());
        }

        return i + 1;
    }
    private void swap(int[] array, int i, int j) {
        if (i == j) return;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        interchanges++;
    }
}