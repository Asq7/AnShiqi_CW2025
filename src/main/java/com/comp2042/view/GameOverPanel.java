package com.comp2042.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Panel that displays the game-over message when the game ends
 */
public class GameOverPanel extends BorderPane {
    /**
     * Constructs a GameOverPanel and initializes the game over label
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }

}
