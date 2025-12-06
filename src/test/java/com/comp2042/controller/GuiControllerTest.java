package com.comp2042.controller;

import com.comp2042.core.Board;
import com.comp2042.core.ClearRow;
import com.comp2042.core.GameScore;
import com.comp2042.model.*;
import com.comp2042.util.GameTimer;
import com.comp2042.view.GameOverPanel;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import javafx.embed.swing.JFXPanel;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for GuiController class
 */
public class GuiControllerTest {

    private GuiController guiController;

    @Mock
    private GameInputHandler mockEventListener;

    @Mock
    private Board mockBoard;

    @Mock
    private GameScore mockScore;

    @BeforeAll
    public static void initJavaFX() {
        // 强制初始化JavaFX Toolkit，仅需执行一次
        new JFXPanel();
        // 等待Toolkit完全启动（可选）
        Platform.runLater(() -> {});
    }

    @BeforeEach
    void setUp() throws IOException {
        new JFXPanel();
        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        guiController = fxmlLoader.getController();
        guiController.setLevelLabel(new Label());

        MockitoAnnotations.openMocks(this);

        // Create an instance of GuiController
        guiController = new GuiController();

        // Mock dependencies
        when(mockEventListener.getBoard()).thenReturn(mockBoard);
        when(mockBoard.getScore()).thenReturn(mockScore);

        // Set up the event listener
        guiController.setEventListener(mockEventListener);
    }

    @Test
    void testInitialize() throws Exception {
        // Setup FXML loader to load the actual FXML file
        FXMLLoader loader = new FXMLLoader();
        URL fxmlUrl = getClass().getClassLoader().getResource("tetris.fxml");
        if (fxmlUrl != null) {
            loader.setLocation(fxmlUrl);
            loader.setController(guiController);

            // Verify initialization doesn't throw exceptions
            assertDoesNotThrow(() -> {
                loader.load();
            });
        }
    }

    @Test
    void testBindLevel() {
        // Create a mock label
        javafx.scene.control.Label mockLabel = Mockito.mock(javafx.scene.control.Label.class);
        // Create a mock label
        //Label mockLabel = mock(Label.class);
        guiController.setLevelLabel(mockLabel);

        // Create a test property
        IntegerProperty levelProperty = new SimpleIntegerProperty(5);

        // Bind level
        guiController.bindLevel(levelProperty);

        // Verify binding occurred
        verify(mockLabel).textProperty();
    }

    @Test
    void testBindScore() {
        // Create a mock label
        Label mockLabel = mock(Label.class);
        guiController.setScoreLabel(mockLabel);

        // Create a test property
        IntegerProperty scoreProperty = new SimpleIntegerProperty(100);

        // Bind score
        guiController.bindScore(scoreProperty);

        // Verify binding occurred
        verify(mockLabel).textProperty();
    }

    @Test
    void testHandleGameplayKeys_Left() {
        // Create KeyEvent for LEFT arrow
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);

        // Mock the event listener response
        ViewData mockViewData = mock(ViewData.class);
        when(mockEventListener.onLeftEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        // Call the method under test
        guiController.handleGameplayKeys(keyEvent);

        // Verify that onLeftEvent was called
        verify(mockEventListener).onLeftEvent(any(MoveEvent.class));
    }

    @Test
    void testHandleGameplayKeys_Right() {
        // Create KeyEvent for RIGHT arrow
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);

        // Mock the event listener response
        ViewData mockViewData = mock(ViewData.class);
        when(mockEventListener.onRightEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        // Call the method under test
        guiController.handleGameplayKeys(keyEvent);

        // Verify that onRightEvent was called
        verify(mockEventListener).onRightEvent(any(MoveEvent.class));
    }

    @Test
    void testHandleGameplayKeys_Up() {
        // Create KeyEvent for UP arrow
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);

        // Mock the event listener response
        ViewData mockViewData = mock(ViewData.class);
        when(mockEventListener.onRotateEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        // Call the method under test
        guiController.handleGameplayKeys(keyEvent);

        // Verify that onRotateEvent was called
        verify(mockEventListener).onRotateEvent(any(MoveEvent.class));
    }

