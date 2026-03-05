package com.example.sorting.util;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DrawHelper {

    public static void drawArray(Pane visualizationPane, int[] array) {
        drawArrayHighlighted(visualizationPane, array, -1, -1);
    }

    public static void drawArrayHighlighted(Pane visualizationPane, int[] array, int currentIdx, int compareIdx) {
        visualizationPane.getChildren().clear();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(visualizationPane.widthProperty());
        clip.heightProperty().bind(visualizationPane.heightProperty());
        visualizationPane.setClip(clip);

        double paneWidth = visualizationPane.getWidth() > 0 ? visualizationPane.getWidth() : 760;
        double paneHeight = visualizationPane.getHeight() > 0 ? visualizationPane.getHeight() : 400;

        double usableWidth = paneWidth - 20;
        double usableHeight = paneHeight - 10;

        int n = array.length;
        int maxValue = 1;
        for (int v : array) {
            if (v > maxValue) maxValue = v;
        }

        double gap = 2;
        double barWidth = Math.max(1, (usableWidth - (n - 1) * gap) / n);

        for (int i = 0; i < n; i++) {
            double barHeight = Math.max(1, ((double) array[i] / maxValue) * usableHeight * 0.92);

            Rectangle rect = new Rectangle(barWidth, barHeight);
            rect.setManaged(false);
            rect.setX(10 + i * (barWidth + gap));
            rect.setY(paneHeight - barHeight - 10);

            if (i == currentIdx) {
                rect.setFill(Color.web("#e74c3c")); // أحمر
            } else if (i == compareIdx) {
                rect.setFill(Color.web("#2ecc71")); // أخضر
            } else {
                rect.setFill(Color.web("#3498db")); // أزرق
            }

            rect.setArcWidth(2);
            rect.setArcHeight(2);
            visualizationPane.getChildren().add(rect);
        }
    }
}