package com.comp2042.core;

import com.comp2042.model.ViewData;
/**
 * Represents the game board
 */
public interface Board {
    /**
     * Moves the current brick down by one unit.
     * @return true if the move was successful, false otherwise.
     */
    boolean moveBrickDown();
    /**
     * Moves the current brick to the left by one unit.
     * @return true if the move was successful, false otherwise.
     */
    boolean moveBrickLeft();
    /**
     * Moves the current brick to the right by one unit.
     * @return true if the move was successful, false otherwise.
     */
    boolean moveBrickRight();
    /**
     * Rotates the current brick counterclockwise.
     * @return true if the rotation was successful, false otherwise.
     */
    boolean rotateLeftBrick();

    /**
     * Creates a new brick at the top of the board.
     * @return true if the brick was created successfully, false otherwise.
     */
    boolean createNewBrick();
    /**
     * Gets the matrix representation of the board.
     * @return a 2D integer array representing the board state.
     */
    int[][] getBoardMatrix();

    /**
     * Gets view-related data for rendering the board.
     * @return a {@link ViewData} object containing display information.
     */
    ViewData getViewData();
    /**
     * Merges the current brick into the background grid.
     */
    void mergeBrickToBackground();
    /**
     * Clears any completed rows on the board.
     * @return an instance of {@link ClearRow} indicating which rows were cleared.
     */
    ClearRow clearRows();
    /**
     * Gets the current score of the game.
     * @return the {@link GameScore} object representing the player's score.
     */
    GameScore getScore();

    /**
     * Resets the board and starts a new game.
     */
    void newGame();
}
