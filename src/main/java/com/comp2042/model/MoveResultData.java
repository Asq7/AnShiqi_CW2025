package com.comp2042.model;

import com.comp2042.core.ClearRow;
/**
 * Data class that holds information about the result of a down movement event
 */
public final class MoveResultData {
    private final ClearRow clearRow;
    private final GameViewData gameViewData;
    /**
     * Constructs a MoveResultData object with the specified clear row and view data
     * @param clearRow the ClearRow object containing row clearing information
     * @param gameViewData the GameViewData object containing display information
     */
    public MoveResultData(ClearRow clearRow, GameViewData gameViewData) {
        this.clearRow = clearRow;
        this.gameViewData = gameViewData;
    }

    /**
     * Gets the clear row information
     * @return the ClearRow object containing row clearing information
     */
    public ClearRow getClearRow() {
        return clearRow;
    }
    /**
     * Gets the view data information
     * @return the GameViewData object containing display information
     */
    public GameViewData getViewData() {
        return gameViewData;
    }
}
