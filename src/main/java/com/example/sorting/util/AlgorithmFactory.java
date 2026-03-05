package com.example.sorting.util;

import com.example.sorting.algorithms.*;

public class AlgorithmFactory {

    public static SortingAlgorithms createAlgorithm(String name) {
        switch (name) {
            case "Selection Sort":
                return new SelectionSort();
            case "Insertion Sort":
                return new InsersionSort(); // نفس السبلينج بتاعك
            case "Bubble Sort":
                return new BubbleSort();
            case "Merge Sort":
                return new MergeSort();
            case "Heap Sort":
                return new HeapSort();
            case "Quick Sort":
                return new QuickSort();
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
    }
}