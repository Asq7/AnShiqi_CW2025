package com.comp2042.bricks.impl.shapes;

import com.comp2042.core.MatrixOperations;
import com.comp2042.bricks.Brick;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the L-shaped brick
 */
public final class LBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();
    /**
     * Initializes the L-brick with its four rotation states
     */
    public LBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 3},
                {0, 3, 0, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 3, 3, 0},
                {0, 0, 3, 0},
                {0, 0, 3, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 3, 0},
                {3, 3, 3, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 3, 0, 0},
                {0, 3, 0, 0},
                {0, 3, 3, 0},
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
