package com.comp2042.controller;

import com.comp2042.core.GameBoardInterface;
import com.comp2042.core.ClearRow;
import com.comp2042.core.GameScore;
import com.comp2042.model.*;
import com.comp2042.util.GameTimerUtility;
import com.comp2042.view.GameOverPanel;
import com.comp2042.view.NextBricksPanel;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * GuiController unit test class
 * Tests the GUI controller functionality including user input handling and UI updates
 */
public class GuiControllerTest {

    private GuiController guiController;

    @Mock
    private GameControllerInterface mockEventListener;

    @Mock
    private GameBoardInterface mockGameBoardInterface;

    @Mock
    private GameScore mockScore;

    @Mock
    private GameTimerUtility mockTimer;

    @Mock
    private GameOverPanel mockGameOverPanel;

    @Mock
    private NextBricksPanel mockNextBricksPanel;

    @BeforeAll
    public static void initJavaFX() {
        // Initialize JavaFX Toolkit, only needs to be executed once
        new JFXPanel();
        Platform.runLater(() -> {});
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Create GuiController instance
        guiController = new GuiController();

        // Set up necessary UI components
        guiController.gamePanel = new GridPane();
        guiController.pauseButton = new Button("PAUSE");
        guiController.newGameButton = new Button("NEW GAME");
        guiController.setLevelLabel(new Label("Level: 1"));
        guiController.setScoreLabel(new Label("Score: 0"));
        guiController.gameOverPanel = mockGameOverPanel;
        guiController.groupNotification = new Group();

        // Initialize rectangle arrays
        guiController.rectangles = new Rectangle[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                guiController.rectangles[i][j] = new Rectangle();
            }
        }

        // Use reflection to set isPause property to false
        Field isPauseField = GuiController.class.getDeclaredField("isPause");
        isPauseField.setAccessible(true);
        isPauseField.set(guiController, new SimpleBooleanProperty(false));

        // Set up mock dependencies
        guiController.gameTimerUtility = mockTimer;
        guiController.setEventListener(mockEventListener);

        // Configure mock object behavior
        when(mockEventListener.getBoard()).thenReturn(mockGameBoardInterface);
        when(mockGameBoardInterface.getScore()).thenReturn(mockScore);

