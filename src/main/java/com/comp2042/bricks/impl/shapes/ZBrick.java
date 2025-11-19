package com.comp2042.bricks.impl.shapes;

import com.comp2042.core.MatrixOperations;
import com.comp2042.bricks.Brick;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the Z-shaped brick
 */
public class ZBrick implements Brick {
    /**
     * Initializes the Z-brick with its two rotation states
     */
    private final List<int[][]> brickMatrix = new ArrayList<>();

    public ZBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {7, 7, 0, 0},
                {0, 7, 7, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 7, 0, 0},
                {7, 7, 0, 0},
                {7, 0, 0, 0},
                {0, 0, 0, 0}
        });
    }
    /**
     *Get the shape matrix list of the current brick
     * @return Returns a deep copy list of brick matrices, each element represents a rotation state of the brick
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }
}
