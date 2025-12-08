# **1.Github link**  
* https://github.com/kooitt/CW2025.git

# 2.**Compilation Instructions:**
编译说明：提供清晰的分步指南，说明如何编译代码生成应用程序，包括所需的依赖项或特殊设置。
```
mvn clean javafx:run
```

# 3.Implemented and Working Properly: 
已实现且运行正常的功能：列出成功实现并按预期运行的功能，简要描述每项功能。
## <p style="text-indent:2em;">(1)Add preview feature增加预览功能 
* brief description：Add a preview of the next block in the empty area at the top-left corner of the game screen.
* Modified Java Classes：    
GuiController.java: Add new member variables:nextBrickPanel,nextRectangles;
Add a method initNextBrickPreview to initialize the next-block preview;
Add a method updateNextBrickPreview to update preview；
calls the updateNextBrickPreview method with the next brick's data (brick.getNextBrickData()),to update the next-block preview.
    
## <p style="text-indent:2em;">(2)Add score calculation and display增加分数计算和显示功能
* brief description：The original program could only show the added score but had no scoring function, so I added a feature to calculate the total score.
* Modified Java Classes： 
GuiController.java:import javafx.scene.control.Label to use;
Add private Label scoreLabel;
In the bindScore method, add a bind between the scoreLabel and integerProperty to enable real-time score updates.
## <p style="text-indent:2em;">(3)Add pause/resume functionality增加暂停pause/恢复resume功能
* brief description：Add a pause/resume functionality to the game. Press the space or click the button can pause/resume the game.
* Modified Java Classes：
GuiController.java:import javafx.scene.control.Button to use; Add a button to the game screen; Add a method to the button to pause/resume the game.
## <p style="text-indent:2em;">(4)Add restart functionality(button)增加重新开始功能
* brief description：Add a clickable button：click to start a new game.(Initially there was no button, players can only press "N" to restart)
* Modified Java Classes：
GuiController.java: add: else if (keyEvent.getCode() == KeyCode.SPACE) { pauseGame(null);}
## <p style="text-indent:2em;">(5)Add level mode增加关卡模式
* When the accumulated score reaches a certain value, proceed to the next level (increase speed)累计分数到某个分值时，进入下一关（加快速度）
* Modified Java Classes：
GameScore.java:Add level-related logic to the GameScore class: level up when reach a certain score
GuiController.java: Modify GuiController to display level information:
  add a level label reference in the GuiController class;
  modify the initialize method to bind the level property;
  update the initGameView method to invoke level binding;
  Adjust game speed based on level;
  Modify the timeline configuration in GuiController;
GameController.java: Add a level binding method in the GameController class

* **Level up升级**

# 4.Implemented but Not Working Properly: 
List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.
已实现但运行异常的功能：列出已实现但无法正常运行的功能，说明遇到的问题及（若有）尝试解决的步骤。

# 5.Features Not Implemented: 
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.
未实现的功能：明确未完成的功能，并清晰解释未实现的原因。

# 6.New Java Classes: 
Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.
新增 Java 类：列举为完成作业新增的所有 Java 类，简要描述每个类的用途及其在代码中的位置。




# 7.Modified Java Classes: 
Modified Java Classes:
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.
修改的 Java 类：列出对提供的代码库中已有的 Java 类所做的修改，描述修改内容并说明修改的必要性。（包括BUG修复（代码重构
rename class: 
- Score.java--->GameScore.java
- SimpleBoard.java --->GameBoard.java
- InputEventListener.java --->GameInputHandler.java

  Delete line 25 of RandomBrickGenerator.java (reason:repeat)

