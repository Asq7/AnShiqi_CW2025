package com.comp2042.controller;

import com.comp2042.controller.GameController;

import com.comp2042.controller.GuiController;
import com.comp2042.model.DownData;
import com.comp2042.model.EventSource;
import com.comp2042.model.MoveEvent;
import com.comp2042.model.ViewData;
import javafx.beans.property.IntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.comp2042.model.EventType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private GuiController guiController;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameController = new GameController(guiController);
    }

    @Test
    void testConstructorInitializesGame() {
        // Verify that the GUI controller's methods are called during initialization
        verify(guiController).setEventListener(gameController);
        verify(guiController).initGameView(any(int[][].class), any(ViewData.class));
        verify(guiController).bindScore(any(IntegerProperty.class));
    }

    @Test
    void testOnDownEventWithValidMove() {
        MoveEvent event = new MoveEvent(EventType.DOWN, EventSource.USER);

        // Execute the down event
        DownData result = gameController.onDownEvent(event);

        // Verify that the result is not null
        assertNotNull(result);
        // Verify that game over was not triggered
        verify(guiController, never()).gameOver();
    }

    @Test
    void testOnLeftEvent() {
        MoveEvent event = new MoveEvent(EventType.LEFT, EventSource.USER);

        ViewData result = gameController.onLeftEvent(event);

        assertNotNull(result);
    }

    @Test
    void testOnRightEvent() {
        MoveEvent event = new MoveEvent(EventType.RIGHT, EventSource.USER);

        ViewData result = gameController.onRightEvent(event);

        assertNotNull(result);
    }



    @Test
    void testOnRotateEvent() {
        MoveEvent event = new MoveEvent(EventType.ROTATE, EventSource.USER);

        ViewData result = gameController.onRotateEvent(event);

        assertNotNull(result);
    }

    @Test
    void testCreateNewGame() {
        gameController.createNewGame();

        // Verify that the background is refreshed after creating a new game
        verify(guiController).refreshGameBackground(any(int[][].class));
    }

    @Test
    void testBindLevel() {
        IntegerProperty levelProperty = mock(IntegerProperty.class);

        gameController.bindLevel(levelProperty);

        // Verify that the level property is bound in the GUI controller
        verify(guiController).bindLevel(levelProperty);
    }

    @Test
    void testGetBoard() {
        // Verify that we can retrieve the board instance
        assertNotNull(gameController.getBoard());
    }

    @Test
    void testGetNextBrickData() {
        // Test getting next brick data with different values
        int[][] brickData1 = gameController.getNextBrickData(1);
        int[][] brickData2 = gameController.getNextBrickData(2);

        // Both should return arrays (could be empty but not null)
        assertNotNull(brickData1);
        assertNotNull(brickData2);
    }
}
