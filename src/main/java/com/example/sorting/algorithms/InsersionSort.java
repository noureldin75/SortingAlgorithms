package com.example.sorting.algorithms;

import java.util.ArrayList;

public class InsersionSort implements SortingAlgorithms {

    @Override
    public SortResult sort(int[] array, boolean recordSteps) {
        long starttime = System.nanoTime();
        long comparisons = 0;
        long interchanges = 0;
        long runtime;
        ArrayList<sortStep> steps = new ArrayList<>();
        int n = array.length;
        if (recordSteps) {
            steps.add(new sortStep.Builder().setArrayState(array).build());
        }
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            comparisons += i;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                interchanges++;
                if (recordSteps) {
                    steps.add(new sortStep.Builder().setArrayState(array)
                            .setCurrentidx(j + 1).setCompareidx(j).build());
                }
            }
            array[j + 1] = key;
        }
        long endtime = System.nanoTime();
        return new SortResult(endtime - starttime, comparisons, interchanges, steps);
    }

}
