package com.comp2042.core;

import com.comp2042.bricks.BrickInterface;
import com.comp2042.bricks.BrickGeneratorInterface;
import com.comp2042.model.GameViewData;
import com.comp2042.model.NextShapeInfo;

import java.awt.*;
/**
 * Implementation of the game board
 */
public class GameGameBoardInterface implements GameBoardInterface {

    private final int width;
    private final int height;
    private final BrickGeneratorInterface brickGeneratorInterface;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final GameScore gameScore;
    /**
     * Constructs a GameGameBoardInterface with the specified width and height
     * @param width the width of the game board
     * @param height the height of the game board
     */
    public GameGameBoardInterface(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGeneratorInterface = new com.comp2042.bricks.impl.BrickGeneratorInterface();
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
        BrickInterface currentBrickInterface = brickGeneratorInterface.getBrick();
        brickRotator.setBrick(currentBrickInterface);
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
     */
    @Override
    public GameViewData getViewData() {
        int[][] nextBrickData = new int[4][4];
        if (brickGeneratorInterface instanceof com.comp2042.bricks.impl.BrickGeneratorInterface) {
            BrickInterface nextBrickInterface = ((com.comp2042.bricks.impl.BrickGeneratorInterface) brickGeneratorInterface).getNextBrick(1);
            if (nextBrickInterface != null && !nextBrickInterface.getShapeMatrix().isEmpty()) {
                nextBrickData = nextBrickInterface.getShapeMatrix().get(0);
            }
        }

        return new GameViewData(brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                nextBrickData);
    }

    /**
     * Gets the next brick data for a specific position
     * @param position the position of the next brick
     * @return a 2D integer array representing the next brick
     */
    @Override
    public int[][] getNextBrickData(int position) {
        if (brickGeneratorInterface instanceof com.comp2042.bricks.impl.BrickGeneratorInterface) {
            BrickInterface nextBrickInterface = ((com.comp2042.bricks.impl.BrickGeneratorInterface) brickGeneratorInterface).getNextBrick(position);
            if (nextBrickInterface != null && !nextBrickInterface.getShapeMatrix().isEmpty()) {
                return nextBrickInterface.getShapeMatrix().get(0);
            }
        }
        return new int[4][4];
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
