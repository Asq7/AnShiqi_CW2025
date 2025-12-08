package com.comp2042.controller;

import com.comp2042.core.GameBoardInterface;
import com.comp2042.model.GameViewData;
import com.comp2042.model.MoveResultData;
import com.comp2042.model.GameMoveEvent;
import javafx.beans.property.IntegerProperty;

/**
 * Interface for the game input handler
 */
public interface GameControllerInterface {
    /**
     * Handles the down event
     * @param event The GameMoveEvent containing event details
     * @return MoveResultData containing clear row information and view data
     */
    MoveResultData onDownEvent(GameMoveEvent event);
    /**
     * Handles the left event
     * @param event The GameMoveEvent containing event details
     * @return GameViewData containing view data
     */
    GameViewData onLeftEvent(GameMoveEvent event);
    /**
     * Handles the right event
     * @param event The GameMoveEvent containing event details
     * @return GameViewData containing view data
     */
    GameViewData onRightEvent(GameMoveEvent event);
    /**
     * Handles rotate event
     * @param event The GameMoveEvent containing event details
     * @return  GameViewData containing view data
     */
    /**
     * Handles rotate event
     * @param event The GameMoveEvent containing event details
     * @return  GameViewData containing view data
     */
    GameViewData onRotateEvent(GameMoveEvent event);
    /**
     * Creates a new game
     */
    void createNewGame();
    /**
     * Gets the board
     * @return The board
     **/
    GameBoardInterface getBoard();
    /**
     * Binds the level property to the view controller
     * @param levelProperty The level property to bind
     */
    void bindLevel(IntegerProperty levelProperty);

    /**
     * Gets the next brick data at the specified position in the queue
     * @param n The position of the next brick in the queue
     * @return The next brick data at the specified position
     */
    int[][] getNextBrickData(int n);
}
