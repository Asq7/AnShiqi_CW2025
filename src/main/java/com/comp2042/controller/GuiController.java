package com.comp2042.controller;

import com.comp2042.core.Board;
import com.comp2042.model.*;
import com.comp2042.view.GameOverPanel;
import com.comp2042.view.NotificationPanel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * GUI Controller class responsible for managing the visual interface and user interaction of the Tetris game
 * This class implements the game interface rendering and control through JavaFX
 * @author AnShiqi
 * @version 1.0
 * @since 2025
 */
public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    private static final int BRICK_PANEL_Y_OFFSET = -42;
    private static final int INITIAL_SPEED = 700;
    private static final int MIN_SPEED = 200;
    private static final int SPEED_DECREMENT_PER_LEVEL = 150;
    private static final int ARC_RADIUS = 9;
    @FXML
    public Label levelLabelText;
    public Label scoreLabelText;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;


    @FXML
    private GridPane nextBrickPanel1;
    @FXML
    private GridPane nextBrickPanel2;
    @FXML
    private GridPane nextBrickPanel3;

    // add a button to pause
    @FXML
    private Button pauseButton;
    //add a button to new game
    @FXML
    private Button newGameButton;

    @FXML
    private Label levelLabel;

    private Rectangle[][] nextRectangles;
    private Rectangle[][] nextRectangles1;
    private Rectangle[][] nextRectangles2;
    private Rectangle[][] nextRectangles3;

    private Rectangle[][] displayMatrix;

    private GameInputHandler eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @FXML
    private Label scoreLabel;


    /**
     * Initializes the controller and sets up the game panel
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDigitalFont();
        setupGamePanelFocus();
        setupKeyboardControls();
        initializeGameOverPanel();
    }

    /**
     * Loads the digital font for UI elements
     */
    private void loadDigitalFont() {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
    }

    /**
     * Sets up the game panel focus properties
     */
    private void setupGamePanelFocus() {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
    }

    /**
     * Configures keyboard event handling for game controls
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
     * Handles gameplay-related keyboard inputs
     */
    private void handleGameplayKeys(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
            refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
            refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
            refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
            keyEvent.consume();
        }
        if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
            moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
            keyEvent.consume();
        }
    }


    /**
     * Initializes the game over panel visibility
     */
    private void initializeGameOverPanel() {
        gameOverPanel.setVisible(false);
    }

    /**
     * Binds the level property to the level label
     * @param levelProperty the IntegerProperty representing the level
     */
    public void bindLevel(IntegerProperty levelProperty) {
        if (levelLabel != null) {
            levelLabel.textProperty().bind(levelProperty.asString());
        }
    }

    private void initBrickPanel(Rectangle[][] rectangles,GridPane brickPanel ,int[][] brickData) {
        for (int i = 0; i < brickData.length; i++) {
            for (int j = 0; j < brickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brickData[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
    }

    /**
     * Initializes the game view by creating and setting up the game board and brick panels
     * @param boardMatrix the 2D array representing the game board state
     * @param brick the ViewData object containing current brick information
     */
    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        initBrickPanel(rectangles, brickPanel, brick.getBrickData());

        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(BRICK_PANEL_Y_OFFSET + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);

        initThreeNextBrickPreviews(brick);


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(INITIAL_SPEED), // LEVEL1 SPEED
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        Board board=eventListener.getBoard();
        board.getScore().levelProperty().addListener((observable, oldValue, newValue) -> {
            if (timeLine != null) {
                timeLine.stop();
                timeLine.getKeyFrames().clear();

                // SPEED UP WITH LEVEL UP(MIN200ms)
                    long speed = Math.max(MIN_SPEED, INITIAL_SPEED - (newValue.intValue()- 1) * SPEED_DECREMENT_PER_LEVEL);
                timeLine.getKeyFrames().add(new KeyFrame(
                        Duration.millis(speed),
                        ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
                ));
                timeLine.play();
            }
        });
        timeLine.play();


        eventListener.bindLevel(board.getScore().levelProperty());
    }

    /**
     * Initializes the three next brick previews
     */
    private void initThreeNextBrickPreviews(ViewData brick) {
        // Clear preview panels
        nextBrickPanel1.getChildren().clear();
        nextBrickPanel2.getChildren().clear();
        nextBrickPanel3.getChildren().clear();

        // Initialize first preview (current next brick)
        int[][] nextBrickData1 = brick.getNextBrickData();
        if (nextBrickData1.length > 0 && nextBrickData1[0].length > 0) {
            nextRectangles1 = new Rectangle[nextBrickData1.length][nextBrickData1[0].length];
            initBrickPanel(nextRectangles1, nextBrickPanel1, nextBrickData1);
        }

        // Initialize second preview (the brick after next)
        int[][] nextBrickData2 = getNextBrickNData(2);
        if (nextBrickData2.length > 0 && nextBrickData2[0].length > 0) {
            nextRectangles2 = new Rectangle[nextBrickData2.length][nextBrickData2[0].length];
            initBrickPanel(nextRectangles2, nextBrickPanel2, nextBrickData2);
        }

        // Initialize third preview (third upcoming brick)
        int[][] nextBrickData3 = getNextBrickNData(3);
        if (nextBrickData3.length > 0 && nextBrickData3[0].length > 0) {
            nextRectangles3 = new Rectangle[nextBrickData3.length][nextBrickData3[0].length];
            initBrickPanel(nextRectangles3, nextBrickPanel3, nextBrickData3);
        }
    }

    /**
     * Get the data of the nth next brick
     */
    private int[][] getNextBrickNData(int n) {
        // Get real brick data from game controller
        Board board = eventListener.getBoard();
        if (board!=null) {
            //GameBoard gameBoard = (GameBoard) board;
            return board.getNextBrickData(n);
        }
        // If unable to get, return default data
        return new int[4][4];
    }

//    /**
//     * Initializes the next-block preview area
//     * @param nextBrickData
//     */
//    private void initNextBrickPreview(int[][] nextBrickData) {
//        for (int i = 0; i < nextBrickData.length; i++) {
//            for (int j = 0; j < nextBrickData[i].length; j++) {
//                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
//                rectangle.setFill(getFillColor(nextBrickData[i][j]));
//                nextRectangles[i][j] = rectangle;
//                nextBrickPanel.add(rectangle, j, i);
//            }
//        }
//    }//Duplicate Code

    /**
     * Add a method to update preview
     * @param nextBrickData
     */
// overload the updateNextBrickPreview method to adapt to different rectangular arrays
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
     * Maps brick type identifiers to their corresponding fill colors
     * @param i the brick type identifier (0-7)
     * @return the Paint color corresponding to the brick type
     */
    private Paint getFillColor(int i) {
        return switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.AQUA;
            case 2 -> Color.BLUEVIOLET;
            case 3 -> Color.DARKGREEN;
            case 4 -> Color.YELLOW;
            case 5 -> Color.RED;
            case 6 -> Color.BEIGE;
            case 7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
        //return returnPaint;
    }

/**
     * Updates the next-block preview area with the latest brick data
     * @param brick the ViewData object containing brick position, data and next brick data
     */
    private void updateThreeNextBrickPreviews(ViewData brick) {
        // update NextBrick Preview
        updateNextBrickPreview(brick.getNextBrickData(), nextRectangles1);
        updateNextBrickPreview(getNextBrickNData(2), nextRectangles2);
        updateNextBrickPreview(getNextBrickNData(3), nextRectangles3);
    }

    /**
     * Refreshes the brick display position and data
     * @param brick the ViewData object containing brick position, data and next brick data
     */
    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
            // Update preview of the next-block
            updateThreeNextBrickPreviews(brick);
        }
    }
    /**
     * Refreshes the game background display by updating each rectangle's data
     * @param board 2D array representing the game board state data
     */
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }
    /**
     * Sets the fill color and rounded corners for a rectangle based on the color identifier
     * @param color the color identifier (0-7) representing different brick types
     * @param rectangle the Rectangle object to be updated
     */
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(ARC_RADIUS);
        rectangle.setArcWidth(ARC_RADIUS);
    }

    /**
     * Handles the downward movement of the current brick and processes game logic
     * @param event the MoveEvent containing information about the source of the movement (user or thread)
     */
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }
    /**
     * Sets the input event listener for handling user interactions
     * @param eventListener the GameInputHandler to be set
     */
    public void setEventListener(GameInputHandler eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * Binds the game score property to the UI display component
     * @param integerProperty the score property to bind
     */
    public void bindScore(IntegerProperty integerProperty) {
        if (scoreLabel != null) {
            scoreLabel.textProperty().bind(integerProperty.asString());
        }
    }

    /**
     * Displays a game over a message and stops the game
     */
    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
    }

    /**
     * Starts a new game by resetting the game state and UI components
     * @param actionEvent the ActionEvent that triggered this method (can be null)
     */
    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }
    /**
     * Pauses the game
     * @param actionEvent the ActionEvent that triggered this method (can be null)
     */
    public void pauseGame(ActionEvent actionEvent) {

        if (isPause.getValue()) {
            // pause
            timeLine.play();
            pauseButton.setText("PAUSE");
            isPause.setValue(Boolean.FALSE);
        } else {
            // RESUME
            timeLine.pause();
            pauseButton.setText("RESUME");
            isPause.setValue(Boolean.TRUE);
        }
        gamePanel.requestFocus();
    }
}
