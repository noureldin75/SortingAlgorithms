package com.example.sorting.util;

import com.example.sorting.model.ComparisonResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static int[] parseFile(File file) throws IOException {
        List<Integer> nums = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("[,\\s]+");
                for (String token : tokens) {
                    token = token.trim();
                    if (!token.isEmpty()) {
                        nums.add(Integer.parseInt(token));
                    }
                }
            }
        }
        return nums.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void exportCsv(File file, List<ComparisonResult> results) throws IOException {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            pw.println("Algorithm,Size,Mode,Runs,Avg Time (ms),Min Time (ms),Max Time (ms),Comparisons,Interchanges");
            for (ComparisonResult row : results) {
                pw.printf("%s,%d,%s,%d,%.2f,%d,%d,%d,%d%n",
                        row.getAlgorithm(),
                        row.getSize(),
                        row.getMode(),
                        row.getRuns(),
                        row.getAvgTime(),
                        row.getMinTime(),
                        row.getMaxTime(),
                        row.getComparisons(),
                        row.getInterchanges());
            }
        }
    }
}