# 捕虎棋🐯 | CatchTigerChess

> *NJUPT 2018/2019 学年第 2 学期《JAVA 程序设计》课程 大作业* 🎆

> *Author :* **何炎柏 | 杨焕煜 | 汪有为**

> 参考项目 : [FieldSoft-HelloClyde(ChineseChess)](https://github.com/FieldSoft-HelloClyde/ChineseChess)

> 参考资源 : [图标📈(flaticon)](https://www.flaticon.com) | [音乐🎵(zapsplat)](www.zapsplat.com) | [配音🎤(Google Translate)](https://translate.google.cn)

> Download( CatchTigerChess.jar ) 👉 [[Google Drive]](https://drive.google.com/open?id=1Q1CaD6OfE5tsRKfXNgorg4wl437H2JVQ)
## 游戏介绍 📖
   * 对战双方分别执代表老虎的棋子（1枚）和狗狗的棋子（16枚）。
   * 行棋之前，双方将各自的棋子放置于棋盘上，老虎放在大正方形的中点，狗狗围绕着大正方形放在交点上。
   
   <p align="center"> 
    <img src="https://github.com/BaldwinHe/JavaBigHomework/blob/master/demo/demo_first.png">
   </p>  
    
   * 游戏开始后，由执老虎棋子的一方先走。轮到走棋的一方，将某个棋子从一个交叉点走到与之相邻的另一个交叉点。双方每次只能走一步。且不能将棋子移动到已经有棋子占据的交叉点上。
   * 狗狗一方的棋子不能伤害老虎，当 *老虎走入顶点的陷阱位置* 或者 *老虎无路可走* ，狗狗方取得胜利。
   * 老虎一方可以吃掉狗狗方的棋子，方法是将自己的棋子移动到两枚狗狗棋子的中间，就可以吃掉这两枚棋子，有时甚至可以同时吃掉4枚甚至6枚狗狗棋子。如果老虎一方能将狗狗一方的棋子数吃到只剩2枚或者更少，则老虎方获胜。
## Demo 🙌
  `在实现游戏功能的前提下，实现了悔棋和背景音乐的播放并增加了游戏限时功能。`
    
  <p align="center"> 
    <img src="https://github.com/BaldwinHe/JavaBigHomework/blob/master/demo/CatchTigerChess_Demo.gif">
  </p>  
    
    
---
## 开发进程⛽️😊

### 注意!
* 最好在本地装好Github客户端，方便查看修改的代码文件。
* 点击Download，下载最新版代码。
* 修改前最好运行程序(`CatchTigerChess.java`)来检查程序能否正常运行。
* 最好不修改已写好的代码。
* 写完了相应的代码(在本地测试无误),在 **experiment** 分支上传代码(有注释最好啦~)。
* 文件结构

  ```
  ├── LICENSE
  ├── README.md
  ├── build/
  ├── build.xml
  ├── manifest.mf
  ├── nbproject/
  ├── src
  │   ├── catchtigerchess
  │   │   └── CatchTigerChess.java //Game Entrance
  │   ├── gameWindow
  │   │   ├── ChessBoardCanvas.form
  │   │   ├── ChessBoardCanvas.java
  │   │   ├── ChessBoarder.java
  │   │   ├── ChessClick.java
  │   │   ├── ChessPieces.java
  │   │   ├── ChessWindow.form
  │   │   └── ChessWindow.java
  │   ├── imageLibary/
  │   ├── musicLibary/
  │   ├── utils
  │   │   ├── PlayMusic.java
  │   │   ├── Config.java
  │   │   └── RegretData.java
  └── test
  ```

  
### TODO! :
> 需要补的代码都在相应文件的 注释里(*`// TODO:`*):
  
  * (`2019-04-24 -> 2019-04-28`)
    - [x] 初始页面搭建 @汪有为 | @何炎柏
    - [x] 棋子移动 @何炎柏

  * (`2019-04-29 -> 2019-05-04`)
    - [x] 吃棋子 @杨焕煜
  
  * (`2019-05-05 -> 2019-05-11`)
    - [x] 悔棋操作 @杨焕煜
    - [x] 游戏计时 @何炎柏
    
  * (`2019-05-12 -> 2019-05-18`)
    - [x] 游戏声音 @杨焕煜
    - [x] 判断输赢 @汪有为
  
  * (`2019-05-19 -> 2019-05-25`)
    - [x]  界面|音乐优化|代码优化 @何炎柏
    - [x] 实验报告 @杨焕煜 | @汪有为 
 
 * (`2019-05-26 -> ?`)
    - [ ] 测试 
    - [ ] 实验报告 @杨焕煜 | @汪有为 | @何炎柏

    
## Good Luck !
