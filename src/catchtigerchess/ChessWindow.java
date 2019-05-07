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
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
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
    private final ImageIcon TIME;
    private MouseAdapter MouseListener;
    private static int gameTime;
    static Timer timer;
    private Boolean gameIsStart;
    private ActionListener updateProBar;
    ChessBoardCanvas chessCanvas;
    
    /**
     * Creates new form ChessWindow
     */
    public ChessWindow(Integer totalTime) {
        initComponents();
        TIGERMOVE = new javax.swing.ImageIcon(getClass().getResource("/images/TIGERMOVE.png"));
        DOGMOVE = new javax.swing.ImageIcon(getClass().getResource("/images/DOGMOVE.png"));
        TIME = new javax.swing.ImageIcon(getClass().getResource("/images/time.png"));
        GAMEOVER = new javax.swing.ImageIcon(getClass().getResource("/images/GAMEOVER.png"));
        gameIsStart = false;
        PlayerNow = new String("Tiger");
        chessBoarder = new ChessBoarder();
        MouseListener = new ChessClick();
        
        this.setTitle("CatchTigerChess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        chessCanvas = new ChessBoardCanvas();
        chessCanvas.setBounds(0, 0, 500, 700);
        chessCanvas.repaint();
        chessCanvas.addMouseListener(MouseListener);
        gameBoard.add(chessCanvas);
        chessCanvas.removeMouseListener(MouseListener);
    }
    private void startNewGame(Integer totalTime){
        gameIsStart = true;
        timeLeftBar.setMinimum(0);
        if (totalTime < 0) {
            timeLeftBar.setMaximum(1000000000);
            gameTime = 1000000000;
            totalTimeText.setText("Total Time : " + "INFINITY~");
        }else{
            gameTime = totalTime*60;
            timeLeftBar.setMaximum(gameTime);
            totalTimeText.setText("Total Time : " + totalTime.toString() + " minutes");
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = timeLeftBar.getValue() + 1;
                timeLeftBar.setValue(value);
            }
        });
        timeLeftBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Integer min = timeLeftBar.getValue()/60;
                Integer second = timeLeftBar.getValue()%60;
                timeNowText.setText("Time Now : " + min.toString() + " m " + second.toString() + " s");
                if (timeLeftBar.getValue() == gameTime) {
                    timer.stop();
                    
                    setGameStatus("GAMEOVER");
                    chessCanvas.removeMouseListener(MouseListener);
                }
            }
        });
        timer.start();
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
        if (status.equals("TIGERWIN")) {
            System.out.println("TIGER WIN");
        }
        if (status.equals("DOGWIN")){
            System.out.println("DOG WIN");
        }
    }
    private static boolean isNumeric(String str){  
        Pattern pattern = Pattern.compile("[0-9]*");  
        return pattern.matcher(str).matches();     
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
        regretButton = new javax.swing.JButton();
        timeLeftBar = new javax.swing.JProgressBar();
        timeNowText = new javax.swing.JLabel();
        totalTimeText = new javax.swing.JLabel();
        voiceControlButton = new javax.swing.JToggleButton();
        gameControllButton = new javax.swing.JButton();

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

        regretButton.setText("I regret it so much");
        regretButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regretButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gameStatusLayout = new javax.swing.GroupLayout(gameStatus);
        gameStatus.setLayout(gameStatusLayout);
        gameStatusLayout.setHorizontalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameStatusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gameStatusLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(gameStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(gameStatusLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(regretButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        gameStatusLayout.setVerticalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameStatusImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(gameStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(regretButton)
                .addGap(64, 64, 64))
        );

        timeLeftBar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        timeLeftBar.setBorderPainted(false);
        timeLeftBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        timeNowText.setText("Time Now : 0 m 0 s");

        totalTimeText.setText("Total Time : INFINITY");

        voiceControlButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/volume.png"))); // NOI18N
        voiceControlButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        voiceControlButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        voiceControlButton.setPreferredSize(new java.awt.Dimension(48, 48));
        voiceControlButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rock-and-roll.png"))); // NOI18N
        voiceControlButton.setRequestFocusEnabled(false);
        voiceControlButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mute.png"))); // NOI18N
        voiceControlButton.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/volume.png"))); // NOI18N
        voiceControlButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mute.png"))); // NOI18N
        voiceControlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voiceControlButtonActionPerformed(evt);
            }
        });

        gameControllButton.setText("Start");
        gameControllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gameControllButtonMouseClicked(evt);
            }
        });
        gameControllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameControllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gameStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(timeNowText)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(totalTimeText))
                                    .addComponent(timeLeftBar, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(121, 121, 121)
                                .addComponent(voiceControlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(gameControllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gameStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(timeLeftBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(timeNowText)
                                    .addComponent(totalTimeText)))
                            .addComponent(voiceControlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(gameControllButton))
                    .addComponent(gameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        voiceControlButton.getAccessibleContext().setAccessibleName("soundControlButton");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void voiceControlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voiceControlButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_voiceControlButtonActionPerformed

    private void gameControllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameControllButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gameControllButtonActionPerformed
    
    private void gameControllButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gameControllButtonMouseClicked
        // TODO add your handling code here:
        String inputValue = JOptionPane.showInputDialog(null,"Enter the game time(minutes) to continue","Please",JOptionPane.QUESTION_MESSAGE); 
        Integer time;
        if(inputValue == null){
            return;          
        }else if(inputValue.isEmpty() || !isNumeric(inputValue.toString()) || (inputValue.length() > 8)){
            
            time = -1;
        }else{
            time = Integer.parseInt(inputValue.toString());
        }
        if (gameIsStart) {
            timer.stop();
            gameBoard.remove(chessCanvas);
            timeLeftBar.setValue(0);
            PlayerNow = new String("Tiger");
            setGameStatus("TIGERMOVE");
            chessBoarder = new ChessBoarder();
            MouseListener = new ChessClick();
            chessCanvas = new ChessBoardCanvas();
            chessCanvas.setBounds(0, 0, 500, 700);
            chessCanvas.addMouseListener(MouseListener);
            gameBoard.add(chessCanvas);
            chessCanvas.repaint();
            startNewGame(time);
        }
        else{
            gameControllButton.setText("Restart");
            chessCanvas.addMouseListener(MouseListener);
            startNewGame(time);
        }
    }//GEN-LAST:event_gameControllButtonMouseClicked

    private void regretButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regretButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regretButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gameBoard;
    private javax.swing.JButton gameControllButton;
    private static javax.swing.JPanel gameStatus;
    private static javax.swing.JLabel gameStatusImage;
    private static javax.swing.JLabel gameStatusText;
    private javax.swing.JButton regretButton;
    private static javax.swing.JProgressBar timeLeftBar;
    private javax.swing.JLabel timeNowText;
    private javax.swing.JLabel totalTimeText;
    private javax.swing.JToggleButton voiceControlButton;
    // End of variables declaration//GEN-END:variables
}
