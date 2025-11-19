package com.comp2042.bricks.impl.shapes;

import com.comp2042.core.MatrixOperations;
import com.comp2042.bricks.Brick;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the J-shaped brick
 */
public final class JBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();
    /**
     * Initializes the J-brick with its FOUR rotation states
     */
    public JBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {2, 2, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 2},
                {0, 0, 0, 0}
        });
        brickMatrix.add(new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
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
