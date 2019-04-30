/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtigerchess;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mouseclick.ChessClick;
import javax.swing.JProgressBar;
import java.awt.Image;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author heyanbai
 */
public class ChessWindow extends JFrame {
    
    public static ChessBoarder chessBoarder;
    private static String PlayerNow;
    private static ImageIcon TIGERMOVE;
    private static ImageIcon DOGMOVE;
    private static ImageIcon GAMEOVER;
    private MouseAdapter MouseListener;
    private static int gameTime;
    static Timer timer;
    private ActionListener updateProBar;
    ChessBoardCanvas chessCanvas;
    
    /**
     * Creates new form ChessWindow
     */
    public ChessWindow(Integer totalTime) {
        initComponents();
        //System.err.println(this.getClass().getResource("..").getPath());
        TIGERMOVE = new javax.swing.ImageIcon(getClass().getResource("/images/TIGERMOVE.png"));
        DOGMOVE = new javax.swing.ImageIcon(getClass().getResource("/images/DOGMOVE.png"));
        GAMEOVER = new javax.swing.ImageIcon(getClass().getResource("/images/GAMEOVER.png"));
        
        totalTimeText.setText("Total Time : " + totalTime.toString() + " minutes");
        PlayerNow = new String("Tiger");
        chessBoarder = new ChessBoarder();
        MouseListener = new ChessClick();
        
        this.setTitle("CatchTigerChess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        timeLeftBar.setMinimum(0);
        if (totalTime < 0) {
            timeLeftBar.setMaximum(100000000);
            gameTime = 100000000;
        }else{
            gameTime = totalTime*60;
            timeLeftBar.setMaximum(gameTime);
        }
        final Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = timeLeftBar.getValue() + 1;
                timeLeftBar.setValue(value);
            }

        });
