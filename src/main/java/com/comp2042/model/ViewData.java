package com.comp2042.model;

import com.comp2042.core.MatrixOperations;
/**
 * Represents view data for rendering the game board including current brick and next brick information
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final int[][] nextBrickData;
    /**
     * Constructs a ViewData object with the specified brick data, position, and next brick data
     * @param brickData the current brick's shape data
     * @param xPosition the x-coordinate position of the current brick
     * @param yPosition the y-coordinate position of the current brick
     * @param nextBrickData the next brick's shape data for preview
     */
    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }
    /**
     * Gets a copy of the current brick's shape data
     * @return a deep copy of the brick data matrix
     */
    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }
    /**
     * Gets the x-coordinate position of the current brick
     * @return the x-position value
     */
    public int getxPosition() {
        return xPosition;
    }
    /**
     * Gets the y-coordinate position of the current brick
     * @return the y-position value
     */
    public int getyPosition() {
        return yPosition;
    }
    /**
     * Gets a copy of the next brick's shape data for preview
     * @return a deep copy of the next brick data matrix
     */
    public int[][] getNextBrickData() {
        return MatrixOperations.copy(nextBrickData);
    }
}