    @Test
    void testHandleGameplayKeys_Down() {
        // Create KeyEvent for DOWN arrow
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);

        // Call the method under test
        guiController.handleGameplayKeys(keyEvent);

        // Verify that onDownEvent was called
        verify(mockEventListener).onDownEvent(any(MoveEvent.class));
    }

    @Test
    void testMoveDown() {
        // Create a MoveEvent
        MoveEvent moveEvent = new MoveEvent(EventType.DOWN, EventSource.THREAD);

        // Mock responses
        ViewData mockViewData = mock(ViewData.class);
        DownData mockDownData = mock(DownData.class);
        ClearRow mockClearRow = mock(ClearRow.class);

        when(mockEventListener.onDownEvent(moveEvent)).thenReturn(mockDownData);
        when(mockDownData.getClearRow()).thenReturn(mockClearRow);
        when(mockClearRow.getLinesRemoved()).thenReturn(1);
        when(mockClearRow.getScoreBonus()).thenReturn(100);
        when(mockDownData.getViewData()).thenReturn(mockViewData);

        // Create necessary UI components
        guiController.groupNotification = new Group();

        // Call the method under test
        guiController.moveDown(moveEvent);

        // Verify methods were called
        verify(mockEventListener).onDownEvent(moveEvent);
    }

    @Test
    void testGameOver() {
        // Create a mock GameTimer
        GameTimer mockTimer = mock(GameTimer.class);
        guiController.gameTimer = mockTimer;

        // Create a mock GameOverPanel
        GameOverPanel mockGameOverPanel = mock(GameOverPanel.class);
        guiController.gameOverPanel = mockGameOverPanel;

        // Call the method under test
        guiController.gameOver();

        // Verify timer was stopped
        verify(mockTimer).stop();

        // Verify game over panel visibility was set
        verify(mockGameOverPanel).setVisible(true);
    }

    @Test
    void testNewGame() {
        // Create a mock GameTimer
        GameTimer mockTimer = mock(GameTimer.class);
        guiController.gameTimer = mockTimer;

        // Create a mock GameOverPanel
        GameOverPanel mockGameOverPanel = mock(GameOverPanel.class);
        guiController.gameOverPanel = mockGameOverPanel;

        // Mock event listener
        guiController.setEventListener(mockEventListener);

        // Call the method under test
        guiController.newGame(null);

        // Verify timer methods were called
        verify(mockTimer).stop();
        verify(mockTimer).play();

        // Verify game over panel visibility was set
        verify(mockGameOverPanel).setVisible(false);

        // Verify createNewGame was called
        verify(mockEventListener).createNewGame();
    }

    @Test
    void testPauseGame() {
        // Create a mock GameTimer
        GameTimer mockTimer = mock(GameTimer.class);
        guiController.gameTimer = mockTimer;

        // Create a mock pause button
        Button mockPauseButton = mock(Button.class);
        guiController.pauseButton = mockPauseButton;

        // Initially not paused
        guiController.pauseGame(null);

        // Verify play was called and a button text was set
        verify(mockTimer).pause();

        // Pause again to resume
        guiController.pauseGame(null);

        // Verify pause was called and a button text was set
        verify(mockTimer).play();
    }

    @Test
    void testGetNextBrickNData() {
        // Test when eventListener is null
        guiController.setEventListener(null);
        int[][] result = guiController.getNextBrickNData(2);

        // Should return a default 4x4 array
        assertNotNull(result);
        assertEquals(4, result.length);
        assertEquals(4, result[0].length);

        // Test when eventListener is not null
        guiController.setEventListener(mockEventListener);
        int[][] testData = {{1, 2}, {3, 4}};
        when(mockEventListener.getNextBrickData(2)).thenReturn(testData);

        result = guiController.getNextBrickNData(2);

        // Should return the mocked data
        assertArrayEquals(testData, result);
    }
}
