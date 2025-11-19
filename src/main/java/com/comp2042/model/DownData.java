package com.comp2042.model;

import com.comp2042.core.ClearRow;
/**
 * Data class that holds information about the result of a down movement event
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;
    /**
     * Constructs a DownData object with the specified clear row and view data
     * @param clearRow the ClearRow object containing row clearing information
     * @param viewData the ViewData object containing display information
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
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
     * @return the ViewData object containing display information
     */
    public ViewData getViewData() {
        return viewData;
    }
}
