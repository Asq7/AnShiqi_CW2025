package com.comp2042.controller;

import com.comp2042.core.ClearRow;
import com.comp2042.core.Board;
import com.comp2042.core.GameBoard;
import com.comp2042.model.DownData;
import com.comp2042.model.EventSource;
import com.comp2042.model.MoveEvent;
import com.comp2042.model.ViewData;
import javafx.beans.property.IntegerProperty;

/**
 * The controller class for the game
 * GameController handles game logic and acts as intermediary between model and view
 */
public class GameController implements GameInputHandler {

    private Board board = new GameBoard(25, 10);

    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
    }
    /**
     * Handles the down movement event for the current brick
     * @param event The MoveEvent containing event details
     * @return DownData containing clear row information and view data
     */
    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(board.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }
    /**
     * Handles the left movement event for the current brick
     * @param event The MoveEvent containing event details
     * @return ViewData containing updated brick position and data
     */
    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }
    /**
     * Handles the right movement event for the current brick
     * @param event The MoveEvent containing event details
     * @return ViewData containing updated brick position and data
     */
    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }
    /**
     * Handles the rotate movement event for the current brick
     * @param event The MoveEvent containing event details
     * @return ViewData containing updated brick position and data
     */
    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    /**
     * Creates a new game by resetting the board state
     */
    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
    }
    /**
     * Binds the level property to the view controller
     * @param levelProperty The level property to bind
     */
    @Override
    public void bindLevel(IntegerProperty levelProperty) {
        viewGuiController.bindLevel(levelProperty);
    }
    @Override
    public Board getBoard() {
        return board;
    }
}
