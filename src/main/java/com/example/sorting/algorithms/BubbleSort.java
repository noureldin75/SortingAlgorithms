package com.example.sorting.algorithms;

import java.util.ArrayList;

import static java.util.Collections.swap;

public class BubbleSort implements SortingAlgorithms {

    @Override
    public SortResult sort(int[] array, boolean recordSteps) {
        long starttime = System.nanoTime();
        long comparisons = 0;
        long interchanges = 0;
        long runtime;
        ArrayList<sortStep> steps = new ArrayList<>();
        int n = array.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    if (recordSteps) {
                        steps.add(new sortStep.Builder().setArrayState(array)
                                .setCurrentidx(j).setCompareidx(j + 1).build());
                    }

                    interchanges++;
                }
                if (recordSteps) {
                    steps.add(new sortStep.Builder().setArrayState(array)
                            .setCurrentidx(j).setCompareidx(j + 1).build());
                }

                comparisons++;
            }

        }
        long endtime = System.nanoTime();
        return new SortResult(endtime - starttime, comparisons, interchanges, steps);
    }
}