package com.example.sorting.algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class HeapSort implements SortingAlgorithms {
    private long comparisons = 0;
    private long interchanges = 0;

    @Override
    public SortResult sort(int[] array, boolean recordSteps) {
        long starttime = System.nanoTime();
        this.comparisons = 0;
        this.interchanges = 0;
        ArrayList<sortStep> steps = new ArrayList<>();
        long runtime;
        int n = array.length;

        // MaxHeap Construction
        for (int i = (n / 2) - 1; i >= 0; i--) {
            Max_Heapify(i, n, array, steps, recordSteps);
        }
        while (n - 1 >= 0) {
            int temp = array[0];
            array[0] = array[n - 1];
            array[n - 1] = temp;
            n--;
            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(array).setCurrentidx(0).setCompareidx(n - 1).build());
            }
            this.interchanges++;
            Max_Heapify(0, n, array, steps, recordSteps);
        }

        long endtime = System.nanoTime();
        return new SortResult(endtime - starttime, comparisons, interchanges, steps);
    }

    public void Max_Heapify(int index, int HeapSize, int[] array, ArrayList<sortStep> steps, boolean recordSteps) {
        int l = (2 * index) + 1;
        int r = (2 * index) + 2;
        int n = HeapSize;
        int largest = index;

        if (l <= n - 1) {
            this.comparisons++;
            if (array[l] > array[largest]) {
                largest = l;
            }
        }

        if (recordSteps) {
            steps.add(new sortStep.Builder().setArrayState(array).setCurrentidx(index).setCompareidx(l).build());
        }

        if (r <= n - 1) {
            this.comparisons++;
            if (array[r] > array[largest]) {
                largest = r;
            }
        }

        if (recordSteps) {
            steps.add(new sortStep.Builder().setArrayState(array).setCurrentidx(index).setCompareidx(r).build());
        }

        if (largest != index) {
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            this.interchanges++;
            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(array).setCurrentidx(index).setCompareidx(largest)
                        .build());
            }
            Max_Heapify(largest, n, array, steps, recordSteps);
        }

    }

}
