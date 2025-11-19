package com.comp2042.core;

import com.comp2042.bricks.Brick;
import com.comp2042.model.NextShapeInfo;
/**
 * Utility class responsible for rotating bricks and managing their shapes
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;
    /**
     * Gets the next shape information for the brick
     * @return NextShapeInfo object containing the next shape matrix and shape index
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }
    /**
     * Gets the current shape matrix
     * @return 2D integer array representing the current shape
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }
    /**
     * Sets the current shape index
     * @param currentShape the shape index to set
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets the brick and resets the current shape index to 0
     * @param brick the brick object to set
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }


}
