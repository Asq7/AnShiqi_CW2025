package com.comp2042.controller;

import com.comp2042.core.ClearRow;
import com.comp2042.core.Board;
import com.comp2042.core.GameBoard;
import com.comp2042.model.DownData;
import com.comp2042.model.EventSource;
import com.comp2042.model.MoveEvent;
import com.comp2042.model.ViewData;
import javafx.beans.property.IntegerProperty;
import com.comp2042.util.GameConfig;

/**
 * The controller class for the game
 * GameController handles game logic and acts as intermediary between model and view
 */
public class GameController implements GameInputHandler {

    /**
     * The board instance used for game logic
     */
    private Board board = new GameBoard(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);

    private final GuiController viewGuiController;

    /**
     * Constructs a new GameController with the specified view controller
     * @param c The view controller to use
     */
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
     * Handles the rotated movement event for the current brick
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

    /**
     * Returns the current board instance
     * @return The current board instance
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the next brick data at the specified position in the queue
     * @param n The position of the next brick in the queue
     * @return The next brick data at the specified position
     */
    @Override
    public int[][] getNextBrickData(int n) {
        return board.getNextBrickData(n);
    }
}
