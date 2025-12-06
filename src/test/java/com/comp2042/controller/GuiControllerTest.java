package com.comp2042.controller;

import com.comp2042.core.Board;
import com.comp2042.core.ClearRow;
import com.comp2042.core.GameScore;
import com.comp2042.model.*;
import com.comp2042.util.GameTimer;
import com.comp2042.view.GameOverPanel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
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
import javafx.scene.layout.Pane;
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
 * 修复后的GuiController单元测试类
 * 解决了ViewData中brickData为null导致的NullPointerException问题
 */
public class GuiControllerTest {

    private GuiController guiController;

    @Mock
    private GameInputHandler mockEventListener;

    @Mock
    private Board mockBoard;

    @Mock
    private GameScore mockScore;

    @Mock
    private GameTimer mockTimer;

    @Mock
    private GameOverPanel mockGameOverPanel;

    @BeforeAll
    public static void initJavaFX() {
        // 初始化JavaFX Toolkit，仅需执行一次
        new JFXPanel();
        Platform.runLater(() -> {});
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // 创建GuiController实例
        guiController = new GuiController();

        // 设置必要的UI组件
        guiController.gamePanel = new GridPane();
        guiController.pauseButton = new Button("PAUSE");
        guiController.newGameButton = new Button("NEW GAME");
        guiController.setLevelLabel(new Label("Level: 1"));
        guiController.setScoreLabel(new Label("Score: 0"));
        guiController.gameOverPanel = mockGameOverPanel;
        guiController.groupNotification = new Group();

        // 初始化矩形数组
        guiController.nextRectangles = new Rectangle[4][4];
        guiController.rectangles = new Rectangle[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                guiController.rectangles[i][j] = new Rectangle();
            }
        }

        // 使用反射设置isPause属性为false
        Field isPauseField = GuiController.class.getDeclaredField("isPause");
        isPauseField.setAccessible(true);
        isPauseField.set(guiController, new SimpleBooleanProperty(false));

        // 设置模拟依赖
        guiController.gameTimer = mockTimer;
        guiController.setEventListener(mockEventListener);

