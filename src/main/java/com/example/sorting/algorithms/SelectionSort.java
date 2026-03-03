package com.example.sorting.algorithms;

import java.util.ArrayList;

public class SelectionSort implements SortingAlgorithms {

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
        for (int i = 0; i < n; i++) {
            int min = array[i];
            int minidx = i;
            for (int j = i + 1; j < n; j++) {

                if (array[j] < min) {
                    min = array[j];
                    minidx = j;
                }
                if (recordSteps) {
                    steps.add(new sortStep.Builder().setArrayState(array).setCurrentidx(minidx).setCompareidx(j).build());
                }
                comparisons++;
            }
            if(i!=minidx) {
                int temp = array[i];
                array[i] = min;
                array[minidx] = temp;
                interchanges++;
            }
            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(array).setCurrentidx(i).setCompareidx(minidx).build());

            }

        }
        long endtime = System.nanoTime();
        return new SortResult(endtime - starttime, comparisons, interchanges, steps);
    }


}
