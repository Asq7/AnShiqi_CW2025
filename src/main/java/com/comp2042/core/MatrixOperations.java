package com.comp2042.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility class providing matrix operations for the game board
 */
public class MatrixOperations {


    //We don't want to instantiate this utility class
    //private MatrixOperations(){

    //}
    /**
     * Checks if a brick intersects with the game matrix at the specified position
     * @param matrix the game board matrix
     * @param brick the brick shape matrix
     * @param x the x-coordinate position
     * @param y the y-coordinate position
     * @return true if intersection detected, false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i]!= 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Checks if the specified coordinates are out of bounds of the matrix
     * @param matrix the game board matrix
     * @param targetX the x-coordinate to check
     * @param targetY the y-coordinate to check
     * @return true if out of bounds, false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        boolean returnValue = true;
        if (targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length) {
            returnValue = false;
        }
        return returnValue;
    }
    /**
     * Creates a deep copy of a 2D integer array
     * @param original the original matrix to copy
     * @return a deep copy of the original matrix
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }
    /**
     * Merges a brick into the game board matrix at the specified position
     * @param filledFields the game board matrix
     * @param brick the brick shape matrix
     * @param x the x-coordinate position
     * @param y the y-coordinate position
     * @return a new matrix with the brick merged into the board
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }
    /**
     * Checks and removes completed rows from the game board
     * @param matrix the game board matrix
     * @return a ClearRow object containing information about removed rows
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }
    /**
     * Creates a deep copy of a list of 2D integer arrays
     * @param list the list of matrices to copy
     * @return a new list containing deep copies of the original matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
