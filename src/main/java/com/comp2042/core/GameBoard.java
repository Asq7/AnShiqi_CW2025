package com.comp2042.core;

import com.comp2042.bricks.Brick;
import com.comp2042.bricks.BrickGenerator;
import com.comp2042.bricks.impl.RandomBrickGenerator;
import com.comp2042.model.NextShapeInfo;
import com.comp2042.model.ViewData;

import java.awt.*;
/**
 * Implementation of the game board
 */
public class GameBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final GameScore gameScore;
    /**
     * Constructs a GameBoard with the specified width and height
     * @param width the width of the game board
     * @param height the height of the game board
     */
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        gameScore = new GameScore();
    }
    /**
     * Moves the current brick down by one unit
     * @return true if the move was successful, false if collision detected
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Moves the current brick to the left by one unit
     * @return true if the move was successful, false if collision detected
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    /**
     * Moves the current brick to the right by one unit
     * @return true if the move was successful, false if collision detected
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    /**
     * Rotates the current brick counterclockwise
     * @return true if the rotation was successful, false if collision detected
     */
    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }
    /**
     * Creates a new brick at the top of the board
     * @return true if collision detected immediately, false otherwise
     */
    @Override
    public boolean createNewBrick() {
        Brick currentBrick = brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);
        currentOffset = new Point(4, 10);
        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
    /**
     * Gets the matrix representation of the board
     * @return a 2D integer array representing the board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }
    /**
     * Gets view-related data for rendering the board
     * @return a ViewData object containing display information
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY(), brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }
    /**
     * Merges the current brick into the background grid
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, brickRotator.getCurrentShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }
    /**
     * Clears any completed rows on the board
     * @return a ClearRow object indicating which rows were cleared
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    /**
     * Gets the current score of the game
     * @return the GameScore object representing the player's score
     */
    @Override
    public GameScore getScore() {
        return gameScore;
    }

    /**
     * Resets the board and starts a new game
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        gameScore.reset();
        createNewBrick();
    }
}
