package com.comp2042.core;
/**
 * Represents the result of clearing completed rows on the game board
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;
    /**
     * Constructs a ClearRow object with the specified parameters
     * @param linesRemoved the number of lines that were removed
     * @param newMatrix the updated board matrix after row removal
     * @param scoreBonus the bonus points awarded for clearing rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }
    /**
     * Gets the number of lines removed
     * @return the count of removed lines
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }
    /**
     * Gets a copy of the new board matrix
     * @return a deep copy of the updated board matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }
    /**
     * Gets the score bonus for clearing rows
     * @return the bonus points awarded
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
