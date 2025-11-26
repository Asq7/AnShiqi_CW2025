package com.comp2042.controller;

import com.comp2042.core.Board;
import com.comp2042.model.DownData;
import com.comp2042.model.MoveEvent;
import com.comp2042.model.ViewData;
import javafx.beans.property.IntegerProperty;

/**
 * Interface for the game input handler
 */
public interface GameInputHandler {
    /**
     * Handles the down event
     * @param event The MoveEvent containing event details
     * @return DownData containing clear row information and view data
     */
    DownData onDownEvent(MoveEvent event);
    /**
     * Handles the left event
     * @param event The MoveEvent containing event details
     * @return ViewData containing view data
     */
    ViewData onLeftEvent(MoveEvent event);
    /**
     * Handles the right event
     * @param event The MoveEvent containing event details
     * @return ViewData containing view data
     */
    ViewData onRightEvent(MoveEvent event);
    /**
     * Handles rotate event
     * @param event The MoveEvent containing event details
     * @return  ViewData containing view data
     */
    ViewData onRotateEvent(MoveEvent event);
    /**
     * Creates a new game
     */
    void createNewGame();
    /**
     * Gets the board
     * @return The board
     **/
    Board getBoard();
    /**
     * Binds the level property to the view controller
     * @param levelProperty The level property to bind
     */
    void bindLevel(IntegerProperty levelProperty);
}
