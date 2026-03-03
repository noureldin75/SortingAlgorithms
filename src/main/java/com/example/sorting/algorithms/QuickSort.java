package com.example.sorting.algorithms;

import java.util.ArrayList;
import java.util.List;

public class QuickSort implements SortingAlgorithms {

    @Override
    public SortResult sort(int[] array,boolean recordSteps) {
        long comparisons = 0;
        long interchanges = 0;
        List<sortStep>  steps = new ArrayList<>();
        long startTime = System.nanoTime();

        return null;
    }


    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
