package com.comp2042.controller;

import com.comp2042.model.*;
import com.comp2042.util.ColorUtility;
import com.comp2042.view.GameOverPanel;
import com.comp2042.view.NextBricksPanel;
import com.comp2042.view.NotificationPanel;
import com.comp2042.util.GameConfig;
import com.comp2042.util.GameTimerUtility;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI Controller - Manages the visual interface and user interactions
 * Next brick related functionality has been removed and is now handled independently by NextBricksPanel
 */
public class GuiController implements Initializable {

    @FXML public Label levelLabelText;
    @FXML public Label scoreLabelText;
    @FXML GridPane gamePanel;
    @FXML Group groupNotification;
    @FXML private GridPane brickPanel;
    @FXML GameOverPanel gameOverPanel;

    // Next brick panels
    @FXML private GridPane nextBrickPanel1;
    @FXML private GridPane nextBrickPanel2;
    @FXML private GridPane nextBrickPanel3;

    @FXML Button pauseButton;
    @FXML Button newGameButton;
    @FXML private Label levelLabel;
    @FXML Label scoreLabel;

    private Rectangle[][] displayMatrix;
    private GameControllerInterface eventListener;
    Rectangle[][] rectangles;
    private NextBricksPanel nextBricksPanel; // New: NextBricksPanel instance

    GameTimerUtility gameTimerUtility;
    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    /**
     * Initialize the controller and set up the game panel
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDigitalFont();
        setupGamePanelFocus();
        setupKeyboardControls();
        initializeGameOverPanel();

        // Initialize NextBricksPanel
        GridPane[] previewPanels = {nextBrickPanel1, nextBrickPanel2, nextBrickPanel3};
        nextBricksPanel = new NextBricksPanel(previewPanels);
    }

    /**
     * Load digital font
     */
    private void loadDigitalFont() {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
    }

    /**
     * Set up game panel focus properties
     */
    private void setupGamePanelFocus() {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
    }

