package com.comp2042.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorUtility {

    /**
     * Get the fill color of the cell
     * @param i the cell type identifier (0-7)
     * @return the fill color of the cell
     */
    public static Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}