        // 配置模拟对象行为
        when(mockEventListener.getBoard()).thenReturn(mockBoard);
        when(mockBoard.getScore()).thenReturn(mockScore);
    }

    @Test
    @DisplayName("测试初始化方法")
    void testInitialize() {
        // 验证初始化不会抛出异常
        assertDoesNotThrow(() -> {
            assertNotNull(guiController.gamePanel);
            assertNotNull(guiController.pauseButton);
            assertNotNull(guiController.newGameButton);
        });
    }

    @Test
    @DisplayName("测试等级绑定功能")
    void testBindLevel() {
        // 创建测试属性
        IntegerProperty levelProperty = new SimpleIntegerProperty(5);

        // 绑定等级
        guiController.bindLevel(levelProperty);

        // 验证绑定生效
        assertEquals("5", guiController.getLevelLabel().getText());

        // 测试属性变化
        levelProperty.set(10);
        assertEquals("10", guiController.getLevelLabel().getText());
    }

    @Test
    @DisplayName("测试分数绑定功能")
    void testBindScore() {
        // 创建测试属性
        IntegerProperty scoreProperty = new SimpleIntegerProperty(1000);

        // 绑定分数
        guiController.bindScore(scoreProperty);

        // 验证绑定生效
        assertEquals("1000", guiController.scoreLabel.getText());

        // 测试属性变化
        scoreProperty.set(2000);
        assertEquals("2000", guiController.scoreLabel.getText());
    }

    @Test
    @DisplayName("测试左箭头键处理")
    void testHandleGameplayKeys_Left() {
        // 创建左箭头键事件
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);

        // 模拟事件监听器响应
        ViewData mockViewData = createMockViewData();
        when(mockEventListener.onLeftEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        // 处理方法 - 确保不会抛出异常
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // 验证onLeftEvent被调用
        verify(mockEventListener).onLeftEvent(any(MoveEvent.class));
    }

    @Test
    @DisplayName("测试右箭头键处理")
    void testHandleGameplayKeys_Right() {
        // 创建右箭头键事件
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);

        // 模拟事件监听器响应
        ViewData mockViewData = createMockViewData();
        when(mockEventListener.onRightEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        // 处理方法 - 确保不会抛出异常
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // 验证onRightEvent被调用
        verify(mockEventListener).onRightEvent(any(MoveEvent.class));
    }

    @Test
    @DisplayName("测试上箭头键处理（旋转）")
    void testHandleGameplayKeys_Up() {
        // 创建上箭头键事件
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);

        // 模拟事件监听器响应
        ViewData mockViewData = createMockViewData();
        when(mockEventListener.onRotateEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        // 处理方法 - 确保不会抛出异常
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // 验证onRotateEvent被调用
        verify(mockEventListener).onRotateEvent(any(MoveEvent.class));
    }

    @Test
    @DisplayName("测试下箭头键处理")
    void testHandleGameplayKeys_Down() {
        // 创建下箭头键事件
        KeyEvent keyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);

        // 处理方法 - 确保不会抛出异常
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(keyEvent);
        });

        // 验证onDownEvent被调用
        verify(mockEventListener).onDownEvent(any(MoveEvent.class));
    }

    @Test
    @DisplayName("测试WASD键处理")
    void testHandleGameplayKeys_WASD() {
        // 测试W键（旋转）
        KeyEvent wKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.W, false, false, false, false);
        ViewData mockViewData = createMockViewData();
        when(mockEventListener.onRotateEvent(any(MoveEvent.class))).thenReturn(mockViewData);

        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(wKeyEvent);
        });
        verify(mockEventListener).onRotateEvent(any(MoveEvent.class));

        // 测试A键（左移）
        KeyEvent aKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(aKeyEvent);
        });
        verify(mockEventListener, times(2)).onLeftEvent(any(MoveEvent.class));

        // 测试S键（下移）
        KeyEvent sKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.S, false, false, false, false);
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(sKeyEvent);
        });
        verify(mockEventListener).onDownEvent(any(MoveEvent.class));

        // 测试D键（右移）
        KeyEvent dKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.D, false, false, false, false);
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(dKeyEvent);
        });
        verify(mockEventListener, times(2)).onRightEvent(any(MoveEvent.class));
    }

    @Test
    @DisplayName("测试新游戏键处理")
    void testHandleGameplayKeys_NewGame() {
        // 创建N键事件
        KeyEvent nKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.N, false, false, false, false);

        // 处理方法
        guiController.handleGameplayKeys(nKeyEvent);

        // 验证newGame被调用（通过事件监听器）
        verify(mockEventListener).createNewGame();
    }

    @Test
    @DisplayName("测试空格键处理（暂停）")
    void testHandleGameplayKeys_Space() {
        // 创建空格键事件
        KeyEvent spaceKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);

        // 处理方法
        guiController.handleGameplayKeys(spaceKeyEvent);

        // 验证timer的pause/play被调用
        verify(mockTimer).pause();

        // 再次按空格键恢复
        guiController.handleGameplayKeys(spaceKeyEvent);
        verify(mockTimer).play();
    }

    @Test
    @DisplayName("测试砖块下移功能")
    void testMoveDown() {
        // 创建下移事件
        MoveEvent moveEvent = new MoveEvent(EventType.DOWN, EventSource.THREAD);

        // 模拟响应数据
        ViewData mockViewData = createMockViewData();
        DownData mockDownData = mock(DownData.class);
        ClearRow mockClearRow = mock(ClearRow.class);

        when(mockEventListener.onDownEvent(moveEvent)).thenReturn(mockDownData);
        when(mockDownData.getClearRow()).thenReturn(mockClearRow);
        when(mockClearRow.getLinesRemoved()).thenReturn(2);
        when(mockClearRow.getScoreBonus()).thenReturn(200);
        when(mockDownData.getViewData()).thenReturn(mockViewData);

        // 处理方法
        guiController.moveDown(moveEvent);

        // 验证方法调用
        verify(mockEventListener).onDownEvent(moveEvent);
        verify(mockDownData).getClearRow();
        verify(mockClearRow).getLinesRemoved();
        verify(mockClearRow).getScoreBonus();
    }

    @Test
    @DisplayName("测试游戏结束功能")
    void testGameOver() {
        // 调用游戏结束方法
        guiController.gameOver();

        // 验证timer停止
        verify(mockTimer).stop();

        // 验证游戏结束面板显示
        verify(mockGameOverPanel).setVisible(true);
    }

    @Test
    @DisplayName("测试新游戏功能")
    void testNewGame() {
        // 调用新游戏方法
        guiController.newGame(null);

        // 验证timer操作
        verify(mockTimer).stop();
        verify(mockTimer).play();

        // 验证游戏结束面板隐藏
        verify(mockGameOverPanel).setVisible(false);

        // 验证创建新游戏
        verify(mockEventListener).createNewGame();
    }

    @Test
    @DisplayName("测试暂停游戏功能")
    void testPauseGame() {
        // 第一次调用暂停
        guiController.pauseGame(null);
        verify(mockTimer).pause();

        // 第二次调用恢复
        guiController.pauseGame(null);
        verify(mockTimer).play();
    }

    @Test
    @DisplayName("测试获取下一个砖块数据")
    void testGetNextBrickNData() {
        // 测试eventListener为null的情况
        guiController.setEventListener(null);
        int[][] result = guiController.getNextBrickNData(1);

        assertNotNull(result);
        assertEquals(4, result.length);
        assertEquals(4, result[0].length);

        // 测试eventListener不为null的情况
        guiController.setEventListener(mockEventListener);
        int[][] testData = {{1, 0}, {1, 1}, {0, 1}, {0, 0}};
        when(mockEventListener.getNextBrickData(2)).thenReturn(testData);

        result = guiController.getNextBrickNData(2);
        assertArrayEquals(testData, result);
    }

    @Test
    @DisplayName("测试设置和获取标签")
    void testLabelGettersAndSetters() {
        // 测试等级标签
        Label newLevelLabel = new Label("New Level");
        guiController.setLevelLabel(newLevelLabel);
        assertEquals(newLevelLabel, guiController.getLevelLabel());

        // 测试分数标签
        Label newScoreLabel = new Label("New Score");
        guiController.setScoreLabel(newScoreLabel);
        assertEquals(newScoreLabel, guiController.scoreLabel);
    }

    @Test
    @DisplayName("测试无效按键处理")
    void testHandleInvalidKeys() {
        // 创建未处理的按键事件
        KeyEvent invalidKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.F1, false, false, false, false);

        // 处理方法（应该不会产生异常）
        assertDoesNotThrow(() -> {
            guiController.handleGameplayKeys(invalidKeyEvent);
        });

        // 验证没有额外的mock调用
        verify(mockEventListener, never()).onLeftEvent(any());
        verify(mockEventListener, never()).onRightEvent(any());
        verify(mockEventListener, never()).onRotateEvent(any());
        verify(mockEventListener, never()).onDownEvent(any());
    }

    /**
     * 辅助方法：创建带有有效数据的Mock ViewData对象
     * 解决getBrickData()和getNextBrickData()返回null的问题
     */
    private ViewData createMockViewData() {
        ViewData mockViewData = mock(ViewData.class);

        // 创建有效的砖块数据（4x4数组）
        int[][] brickData = {
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        // 创建有效的下一个砖块数据
        int[][] nextBrickData = {
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        // 设置模拟行为
        when(mockViewData.getBrickData()).thenReturn(brickData);
        when(mockViewData.getNextBrickData()).thenReturn(nextBrickData);
        when(mockViewData.getxPosition()).thenReturn(5);
        when(mockViewData.getyPosition()).thenReturn(10);

        return mockViewData;
    }
}