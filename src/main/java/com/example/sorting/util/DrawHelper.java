package com.example.sorting.util;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DrawHelper {

    private static final Color COLOR_ACTIVE    = Color.web("#3498db"); // blue – in-bounds bar
    private static final Color COLOR_CURRENT   = Color.web("#e74c3c"); // red  – current index
    private static final Color COLOR_COMPARE   = Color.web("#2ecc71"); // green – compare index
    private static final Color COLOR_DIMMED    = Color.web("#2c3e50", 0.22); // blank / dimmed – out of bounds
    private static final Color COLOR_BOUNDARY  = Color.web("#f39c12"); // orange – boundary markers

    /* ── convenience wrappers ── */

    public static void drawArray(Pane visualizationPane, int[] array) {
        drawArrayFull(visualizationPane, array, -1, -1, -1, -1);
    }

    public static void drawArrayHighlighted(Pane visualizationPane, int[] array,
                                            int currentIdx, int compareIdx) {
        drawArrayFull(visualizationPane, array, currentIdx, compareIdx, -1, -1);
    }

    /* ── main drawing method that supports merge-sort bounds ── */

    public static void drawArrayFull(Pane visualizationPane, int[] array,
                                     int currentIdx, int compareIdx,
                                     int leftBound, int rightBound) {
        visualizationPane.getChildren().clear();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(visualizationPane.widthProperty());
        clip.heightProperty().bind(visualizationPane.heightProperty());
        visualizationPane.setClip(clip);

        double paneWidth  = visualizationPane.getWidth()  > 0 ? visualizationPane.getWidth()  : 760;
        double paneHeight = visualizationPane.getHeight() > 0 ? visualizationPane.getHeight() : 400;

        double usableWidth  = paneWidth  - 20;
        double usableHeight = paneHeight - 10;

        int n = array.length;
        int maxValue = 1;
        for (int v : array) {
            if (v > maxValue) maxValue = v;
        }

        double gap      = 2;
        double barWidth  = Math.max(1, (usableWidth - (n - 1) * gap) / n);
        boolean hasBounds = (leftBound >= 0 && rightBound >= 0);

        for (int i = 0; i < n; i++) {
            double barHeight = Math.max(1, ((double) array[i] / maxValue) * usableHeight * 0.92);

            Rectangle rect = new Rectangle(barWidth, barHeight);
            rect.setManaged(false);
            rect.setX(10 + i * (barWidth + gap));
            rect.setY(paneHeight - barHeight - 10);

            // ── pick the colour ──
            if (i == currentIdx) {
                rect.setFill(COLOR_CURRENT);
            } else if (i == compareIdx) {
                rect.setFill(COLOR_COMPARE);
            } else if (hasBounds && (i == leftBound || i == rightBound)) {
                rect.setFill(COLOR_BOUNDARY);       // boundary markers
            } else if (hasBounds && (i < leftBound || i > rightBound)) {
                rect.setFill(COLOR_DIMMED);          // outside active sub-array → blank
            } else {
                rect.setFill(COLOR_ACTIVE);          // normal in-bounds bar
            }

            rect.setArcWidth(2);
            rect.setArcHeight(2);
            visualizationPane.getChildren().add(rect);
        }

        // ── draw thin bracket lines at the boundary edges ──
        if (hasBounds && leftBound < n && rightBound < n) {
            double lineY = paneHeight - 4;

            double x1 = 10 + leftBound  * (barWidth + gap);
            double x2 = 10 + rightBound * (barWidth + gap) + barWidth;

            Rectangle bracketLine = new Rectangle(x2 - x1, 3);
            bracketLine.setManaged(false);
            bracketLine.setX(x1);
            bracketLine.setY(lineY);
            bracketLine.setFill(COLOR_BOUNDARY);
            bracketLine.setArcWidth(2);
            bracketLine.setArcHeight(2);
            visualizationPane.getChildren().add(bracketLine);
        }
    }
}