//        long sTime = System.currentTimeMillis(); 
        timeLeftBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                System.out.println(timeLeftBar.getValue());
                Integer min = timeLeftBar.getValue()/60;
                Integer second = timeLeftBar.getValue()%60;
                timeNowText.setText("Time Now : " + min.toString() + " m " + second.toString() + " s");
                if (timeLeftBar.getValue() == gameTime) {
                    timer.stop();
//                    long eTime = System.currentTimeMillis();
                    
                    setGameStatus("GAMEOVER");
                    chessCanvas.removeMouseListener(MouseListener);
//                    System.err.println(eTime - sTime);
//                    System.exit(0);
                }
            }
        });
        timer.start();
        chessCanvas = new ChessBoardCanvas();
        chessCanvas.setBounds(0, 0, 500, 700);
        chessCanvas.repaint();
        chessCanvas.addMouseListener(MouseListener);
        gameBoard.add(chessCanvas);
    }
    
    public static void setGameStatus(String status){
        if(status.equals("TIGERMOVE")){
            gameStatusImage.setIcon(TIGERMOVE);
            gameStatusText.setText("<html><font color=\"94A7DC\" size=\"6\" face=\"Gill Sans\">The </font><font color=\"#EFCAC2\" size=\"7\" face=\"Gill Sans\">tiger</font><font color=\"94A7DC\" size=\"6\" face=\"Gill Sans\"> is ready to move !</font></html>");
        }
        if(status.equals("DOGMOVE")){
            gameStatusImage.setIcon(DOGMOVE);
            gameStatusText.setText("<html><font color=\"94A7DC\" size=\"6\" face=\"Gill Sans\">The </font><font color=\"#EFCAC2\" size=\"7\" face=\"Gill Sans\">dogs</font><font color=\"94A7DC\" size=\"6\" face=\"Gill Sans\"> are ready to move !</font></html>");
        }
        if (status.equals("GAMEOVER")) {
            gameStatusImage.setIcon(GAMEOVER);
            gameStatusText.setText("<html><font color=\"#EFCAC2\" size=\"7\" face=\"Gill Sans\">Time out. Game over.</font></html>");
        }
    }
    
    public static void setPlayer(){
        System.out.println(PlayerNow);
        if(PlayerNow.equals("Tiger")){
            PlayerNow = "Dog";
            setGameStatus("DOGMOVE");
        }
        else {
            PlayerNow = "Tiger";
            setGameStatus("TIGERMOVE");
        }
    }
    public static String getPlayer(){
        return PlayerNow;
    }
    public static ChessPieces[][] getChessPieces(){
        return chessBoarder.getChessPieces();
    }
    
    public static void eatChess(int x, int y){
        if(PlayerNow.equals("Tiger")){
            if(x-1>=0 && x-1<=4 && x+1>=0 &&x+1<=4){
                if( (chessBoarder.hasRoad(x,y,x-1,y) && chessBoarder.hasPiece(x-1, y)) && (chessBoarder.hasPiece(x+1, y)&& chessBoarder.hasRoad(x,y,x+1,y))){
                chessBoarder.eatPiece(x-1, y);
                chessBoarder.eatPiece(x+1, y);
                chessBoarder.killDog(2);
                }
            }
            if(y-1>=0 &&y-1<=6 && y+1>=0&&y+1<=6){
                if((chessBoarder.hasRoad(x,y,x,y-1) && chessBoarder.hasPiece(x, y-1)) && (chessBoarder.hasPiece(x, y+1) && chessBoarder.hasRoad(x,y,x,y+1))){
                chessBoarder.eatPiece(x, y-1);
                chessBoarder.eatPiece(x, y+1);
                chessBoarder.killDog(2);
                } 
            }
            if(x-1>=0 && x-1<=4 && x+1>=0 && x+1<=4 && y-1>=0 &&y-1<=6 && y+1>=0&&y+1<=6){
                if((chessBoarder.hasRoad(x,y,x-1,y-1) && chessBoarder.hasPiece(x-1, y-1)) && (chessBoarder.hasPiece(x+1, y+1) && chessBoarder.hasRoad(x,y,x+1,y+1))){
                chessBoarder.eatPiece(x-1, y-1);
                chessBoarder.eatPiece(x+1, y+1);
                chessBoarder.killDog(2);
                }
               if((chessBoarder.hasRoad(x,y,x-1,y+1) && chessBoarder.hasPiece(x-1, y+1)) && (chessBoarder.hasPiece(x+1, y-1) && chessBoarder.hasRoad(x,y,x+1,y-1))){
                chessBoarder.eatPiece(x-1, y+1);
                chessBoarder.eatPiece(x+1, y-1);
                chessBoarder.killDog(2);
                }
            }
            
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gameBoard = new JPanel();
        gameStatus = new javax.swing.JPanel();
        gameStatusImage = new javax.swing.JLabel();
        gameStatusText = new javax.swing.JLabel();
        timeLeftBar = new javax.swing.JProgressBar();
        timeNowText = new javax.swing.JLabel();
        totalTimeText = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        gameBoard.setBackground(new java.awt.Color(60, 255, 65));
        gameBoard.setPreferredSize(new java.awt.Dimension(375, 525));

        javax.swing.GroupLayout gameBoardLayout = new javax.swing.GroupLayout(gameBoard);
        gameBoard.setLayout(gameBoardLayout);
        gameBoardLayout.setHorizontalGroup(
            gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        gameBoardLayout.setVerticalGroup(
            gameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        gameStatus.setBackground(new java.awt.Color(170, 248, 234));

        gameStatusImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TIGERMOVE.png"))); // NOI18N

        gameStatusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gameStatusText.setText("<html><font color=\"94A7DC\" size=\"6\" face=\"Gill Sans\">The </font><font color=\"#EFCAC2\" size=\"7\" face=\"Gill Sans\">tiger</font><font color=\"94A7DC\" size=\"6\" face=\"Gill Sans\"> is ready to move !</font></html>");
        gameStatusText.setToolTipText("");
        gameStatusText.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gameStatusText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout gameStatusLayout = new javax.swing.GroupLayout(gameStatus);
        gameStatus.setLayout(gameStatusLayout);
        gameStatusLayout.setHorizontalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameStatusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(gameStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );
        gameStatusLayout.setVerticalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameStatusImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(gameStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        timeLeftBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        timeLeftBar.setBorderPainted(false);
        timeLeftBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        timeNowText.setText("Time Now : 0 m 0 s");

        totalTimeText.setText("Total Time : INFINITY");

        jScrollPane1.setBackground(new java.awt.Color(255, 63, 65));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gameStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(timeNowText)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(totalTimeText))
                                .addComponent(timeLeftBar, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gameStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(timeLeftBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timeNowText)
                            .addComponent(totalTimeText))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(gameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gameBoard;
    private static javax.swing.JPanel gameStatus;
    private static javax.swing.JLabel gameStatusImage;
    private static javax.swing.JLabel gameStatusText;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JProgressBar timeLeftBar;
    private javax.swing.JLabel timeNowText;
    private javax.swing.JLabel totalTimeText;
    // End of variables declaration//GEN-END:variables
}
