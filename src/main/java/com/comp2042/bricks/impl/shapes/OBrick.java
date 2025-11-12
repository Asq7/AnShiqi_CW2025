package com.comp2042.bricks.impl.shapes;

import com.comp2042.model.MatrixOperations;
import com.comp2042.bricks.Brick;

import java.util.ArrayList;
import java.util.List;

final class OBrick implements Brick {

    private final List<int[][]> brickMatrix = new ArrayList<>();

    /**
     * Constructor for OBrick initializes the brick matrix with the O-shaped tetromino pattern.
     * The O-shape is a 2x2 square block represented in a 4x4 matrix.
     * The pattern uses '4' to indicate filled cells and '0' for empty spaces.
     */
    public OBrick() {
        brickMatrix.add(new int[][]{
                {0, 0, 0, 0},
                {0, 4, 4, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0}
        });
    }
    /**
     * 获取当前砖块的形状矩阵列表
     *
     * @return 返回砖块矩阵的深拷贝列表，每个元素代表砖块的一个旋转状态
     */
    @Override
    public List<int[][]> getShapeMatrix() {
        return MatrixOperations.deepCopyList(brickMatrix);
    }

}