- Refactor code in GuiController:   

  **1.DELETE CODE FOR REFLECTION：(I don't want reflection)**
```java
  final Reflection reflection = new Reflection();
  reflection.setFraction(0.8);
  reflection.setTopOpacity(0.9);
  reflection.setTopOffset(-12);
  ```
  **2.modify Duplicate Code---logic of for loop**
- Code Smell（原文）：
- {Duplicate Code
  Code blocks that appear multiple times across a project.
  Symptoms:
  • Same logic copied in different classes or methods
  • Minor variations in repeated code
  Consequences:
  • Bug fixes or updates must be applied multiple times
  • Increases the chance of inconsistent behavior
  • Makes maintenance harder
  Refactoring Techniques:
  • Extract Method: Create a shared method to replace repeated logic
  • Pull Up Method: Move shared methods to a common superclass}

    ADD NEW METHOD initBrickPanel:
```java
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
```
    TO REPLACE:
```java
  private void initNextBrickPreview(int[][] nextBrickData) {
  nextRectangles = new Rectangle[nextBrickData.length][nextBrickData[0].length];
  for (int i = 0; i < nextBrickData.length; i++) {
  for (int j = 0; j < nextBrickData[i].length; j++) {
  Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
  rectangle.setFill(getFillColor(nextBrickData[i][j]));
  nextRectangles[i][j] = rectangle;
  nextBrickPanel.add(rectangle, j, i);
  }
  }
  }
  ```
    AND REPLACE:
```java
  for (int i = 0; i < brick.getBrickData().length; i++) {
  for (int j = 0; j < brick.getBrickData()[i].length; j++) {
  Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
  rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
  rectangles[i][j] = rectangle;
  brickPanel.add(rectangle, j, i);
  }
}
```
（ADD：initBrickPanel(rectangles, brickPanel, brick.getBrickData());）

  REPLACE:  
```java
initNextBrickPreview(brick.getNextBrickData()); 
private void initNextBrickPreview(int[][] nextBrickData)
```
  AS:  
```java     
  nextRectangles = new Rectangle[brick.getNextBrickData().length][brick.getNextBrickData()[0].length];  
  initBrickPanel(nextRectangles, nextBrickPanel, brick.getNextBrickData());  
```

  **3.modify Complex Conditionals---Long switch statements(getFillColor);**
  - Code Smell（原文）：
  {Complex Conditionals
    Long if–else or switch statements that are hard to read and maintain.
    Symptoms:
    • Multiple nested conditions
    • Repeated code within branches
    • Difficult to predict all possible outcomes
    Consequences:
    • Hard to understand and modify logic
    • Likely to introduce errors when updating conditions
    • Can violate the Open/Closed Principle
    Refactoring Techniques:
    • Replace Conditional with Polymorphism: Use subclasses or strategy patterns to handle
    variations
    • Extract Method: Move conditional branches into descriptive methods}
  
REPLACE:
```java
  Paint returnPaint;
     switch (i) {
     case 0:
     returnPaint = Color.TRANSPARENT;
     break;
     case 1:
     returnPaint = Color.AQUA;
     break;
     case 2:
     returnPaint = Color.BLUEVIOLET;
     break;
     case 3:
     returnPaint = Color.DARKGREEN;
     break;
     case 4:
     returnPaint = Color.YELLOW;
     break;
     case 5:
     returnPaint = Color.RED;
     break;
     case 6:
     returnPaint = Color.BEIGE;
     break;
     case 7:
     returnPaint = Color.BURLYWOOD;
     break;
     default:
     returnPaint = Color.WHITE;
     break;
     }
     return returnPaint;
```
  WITH:
```java
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
```

 **4.modify Long Method**    
- Code Smell（原文）：
{Long Method
A long method tries to do too much in a single block of code.
Symptoms:
• Multiple responsibilities handled in one method
• Many nested loops or conditional statements
• Hard to understand at a glance
Consequences:
• Difficult to maintain or modify
• Higher likelihood of introducing bugs when changing the code
• Harder to test individual pieces of logic
Refactoring Techniques:
• Extract Method: Break the method into smaller, focused methods.
• Replace Temp with Query: Use descriptive methods instead of temporary variables.}

REPLACE:
```java
public void initialize(URL location, ResourceBundle resources) {
Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
gamePanel.setFocusTraversable(true);
gamePanel.requestFocus();
gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
@Override
public void handle(KeyEvent keyEvent) {
if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
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
if (keyEvent.getCode() == KeyCode.N) {
newGame(null);//can add button?
}
else if (keyEvent.getCode() == KeyCode.SPACE) {
pauseGame(null);
}
}
});
gameOverPanel.setVisible(false);
```
```java
AS；
     @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDigitalFont();
        setupGamePanelFocus();
        setupKeyboardControls();
        initializeGameOverPanel();
    }
    private void loadDigitalFont() {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
    }
    private void setupGamePanelFocus() {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
    }
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
    private void initializeGameOverPanel() {
        gameOverPanel.setVisible(false);
    }
```
【The current initialize() method takes on excessive responsibilities. Split it into multiple smaller methods to improve readability and maintainability.
Current Issues:The initialize() method encompasses several unrelated responsibilities:
Loading font resources
Setting focus for the game panel
Configuring keyboard event handlers
Initializing the state of the game over panel
Refactoring:Split the initialize() method into multiple small methods with single responsibilities】

**5.Define constants used as named constants with descriptive identifiers.**
**6.FIX Tight Coupling:getBoard bindLevel(27hao lingchen)**

# 8.Unexpected Problems: 
Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.
意外问题：说明作业过程中遇到的任何意外挑战或问题，描述你如何解决或尝试解决这些问题。




# 3. Refactoring重构
## (1) Class name refactoring (1)类名重构
- Score.java--->GameScore.java
- SimpleBoard.java --->GameBoard.java
- InputEventListener.java --->GameInputHandler.java
## (2) Package path refactoring(2)包路径重构
- Package path structure changed to: 包路径结构改为：   
com.comp2042   
├── bricks   
│   ├── impl   
│   │   ├── shapes   
│   │   │   ├── IBrick.java   
│   │   │   ├── JBrick.java   
│   │   │   ├── LBrick.java   
│   │   │   ├── OBrick.java   
│   │   │   ├── SBrick.java   
│   │   │   ├── TBrick.java   
│   │   │   └── ZBrick.java   
│   │   └── RandomBrickGenerator.java  
│   ├── Brick.java
│   └── BrickGenerator.java
├── controller   
│   ├── GameController.java  
│   ├── GuiController.java  
│   └── GameInputHandler.java
├── core   
│   ├── Board.java  
│   ├── GameScore.java   
│   ├── MatrixOperations.java    
│   ├── GameBoard.java  
│   ├── BrickRotator.java   
│   └── ClearRow.java    
├── model  
│   ├── DownData.java    
│   ├── EventSource.java  
│   ├── EventType.java  
│   ├── MoveEvent.java  
│   ├── NextShapeInfo.java  
│   └── ViewData.java  
├── view  
│   ├── GameOverPanel.java  
│   └── NotificationPanel.java  
└── Main.java  
**reason：**  
To set the package path structure according to functional layers:按功能分层设置包路径结构：
- bricks layer: responsible for handling block-related logic in the game
- controller layer: responsible for controller-level logic
- core layer: contains the game’s core business logic
- model layer: contains the data model classes
- view layer: responsible for the user interface components

- bricks层：负责处理游戏中的方块相关逻辑
- controller层：负责控制层逻辑
- core层：包含游戏核心业务逻辑
- model层：包含数据模型类
- view层：负责用户界面组件





功能实现：
最高分记录：
将高分存储在文件或配置中
在UI中显示历史最佳分数
更多计分事件：
连续快速放置方块时给予额外奖励
使用高级旋转技巧时的加分







