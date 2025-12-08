package com.comp2042.controller;

import com.comp2042.core.ClearRow;
import com.comp2042.core.GameBoardInterface;
import com.comp2042.core.GameGameBoardInterface;
import com.comp2042.model.GameViewData;
import com.comp2042.model.MoveResultData;
import com.comp2042.model.EventSource;
import com.comp2042.model.GameMoveEvent;
import javafx.beans.property.IntegerProperty;
import com.comp2042.util.GameConfig;

/**
 * The controller class for the game
 * GameController handles game logic and acts as intermediary between model and view
 */
public class GameController implements GameControllerInterface {

    /**
     * The gameBoardInterface instance used for game logic
     */
    private GameBoardInterface gameBoardInterface = new GameGameBoardInterface(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);

    private final GuiController viewGuiController;

    /**
     * Constructs a new GameController with the specified view controller
     * @param c The view controller to use
     */
    public GameController(GuiController c) {
        viewGuiController = c;
        gameBoardInterface.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initGameView(gameBoardInterface.getBoardMatrix(), gameBoardInterface.getViewData());
        viewGuiController.bindScore(gameBoardInterface.getScore().scoreProperty());
    }
    /**
     * Handles the down movement event for the current brick
     * @param event The GameMoveEvent containing event details
     * @return MoveResultData containing clear row information and view data
     */
    @Override
    public MoveResultData onDownEvent(GameMoveEvent event) {
        boolean canMove = gameBoardInterface.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            gameBoardInterface.mergeBrickToBackground();
            clearRow = gameBoardInterface.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                gameBoardInterface.getScore().add(clearRow.getScoreBonus());
            }
            if (gameBoardInterface.createNewBrick()) {
                viewGuiController.gameOver();
            }

            viewGuiController.refreshGameBackground(gameBoardInterface.getBoardMatrix());

        } else {
            if (event.getEventSource() == EventSource.USER) {
                gameBoardInterface.getScore().add(1);
            }
        }
        return new MoveResultData(clearRow, gameBoardInterface.getViewData());
    }
    /**
     * Handles the left movement event for the current brick
     * @param event The GameMoveEvent containing event details
     * @return GameViewData containing updated brick position and data
     */
    @Override
    public GameViewData onLeftEvent(GameMoveEvent event) {
        gameBoardInterface.moveBrickLeft();
        return gameBoardInterface.getViewData();
    }
    /**
     * Handles the right movement event for the current brick
     * @param event The GameMoveEvent containing event details
     * @return GameViewData containing updated brick position and data
     */
    @Override
    public GameViewData onRightEvent(GameMoveEvent event) {
        gameBoardInterface.moveBrickRight();
        return gameBoardInterface.getViewData();
    }
    /**
     * Handles the rotated movement event for the current brick
     * @param event The GameMoveEvent containing event details
     * @return GameViewData containing updated brick position and data
     */
    @Override
    public GameViewData onRotateEvent(GameMoveEvent event) {
        gameBoardInterface.rotateLeftBrick();
        return gameBoardInterface.getViewData();
    }

    /**
     * Creates a new game by resetting the gameBoardInterface state
     */
    @Override
    public void createNewGame() {
        gameBoardInterface.newGame();
        viewGuiController.refreshGameBackground(gameBoardInterface.getBoardMatrix());
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
     * Returns the current gameBoardInterface instance
     * @return The current gameBoardInterface instance
     */
    @Override
    public GameBoardInterface getBoard() {
        return gameBoardInterface;
    }

    /**
     * Returns the next brick data at the specified position in the queue
     * @param n The position of the next brick in the queue
     * @return The next brick data at the specified position
     */
    @Override
    public int[][] getNextBrickData(int n) {
        return gameBoardInterface.getNextBrickData(n);
    }
}