    /**
     * Configure keyboard event handling for game controls
     */
    private void setupKeyboardControls() {
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    handleGameplayKeys(keyEvent);
                }

                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                    keyEvent.consume();
                } else if (keyEvent.getCode() == KeyCode.SPACE) {
                    pauseGame(null);
                    keyEvent.consume();
                }
            }
        });
    }

    /**
     * Handle keyboard input related to gameplay
     */
    void handleGameplayKeys(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
            refreshBrick(eventListener.onLeftEvent(new GameMoveEvent(GameEventType.LEFT, EventSource.USER)));
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
            refreshBrick(eventListener.onRightEvent(new GameMoveEvent(GameEventType.RIGHT, EventSource.USER)));
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
            refreshBrick(eventListener.onRotateEvent(new GameMoveEvent(GameEventType.ROTATE, EventSource.USER)));
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
            moveDown(new GameMoveEvent(GameEventType.DOWN, EventSource.USER));
            keyEvent.consume();
        }
    }

    /**
     * Initialize game over panel visibility
     */
    private void initializeGameOverPanel() {
        gameOverPanel.setVisible(false);
    }

    /**
     * Bind level property to level label
     * @param levelProperty IntegerProperty representing the level
     */
    public void bindLevel(IntegerProperty levelProperty) {
        if (levelLabel != null) {
            levelLabel.textProperty().bind(levelProperty.asString());
            levelProperty.addListener((obs, oldVal, newVal) -> {
                updateGameSpeed(newVal.intValue());
            });
        }
    }

    /**
     * Initialize brick panel with rectangles
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
     * Initialize the game view with the given board matrix and brick data
     * @param boardMatrix GameBoardInterface matrix
     * @param brick BrickInterface data
     */
    public void initGameView(int[][] boardMatrix, GameViewData brick) {
        setupGameBoard(boardMatrix);
        setupBrickPanels(brick);
        setupGameLoop();
        eventListener.bindLevel(eventListener.getBoard().getScore().levelProperty());

        // Set up event listener for NextBricksPanel
        nextBricksPanel.setEventListener(eventListener);
        // Initialize next brick preview
        nextBricksPanel.initThreeNextBrickPreviews(brick);
    }

    /**
     * Set up the game board with the given board matrix
     * @param boardMatrix GameBoardInterface matrix
     */
    private void setupGameBoard(int[][] boardMatrix) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = GameConfig.HIDDEN_ROWS; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(GameConfig.BRICK_SIZE, GameConfig.BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - GameConfig.HIDDEN_ROWS);
            }
        }
    }

    /**
     * Set up brick panels with the given brick data
     */
    private void setupBrickPanels(GameViewData brick) {
        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        initBrickPanel(rectangles, brickPanel, brick.getBrickData());
        updateBrickPanelPosition(brick);
    }

    /**
     * Set up the game loop
     */
    private void setupGameLoop() {
        gameTimerUtility = new GameTimerUtility(GameConfig.INITIAL_SPEED, () -> moveDown(new GameMoveEvent(GameEventType.DOWN, EventSource.THREAD)));
        gameTimerUtility.start();
    }

    /**
     * Update game speed based on new level
     * @param newLevel New level
     */
    private void updateGameSpeed(int newLevel) {
        long speed = Math.max(GameConfig.MIN_SPEED, GameConfig.INITIAL_SPEED - (newLevel - 1) * GameConfig.SPEED_DECREMENT_PER_LEVEL);
        gameTimerUtility.setInterval(speed);
    }

    /**
     * Refresh brick display position and data
     * @param brick GameViewData object containing brick position, data, and next brick data
     */
    private void refreshBrick(GameViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            updateBrickPanelPosition(brick);

            // Update current brick display
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }

            // Update next brick preview
            nextBricksPanel.updateThreeNextBrickPreviews(brick);
        }
    }

    /**
     * Update brick panel position based on current brick position
     * @param brick GameViewData object containing brick position, data, and next brick data
     */
    private void updateBrickPanelPosition(GameViewData brick) {
        if (brickPanel == null || gamePanel == null) return;
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * GameConfig.BRICK_SIZE);
        brickPanel.setLayoutY(GameConfig.BRICK_PANEL_Y_OFFSET + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * GameConfig.BRICK_SIZE);
    }

    /**
     * Refresh game background display by updating each rectangle's data
     * @param board 2D array representing game board state data
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    /**
     * Set rectangle fill color and rounded corners
     * @param color Color identifier
     * @param rectangle Rectangle object
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(ColorUtility.getFillColor(color));
        rectangle.setArcHeight(GameConfig.ARC_RADIUS);
        rectangle.setArcWidth(GameConfig.ARC_RADIUS);
    }

    /**
     * Handle downward movement of current brick and process game logic
     * @param event GameMoveEvent containing movement source information (user or thread)
     */
    void moveDown(GameMoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            MoveResultData moveResultData = eventListener.onDownEvent(event);
            if (moveResultData.getClearRow() != null && moveResultData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + moveResultData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(moveResultData.getViewData());
        }
        gamePanel.requestFocus();
    }

    /**
     * Set the input event listener for handling user interactions
     * @param eventListener GameControllerInterface to set
     */
    public void setEventListener(GameControllerInterface eventListener) {
        this.eventListener = eventListener;
        // Also set event listener for NextBricksPanel
        if (nextBricksPanel != null) {
            nextBricksPanel.setEventListener(eventListener);
        }
    }

    /**
     * Bind game score property to UI display components
     * @param integerProperty Score property to bind
     */
    public void bindScore(IntegerProperty integerProperty) {
        if (scoreLabel != null) {
            scoreLabel.textProperty().bind(integerProperty.asString());
        }
    }

    /**
     * Display game over message and stop the game
     */
    public void gameOver() {
        gameTimerUtility.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Start a new game by resetting game state and UI components
     * @param actionEvent ActionEvent that triggered this method (can be null)
     */
    public void newGame(ActionEvent actionEvent) {
        gameTimerUtility.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        gameTimerUtility.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    /**
     * Pause the game
     * @param actionEvent ActionEvent that triggered this method (can be null)
     */
    public void pauseGame(ActionEvent actionEvent) {
        if (isPause.getValue()) {
            // Pause
            gameTimerUtility.play();
            pauseButton.setText("PAUSE");
            isPause.setValue(Boolean.FALSE);
        } else {
            // Resume
            gameTimerUtility.pause();
            pauseButton.setText("RESUME");
            isPause.setValue(Boolean.TRUE);
        }
        gamePanel.requestFocus();
    }

    /**
     * Get the level label component
     * @return Level label component
     */
    public Label getLevelLabel() {
        return levelLabel;
    }

    /**
     * Set the level label component
     * @param levelLabel Level label component to set
     */
    public void setLevelLabel(Label levelLabel) {
        this.levelLabel = levelLabel;
    }

    /**
     * Set the score label component
     * @param scoreLabel Score label component to set
     */
    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    /**
     * Get the NextBricksPanel instance (for testing or other purposes)
     * @return NextBricksPanel instance
     */
    public NextBricksPanel getNextBricksPanel() {
        return nextBricksPanel;
    }
}