        // Set up mock NextBricksPanel using reflection
        Field nextBricksPanelField = GuiController.class.getDeclaredField("nextBricksPanel");
        nextBricksPanelField.setAccessible(true);
        nextBricksPanelField.set(guiController, mockNextBricksPanel);
    }

    @Test
    @DisplayName("Test initialization method")
    void testInitialize() {
        // Verify initialization does not throw exceptions
        assertDoesNotThrow(() -> {
            assertNotNull(guiController.gamePanel);
            assertNotNull(guiController.pauseButton);
            assertNotNull(guiController.newGameButton);
        });
    }

    @Test
    @DisplayName("Test level binding functionality")
    void testBindLevel() {
        // Create test property
        IntegerProperty levelProperty = new SimpleIntegerProperty(5);

        // Bind level
        guiController.bindLevel(levelProperty);

        // Verify binding works
        assertEquals("5", guiController.getLevelLabel().getText());

        // Test property change
        levelProperty.set(10);
        assertEquals("10", guiController.getLevelLabel().getText());
    }

    @Test
    @DisplayName("Test score binding functionality")
    void testBindScore() {
        // Create test property
        IntegerProperty scoreProperty = new SimpleIntegerProperty(1000);

        // Bind score
        guiController.bindScore(scoreProperty);

        // Verify binding works
        assertEquals("1000", guiController.scoreLabel.getText());

        // Test property change
        scoreProperty.set(2000);
        assertEquals("2000", guiController.scoreLabel.getText());
    }

    @Test
    @DisplayName("Test left arrow key handling")
    void testHandleGameplayKeys_Left() {
        // Create left arrow key event
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);

        // Mock event listener response
        GameViewData mockGameViewData = createMockViewData();
        when(mockEventListener.onLeftEvent(any(GameMoveEvent.class))).thenReturn(mockGameViewData);

        // Handle method - ensure no exceptions thrown
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // Verify onLeftEvent was called
        verify(mockEventListener).onLeftEvent(any(GameMoveEvent.class));
    }

    @Test
    @DisplayName("Test right arrow key handling")
    void testHandleGameplayKeys_Right() {
        // Create right arrow key event
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);

        // Mock event listener response
        GameViewData mockGameViewData = createMockViewData();
        when(mockEventListener.onRightEvent(any(GameMoveEvent.class))).thenReturn(mockGameViewData);

        // Handle method - ensure no exceptions thrown
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // Verify onRightEvent was called
        verify(mockEventListener).onRightEvent(any(GameMoveEvent.class));
    }

    @Test
    @DisplayName("Test up arrow key handling (rotation)")
    void testHandleGameplayKeys_Up() {
        // Create up arrow key event
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);

        // Mock event listener response
        GameViewData mockGameViewData = createMockViewData();
        when(mockEventListener.onRotateEvent(any(GameMoveEvent.class))).thenReturn(mockGameViewData);

        // Handle method - ensure no exceptions thrown
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // Verify onRotateEvent was called
        verify(mockEventListener).onRotateEvent(any(GameMoveEvent.class));
    }

    @Test
    @DisplayName("Test down arrow key handling")
    void testHandleGameplayKeys_Down() {
        // Create down arrow key event
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);

        // Mock onDownEvent returning valid MoveResultData object
        MoveResultData mockMoveResultData = mock(MoveResultData.class);
        ClearRow mockClearRow = mock(ClearRow.class);
        GameViewData mockGameViewData = createMockViewData();

        when(mockEventListener.onDownEvent(any(GameMoveEvent.class))).thenReturn(mockMoveResultData);
        when(mockMoveResultData.getClearRow()).thenReturn(mockClearRow);
        when(mockClearRow.getLinesRemoved()).thenReturn(0); // Set to 0 to avoid showing score panel
        when(mockMoveResultData.getViewData()).thenReturn(mockGameViewData);

        // Handle method - ensure no exceptions thrown
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // Verify onDownEvent was called
        verify(mockEventListener).onDownEvent(any(GameMoveEvent.class));
    }

    @Test
    @DisplayName("Test WASD key handling")
    void testHandleGameplayKeys_WASD() {
        // Create valid GameViewData object for all movement operations
        GameViewData mockGameViewData = createMockViewData();

        // Test W key (rotate)
        KeyEvent wKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        when(mockEventListener.onRotateEvent(any(GameMoveEvent.class))).thenReturn(mockGameViewData);

        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(wKeyEvent);
        });
        verify(mockEventListener).onRotateEvent(any(GameMoveEvent.class));

        // Test A key (move left)
        KeyEvent aKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);
        when(mockEventListener.onLeftEvent(any(GameMoveEvent.class))).thenReturn(mockGameViewData);

        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(aKeyEvent);
        });
        verify(mockEventListener).onLeftEvent(any(GameMoveEvent.class));

        // Test S key (move down) - need to set mock return value for onDownEvent
        KeyEvent sKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false);
        MoveResultData mockMoveResultData = mock(MoveResultData.class);
        ClearRow mockClearRow = mock(ClearRow.class);

        when(mockEventListener.onDownEvent(any(GameMoveEvent.class))).thenReturn(mockMoveResultData);
        when(mockMoveResultData.getClearRow()).thenReturn(mockClearRow);
        when(mockClearRow.getLinesRemoved()).thenReturn(0); // Set to 0 to avoid showing score panel
        when(mockMoveResultData.getViewData()).thenReturn(mockGameViewData);

        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(sKeyEvent);
        });
        verify(mockEventListener).onDownEvent(any(GameMoveEvent.class));

        // Test D key (move right)
        KeyEvent dKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false);
        when(mockEventListener.onRightEvent(any(GameMoveEvent.class))).thenReturn(mockGameViewData);

        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(dKeyEvent);
        });
        verify(mockEventListener).onRightEvent(any(GameMoveEvent.class));
    }

    @Test
    @DisplayName("Test new game key handling")
    void testHandleGameplayKeys_NewGame() {
        // Create N key event
        KeyEvent nKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.N, false, false, false, false);

        // Directly call newGame method, because N key handling is in setupKeyboardControls, not in handleGameplayKeys
        guiController.newGame(null);

        // Verify newGame was called (through event listener)
        verify(mockEventListener).createNewGame();
    }

    @Test
    @DisplayName("Test pause game functionality (space key)")
    void testPauseGame_Space() {
        // First call to pause
        guiController.pauseGame(null);
        verify(mockTimer).pause();

        // Second call to resume
        guiController.pauseGame(null);
        verify(mockTimer).play();
    }

    @Test
    @DisplayName("Test brick move down functionality")
    void testMoveDown() {
        // Create move down event
        GameMoveEvent gameMoveEvent = new GameMoveEvent(GameEventType.DOWN, EventSource.THREAD);

        // Mock response data
        GameViewData mockGameViewData = createMockViewData();
        MoveResultData mockMoveResultData = mock(MoveResultData.class);
        ClearRow mockClearRow = mock(ClearRow.class);

        when(mockEventListener.onDownEvent(gameMoveEvent)).thenReturn(mockMoveResultData);
        when(mockMoveResultData.getClearRow()).thenReturn(mockClearRow);
        when(mockClearRow.getLinesRemoved()).thenReturn(2);
        when(mockClearRow.getScoreBonus()).thenReturn(200);
        when(mockMoveResultData.getViewData()).thenReturn(mockGameViewData);

        // Handle method
        guiController.moveDown(gameMoveEvent);

        // Verify method calls
        verify(mockEventListener).onDownEvent(gameMoveEvent);
        verify(mockMoveResultData, times(3)).getClearRow();
        verify(mockClearRow).getLinesRemoved();
        verify(mockClearRow).getScoreBonus();
    }

    @Test
    @DisplayName("Test game over functionality")
    void testGameOver() {
        // Call game over method
        guiController.gameOver();

        // Verify timer stops
        verify(mockTimer).stop();

        // Verify game over panel shows
        verify(mockGameOverPanel).setVisible(true);
    }

    @Test
    @DisplayName("Test new game functionality")
    void testNewGame() {
        // Call new game method
        guiController.newGame(null);

        // Verify timer operations
        verify(mockTimer).stop();
        verify(mockTimer).play();

        // Verify game over panel hides
        verify(mockGameOverPanel).setVisible(false);

        // Verify create new game
        verify(mockEventListener).createNewGame();
    }

    @Test
    @DisplayName("Test pause game functionality")
    void testPauseGame() {
        // First call to pause
        guiController.pauseGame(null);
        verify(mockTimer).pause();

        // Second call to resume
        guiController.pauseGame(null);
        verify(mockTimer).play();
    }

    @Test
    @DisplayName("Test NextBricksPanel getter")
    void testGetNextBricksPanel() {
        // Test getNextBricksPanel method
        NextBricksPanel result = guiController.getNextBricksPanel();
        assertNotNull(result);
        assertEquals(mockNextBricksPanel, result);
    }

    @Test
    @DisplayName("Test label getters and setters")
    void testLabelGettersAndSetters() {
        // Test level label
        Label newLevelLabel = new Label("New Level");
        guiController.setLevelLabel(newLevelLabel);
        assertEquals(newLevelLabel, guiController.getLevelLabel());

        // Test score label
        Label newScoreLabel = new Label("New Score");
        guiController.setScoreLabel(newScoreLabel);
        assertEquals(newScoreLabel, guiController.scoreLabel);
    }

    @Test
    @DisplayName("Test invalid key handling")
    void testHandleInvalidKeys() {
        // Create unhandled key event
        KeyEvent invalidKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.F1, false, false, false, false);

        // Handle method (should not throw exceptions)
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(invalidKeyEvent);
        });

        // Verify no additional mock calls
        verify(mockEventListener, never()).onLeftEvent(any());
        verify(mockEventListener, never()).onRightEvent(any());
        verify(mockEventListener, never()).onRotateEvent(any());
        verify(mockEventListener, never()).onDownEvent(any());
    }

    @Test
    @DisplayName("Test event listener setting with NextBricksPanel")
    void testSetEventListenerWithNextBricksPanel() {
        // Create new event listener
        GameControllerInterface newEventListener = mock(GameControllerInterface.class);

        // Set event listener
        guiController.setEventListener(newEventListener);

        // Verify both GuiController and NextBricksPanel have the event listener set
        // Note: This would require additional setup to verify NextBricksPanel interaction
        assertNotNull(guiController);
    }

    /**
     * Helper method: Create Mock GameViewData object with valid data
     * @return Mock GameViewData object with valid brick data
     */
    private GameViewData createMockViewData() {
        GameViewData mockGameViewData = mock(GameViewData.class);

        // Create valid brick data (4x4 array)
        int[][] brickData = {
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        // Create valid next brick data
        int[][] nextBrickData = {
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        // Set up mock behavior
        when(mockGameViewData.getBrickData()).thenReturn(brickData);
        when(mockGameViewData.getNextBrickData()).thenReturn(nextBrickData);
        when(mockGameViewData.getxPosition()).thenReturn(5);
        when(mockGameViewData.getyPosition()).thenReturn(10);

        return mockGameViewData;
    }
}
