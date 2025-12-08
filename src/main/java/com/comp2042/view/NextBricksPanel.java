package com.comp2042.view;

import com.comp2042.controller.GameInputHandler;
import com.comp2042.model.ViewData;
import com.comp2042.util.ColorUtility;
import com.comp2042.util.GameConfig;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * Panel that displays the next brick previews for the player to visualize the upcoming rotations
 */
public class NextBricksPanel {

    private final GridPane[] previewPanels;
    private final Rectangle[][][] previewRectangles;
    private GameInputHandler eventListener;

    /**
     * Constructs a NextBricksPanel with the specified preview panels
     * @param previewPanels the array of GridPane objects to display the next brick previews
     */
    public NextBricksPanel(GridPane[] previewPanels) {
        this.previewPanels = previewPanels;
        this.previewRectangles = new Rectangle[3][][];
        initializePanels();
    }

    /**
     * Set the input event listener for handling user interactions with the preview panels
     * @param eventListener the GameInputHandler object to handle user input events
     */
    public void setEventListener(GameInputHandler eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Initialize the preview panels with empty rectangles
     */
    private void initializePanels() {
        for (int i = 0; i < previewPanels.length; i++) {
            previewPanels[i].getChildren().clear();
        }
    }

    /**
     * Initialize the three next brick previews with the given ViewData
     * @param brick the ViewData object containing the next brick data
     */
    public void initThreeNextBrickPreviews(ViewData brick) {
        for (GridPane panel : previewPanels) {
            panel.getChildren().clear();
        }

        int[][] nextBrickData1 = brick.getNextBrickData();
        if (nextBrickData1.length > 0 && nextBrickData1[0].length > 0) {
            previewRectangles[0] = new Rectangle[nextBrickData1.length][nextBrickData1[0].length];
            initBrickPanel(previewRectangles[0], previewPanels[0], nextBrickData1);
        }

        int[][] nextBrickData2 = getNextBrickNData(2);
        if (nextBrickData2.length > 0 && nextBrickData2[0].length > 0) {
            previewRectangles[1] = new Rectangle[nextBrickData2.length][nextBrickData2[0].length];
            initBrickPanel(previewRectangles[1], previewPanels[1], nextBrickData2);
        }

        int[][] nextBrickData3 = getNextBrickNData(3);
        if (nextBrickData3.length > 0 && nextBrickData3[0].length > 0) {
            previewRectangles[2] = new Rectangle[nextBrickData3.length][nextBrickData3[0].length];
            initBrickPanel(previewRectangles[2], previewPanels[2], nextBrickData3);
        }
    }

    /**
     * Update the three next brick previews with the given ViewData
     * @param brick the ViewData object containing the next brick data
     */
    public void updateThreeNextBrickPreviews(ViewData brick) {
        // 更新下一个方块预览
        updateNextBrickPreview(brick.getNextBrickData(), previewRectangles[0]);
        updateNextBrickPreview(getNextBrickNData(2), previewRectangles[1]);
        updateNextBrickPreview(getNextBrickNData(3), previewRectangles[2]);
    }

    /**
     * Get the next brick data for the specified index
     * @param n the index of the next brick to retrieve (0-2)
     * @return the next brick data as a 2D array of integers
     */
    private int[][] getNextBrickNData(int n) {
        if (eventListener != null) {
            return eventListener.getNextBrickData(n);
        }
        return new int[4][4];
    }

    /**
     * Initialize the preview panel with the given brick data
     * @param rectangles the rectangle array
     * @param brickPanel the grid pane to add the rectangles to
     * @param brickData the brick data to initialize the panel with
     */
    private void initBrickPanel(Rectangle[][] rectangles, GridPane brickPanel, int[][] brickData) {
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(GameConfig.BRICK_SIZE, GameConfig.BRICK_SIZE);
                rectangle.setFill(ColorUtility.getFillColor(brickData[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
    }

    /**
     * Update the preview of the next brick
     * @param nextBrickData the next brick data
     * @param rectangles the rectangle array
     */
    private void updateNextBrickPreview(int[][] nextBrickData, Rectangle[][] rectangles) {
        if (rectangles != null && nextBrickData != null) {
            for (int i = 0; i < nextBrickData.length && i < rectangles.length; i++) {
                for (int j = 0; j < nextBrickData[i].length && j < rectangles[i].length; j++) {
                    setRectangleData(nextBrickData[i][j], rectangles[i][j]);
                }
            }
        }
    }



    /**
     * Set the fill color and rounded corners of a rectangle
     * @param color the cell type identifier (0-7)
     * @param rectangle the rectangle object
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(ColorUtility.getFillColor(color));
        rectangle.setArcHeight(GameConfig.ARC_RADIUS);
        rectangle.setArcWidth(GameConfig.ARC_RADIUS);
    }

    /**
     * Clear all previews
     */
    public void clearAllPreviews() {
        for (GridPane panel : previewPanels) {
            panel.getChildren().clear();
        }
    }
}
