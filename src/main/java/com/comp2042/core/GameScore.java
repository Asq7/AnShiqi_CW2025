package com.comp2042.core;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * Represents the game score management system using JavaFX properties
 */
public final class GameScore {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(1);
    /**
     * Gets the score property for binding to UI components
     * @return the IntegerProperty representing the current score
     */
    public IntegerProperty scoreProperty() {
        return score;
    }
    /**
     * Gets the level property for binding to UI components
     * @return the IntegerProperty representing the current level
     */
    public int getLevel() {
        return level.get();
    }
    /**
     * Gets the level property for binding to UI components
     * @return the IntegerProperty representing the current level
     */
    public IntegerProperty levelProperty() {
        return level;
    }

    /**
     * Adds the specified points to the score
     */
    public void add(int points) {
        int oldScore = score.get();
        score.setValue(oldScore + points);

        int newLevel = (oldScore+points) / 100 + 1;
        if (newLevel > level.get()) {
            level.set(newLevel);
        }
    }
    /**
     * Resets the score to zero
     */
    public void reset() {
        score.setValue(0);
    }
}
