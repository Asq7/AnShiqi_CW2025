package com.comp2042.core;

import com.comp2042.bricks.BrickInterface;
import com.comp2042.model.NextShapeInfo;
/**
 * Utility class responsible for rotating bricks and managing their shapes
 */
public class BrickRotator {

    private BrickInterface brickInterface;
    private int currentShape = 0;
    /**
     * Gets the next shape information for the brickInterface
     * @return NextShapeInfo object containing the next shape matrix and shape index
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brickInterface.getShapeMatrix().size();
        return new NextShapeInfo(brickInterface.getShapeMatrix().get(nextShape), nextShape);
    }
    /**
     * Gets the current shape matrix
     * @return 2D integer array representing the current shape
     */
    public int[][] getCurrentShape() {
        return brickInterface.getShapeMatrix().get(currentShape);
    }
    /**
     * Sets the current shape index
     * @param currentShape the shape index to set
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets the brickInterface and resets the current shape index to 0
     * @param brickInterface the brickInterface object to set
     */
    public void setBrick(BrickInterface brickInterface) {
        this.brickInterface = brickInterface;
        currentShape = 0;
    }

    /**
     * Gets the current shape index
     * @return the current shape index
     */
    public int getCurrentShapeIndex() {
        return currentShape;
    }
}
