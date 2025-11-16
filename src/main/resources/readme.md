# **1.Github link**  
* https://github.com/kooitt/CW2025.git

# 2.**Compilation Instructions:**
Provide a clear, step-by-step guide on how to compile the
code to produce the application. Include any dependencies or special settings
required.
编译说明：提供清晰的分步指南，说明如何编译代码生成应用程序，包括所需的依赖项或特殊设置。

# 3.Implemented and Working Properly: 
List the features that have been successfully
implemented and are functioning as expected. Provide a brief description of each.
已实现且运行正常的功能：列出成功实现并按预期运行的功能，简要描述每项功能。
## (1)Add preview feature增加预览功能 
* brief description：Add a preview of the next block in the empty area at the top-left corner of the game screen.
* Modified Java Classes：    
GuiController.java: Add new member variables:nextBrickPanel,nextRectangles;
Add a method initNextBrickPreview to initialize the next-block preview;
Add a method updateNextBrickPreview to update preview；
calls the updateNextBrickPreview method with the next brick's data (brick.getNextBrickData()),to update the next-block preview.
    
## (2)Add score calculation and display增加分数计算和显示功能
* brief description：The original program could only show the added score but had no scoring function, so I added a feature to calculate the total score.
* Modified Java Classes： 
GuiController.java:import javafx.scene.control.Label to use;
Add private Label scoreLabel;
In the bindScore method, add a bind between the scoreLabel and integerProperty to enable real-time score updates.
## (3)Add pause/resume functionality增加暂停pause/恢复resume功能
## (4)Add restart functionality增加重新开始功能
## (5)Add level mode增加关卡模式
* When the accumulated score reaches a certain value, proceed to the next level (increase speed)累计分数到某个分值时，进入下一关（加快速度）
* **Level up升级**

# Implemented but Not Working Properly: 
List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.
已实现但运行异常的功能：列出已实现但无法正常运行的功能，说明遇到的问题及（若有）尝试解决的步骤。

# Features Not Implemented: 
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.
未实现的功能：明确未完成的功能，并清晰解释未实现的原因。

# New Java Classes: 
Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.
新增 Java 类：列举为完成作业新增的所有 Java 类，简要描述每个类的用途及其在代码中的位置。

# Modified Java Classes: 
Modified Java Classes:
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.
修改的 Java 类：列出对提供的代码库中已有的 Java 类所做的修改，描述修改内容并说明修改的必要性。（包括BUG修复（代码重构

# Unexpected Problems: 
Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.
意外问题：说明作业过程中遇到的任何意外挑战或问题，描述你如何解决或尝试解决这些问题。




# 3. Refactoring重构
## (1) Class name refactoring (1)类名重构
- Score.java--->GameScore.java
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
│   ├── BrickGenerator.java  
│   ├── BrickRotator.java  
│   └── ClearRow.java   
├── controller   
│   ├── GameController.java  
│   ├── GuiController.java  
│   └── InputEventListener.java  
├── core   
│   ├── Board.java  
│   ├── GameScore.java   
│   ├── MatrixOperations.java    
│   └── SimpleBoard.java   
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

## (3)代码重构





功能实现：
最高分记录：
将高分存储在文件或配置中
在UI中显示历史最佳分数
更多计分事件：
连续快速放置方块时给予额外奖励
使用高级旋转技巧时的加分







