package com.comp2042.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * Represents the game score management system using JavaFX properties
 */
public final class GameScore {
    /**
     * Gets the score property for binding to UI components
     * @return the IntegerProperty representing the current score
     */
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    /**
     * Gets the score property for binding to UI components
     * @return the IntegerProperty representing the current score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points to the current score
     * @param i the number of points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }
    /**
     * Resets the score to zero
     */
    public void reset() {
        score.setValue(0);
    }
}
