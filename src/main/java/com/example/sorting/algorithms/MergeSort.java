package com.example.sorting.algorithms;

import java.util.ArrayList;

public class MergeSort implements SortingAlgorithms {

    private long comparisons = 0;
    private long interchanges = 0;

    @Override
    public SortResult sort(int[] array, boolean recordSteps) {

        this.comparisons = 0;
        this.interchanges = 0;

        long starttime = System.nanoTime();
        long runtime;
        ArrayList<sortStep> steps = new ArrayList<>();
        int n = array.length;

        int l = 0;
        int r = n - 1;

        mergeSort(array, l, r, recordSteps, steps);

        long endtime = System.nanoTime();

        return new SortResult(endtime - starttime, comparisons, interchanges, steps);
    }

    void merge(int arr[], int l, int mid, int r, boolean recordSteps, ArrayList<sortStep> steps) {
        int n1 = (mid - l) + 1;
        int n2 = (r - mid);
        int[] array1 = new int[n1 + 1];
        int[] array2 = new int[n2 + 1];

        for (int i = 0; i < n1; i++) {
            array1[i] = arr[l + i];
        }
        for (int i = 0; i < n2; i++) {
            array2[i] = arr[mid + i + 1];
        }

        array1[n1] = Integer.MAX_VALUE;
        array2[n2] = Integer.MAX_VALUE;

        int i = 0;
        int j = 0;
        for (int k = l; k <= r; k++) {

            comparisons++;

            if (array1[i] <= array2[j]) {
                arr[k] = array1[i];
                i++;
            } else {
                arr[k] = array2[j];
                j++;
            }

            interchanges++;

            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(arr)
                        .bounds(l, r)
                        .setCurrentidx(k)
                        .build());
            }
        }
    }

    void mergeSort(int arr[], int l, int r, boolean recordSteps, ArrayList<sortStep> steps) {

        if (l < r) {
            int mid = (l + r) / 2;

            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(arr).bounds(l, mid).build());
            }
            mergeSort(arr, l, mid, recordSteps, steps);

            if (recordSteps) {
                steps.add(new sortStep.Builder().setArrayState(arr).bounds(mid + 1, r).build());
            }
            mergeSort(arr, mid + 1, r, recordSteps, steps);

            merge(arr, l, mid, r, recordSteps, steps);
        }
    }
}