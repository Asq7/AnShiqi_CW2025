package com.comp2042.bricks;

import java.util.List;

/**
 * defines the interface for accessing the brick's shape data
 */
public interface Brick {

    /**
     * Gets all possible rotation states of the brick as a list of matrices.
     * @return the brick's shape data
     */
    List<int[][]> getShapeMatrix();
}
