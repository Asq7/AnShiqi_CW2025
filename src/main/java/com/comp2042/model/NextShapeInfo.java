package com.comp2042.model;

import com.comp2042.core.MatrixOperations;
/**
 * Represents information about the next shape of a brick including its matrix and position
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final int position;

    /**
     * Constructs a NextShapeInfo with the specified shape matrix and position
     * @param shape the shape matrix of the next rotation state
     * @param position the position index of the next rotation state
     */
    public NextShapeInfo(final int[][] shape, final int position) {
        this.shape = shape;
        this.position = position;
    }
    /**
     * Gets a copy of the shape matrix
     * @return a deep copy of the shape matrix
     */
    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

    /**
     * Gets the position index
     * @return the position index of the next rotation state
     */
    public int getPosition() {
        return position;
    }
}
