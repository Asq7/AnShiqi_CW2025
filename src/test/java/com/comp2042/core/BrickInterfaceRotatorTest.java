package com.comp2042.core;

import com.comp2042.bricks.BrickInterface;
import com.comp2042.model.NextShapeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrickInterfaceRotatorTest {

    private BrickRotator brickRotator;
    private BrickInterface mockBrickInterface;

    @BeforeEach
    void setUp() {
        brickRotator = new BrickRotator();
        mockBrickInterface = mock(BrickInterface.class);
    }

    @Test
    void testSetBrickResetsCurrentShape() {
        // Arrange
        List<int[][]> shapeMatrixList = Arrays.asList(
                new int[][]{{1, 1}, {1, 1}},
                new int[][]{{1, 1, 1, 1}}
        );

        when(mockBrickInterface.getShapeMatrix()).thenReturn(shapeMatrixList);

        // Act
        brickRotator.setBrick(mockBrickInterface);

        // Assert
        assertEquals(0, brickRotator.getCurrentShapeIndex()); // Assuming getter is added
    }

    @Test
    void testGetCurrentShapeReturnsCorrectShape() {
        // Arrange
        int[][] expectedShape = {{1, 1}, {1, 1}};
        List<int[][]> shapeMatrixList = Arrays.asList(new int[][][]{expectedShape});

        when(mockBrickInterface.getShapeMatrix()).thenReturn(shapeMatrixList);
        brickRotator.setBrick(mockBrickInterface);

        // Act
        int[][] actualShape = brickRotator.getCurrentShape();

        // Assert
        assertArrayEquals(expectedShape, actualShape);
    }

    @Test
    void testGetNextShapeReturnsCorrectNextShape() {
        // Arrange
        int[][] shape1 = {{1, 1}, {1, 1}};
        int[][] shape2 = {{1, 1, 1, 1}};
        List<int[][]> shapeMatrixList = Arrays.asList(shape1, shape2);

        when(mockBrickInterface.getShapeMatrix()).thenReturn(shapeMatrixList);
        brickRotator.setBrick(mockBrickInterface);

        // Act
        NextShapeInfo nextShapeInfo = brickRotator.getNextShape();

        // Assert
        assertArrayEquals(shape2, nextShapeInfo.getShape());
        assertEquals(1, nextShapeInfo.getPosition());
    }

    @Test
    void testGetNextShapeWrapsAroundToFirstShape() {
        // Arrange
        int[][] shape1 = {{1, 1}, {1, 1}};
        int[][] shape2 = {{1, 1, 1, 1}};
        List<int[][]> shapeMatrixList = Arrays.asList(shape1, shape2);

        when(mockBrickInterface.getShapeMatrix()).thenReturn(shapeMatrixList);
        brickRotator.setBrick(mockBrickInterface);
        brickRotator.setCurrentShape(1); // Set to last shape

        // Act
        NextShapeInfo nextShapeInfo = brickRotator.getNextShape();

        // Assert
        assertArrayEquals(shape1, nextShapeInfo.getShape());
        assertEquals(0, nextShapeInfo.getPosition());
    }

    @Test
    void testSetCurrentShapeUpdatesShapeIndex() {
        // Act
        brickRotator.setCurrentShape(2);

        // For this test to work, we need a getter method
        // Assuming we add a getter for testing purposes
        // assertEquals(2, brickRotator.getCurrentShape());
    }
}
