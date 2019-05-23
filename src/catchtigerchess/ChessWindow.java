/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtigerchess;
import config.Config;
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
import catchtigerchess.regretData;
import catchtigerchess.playMusic;
/**
 *
 * @author heyanbai
 */
public class ChessWindow extends JFrame {
    
    public static ChessBoarder chessBoarder;
    private static String PlayerNow;
    private static ImageIcon tigerMoveIcon;
    private static ImageIcon dogMoveIcon;
    private static ImageIcon gameOverIcon;
    private static ImageIcon startBtnIcon;
    private static ImageIcon winIcon;
    private static ImageIcon restartBtnIcon;
    private static ImageIcon PeaceIcon;
    private String totalTimeString;
    private static MouseAdapter MouseListener;
    private static int gameTime;
    private static Timer timer;
    private Boolean gameIsStart;
    private ActionListener updateProBar;
    private static ChessBoardCanvas chessCanvas;
    public static playMusic music;
    private static String voice;
    
    /**
     * Creates new form ChessWindow
     */
    public ChessWindow(Integer totalTime) {
        initComponents();
        tigerMoveIcon = new javax.swing.ImageIcon(getClass().getResource("/images/TIGERMOVE.png"));
        dogMoveIcon = new javax.swing.ImageIcon(getClass().getResource("/images/DOGMOVE.png"));
        gameOverIcon = new javax.swing.ImageIcon(getClass().getResource("/images/GAMEOVER.png"));
        startBtnIcon = new javax.swing.ImageIcon(getClass().getResource("/images/start.png"));
        restartBtnIcon = new javax.swing.ImageIcon(getClass().getResource("/images/restart.png"));
        PeaceIcon = new javax.swing.ImageIcon(getClass().getResource("/images/peace.png"));
        winIcon = new javax.swing.ImageIcon(getClass().getResource("/images/WIN.png"));
        gameIsStart = false;
        PlayerNow = new String("Tiger");
        chessBoarder = new ChessBoarder();
        MouseListener = new ChessClick();
        voice = new String("on");
        music = new playMusic();
        music.init();
        
        this.setTitle("CatchTigerChess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        regretButton.setEnabled(false);
        chessCanvas = new ChessBoardCanvas();
        chessCanvas.setBounds(0, 0, 500, 700);
        chessCanvas.repaint();
        chessCanvas.addMouseListener(MouseListener);
        gameBoard.add(chessCanvas);
        chessCanvas.removeMouseListener(MouseListener);
        music.playStartMusic();
        
    }
    private void startNewGame(Integer totalTime){
        Config.regretStack.clear();
        regretButton.setEnabled(false);
        gameIsStart = true;
        timeLeftBar.setMinimum(0);
        if (totalTime < 0) {
            timeLeftBar.setMaximum(1000000000);
            gameTime = 1000000000;
            totalTimeString = new String("INFINITY");
        }else{
            gameTime = totalTime*60;
            timeLeftBar.setMaximum(gameTime);
            totalTimeString = new String(totalTime.toString() + " m");
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
                timeNowText.setText("Time Now : " + min.toString() + " m " + second.toString() + " s / " + totalTimeString);
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
            if (regretButton.isEnabled() == false && Config.regretStack.size() >= 2) {
                regretButton.setEnabled(true);
            }
            gameStatusImage.setIcon(tigerMoveIcon);
            gameStatusText.setText("<html><font color=\"94A7DC\" size=\"5\" face=\"Gill Sans\">The </font><font color=\"#EFCAC2\" size=\"6\" face=\"Gill Sans\">tiger</font><font color=\"94A7DC\" size=\"5\" face=\"Gill Sans\"> is ready to move !</font></html>");
        }
        if(status.equals("DOGMOVE")){
            if (regretButton.isEnabled() == false && Config.regretStack.size() >= 2) {
                regretButton.setEnabled(true);
            }
            gameStatusImage.setIcon(dogMoveIcon);
            gameStatusText.setText("<html><font color=\"94A7DC\" size=\"5\" face=\"Gill Sans\">The </font><font color=\"#EFCAC2\" size=\"6\" face=\"Gill Sans\">dogs</font><font color=\"94A7DC\" size=\"5\" face=\"Gill Sans\"> are ready to move !</font></html>");
        }
        if (status.equals("GAMEOVER")) {
            gameStatusImage.setIcon(gameOverIcon);
            regretButton.setEnabled(false);
            gameStatusText.setText("<html><font color=\"#EFCAC2\" size=\"6\" face=\"Gill Sans\">Time out. Game over.</font></html>");
        }
        if (status.equals("TIGERWIN")) {
            gameStatusImage.setIcon(winIcon);
            gameStatusText.setText("<html><font color=\"#EFCAC2\" size=\"6\" face=\"Gill Sans\">Tiger win. Game over.</font></html>");
            regretButton.setEnabled(false);
            timer.stop();
            chessCanvas.removeMouseListener(MouseListener);
        }
        if (status.equals("DOGWIN")){
            gameStatusImage.setIcon(winIcon);
            gameStatusText.setText("<html><font color=\"#EFCAC2\" size=\"6\" face=\"Gill Sans\">Dog win. Game over.</font></html>");
            regretButton.setEnabled(false);
            timer.stop();
            chessCanvas.removeMouseListener(MouseListener);
        }
    }
    private static boolean isNumeric(String str){  
        Pattern pattern = Pattern.compile("[0-9]*");  
        return pattern.matcher(str).matches();     
    }  
    public static void setPlayer(){
        if (regretButton.isEnabled() == false && Config.regretStack.size() >= 2) regretButton.setEnabled(true);
        if (Config.regretStack.size() < 2) regretButton.setEnabled(false);
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
    
    public static boolean isVoiceON(){
        if(voice.equals("on")){
            return true;
        } else return false;
    }
    
    public static boolean eatChess(int x, int y, regretData regretTemp){
        int judge = 0;
        if(PlayerNow.equals("Tiger")){
            if(x-1>=0 && x-1<=4 && x+1>=0 &&x+1<=4){
                if( (chessBoarder.hasRoad(x,y,x-1,y) && chessBoarder.hasPiece(x-1, y)) && (chessBoarder.hasPiece(x+1, y)&& chessBoarder.hasRoad(x,y,x+1,y))){
                chessBoarder.delPiece(x-1, y);
                chessBoarder.delPiece(x+1, y);
                chessBoarder.killDog(2);
                regretTemp.degree_0 = true;
                judge = 1;
                }
                else regretTemp.degree_0 = false;
            }
            if(y-1>=0 &&y-1<=6 && y+1>=0&&y+1<=6){
                if((chessBoarder.hasRoad(x,y,x,y-1) && chessBoarder.hasPiece(x, y-1)) && (chessBoarder.hasPiece(x, y+1) && chessBoarder.hasRoad(x,y,x,y+1))){
                chessBoarder.delPiece(x, y-1);
                chessBoarder.delPiece(x, y+1);
                chessBoarder.killDog(2);
               regretTemp.degree_90 = true;
                judge = 1;
                } 
                else regretTemp.degree_90 = false;
            }
            if(x-1>=0 && x-1<=4 && x+1>=0 && x+1<=4 && y-1>=0 &&y-1<=6 && y+1>=0&&y+1<=6){
                if((chessBoarder.hasRoad(x,y,x-1,y-1) && chessBoarder.hasPiece(x-1, y-1)) && (chessBoarder.hasPiece(x+1, y+1) && chessBoarder.hasRoad(x,y,x+1,y+1))){
                chessBoarder.delPiece(x-1, y-1);
                chessBoarder.delPiece(x+1, y+1);
                chessBoarder.killDog(2);
                regretTemp.degree_135 = true;
                judge = 1;
                }
                else regretTemp.degree_135 = false;
                
               if((chessBoarder.hasRoad(x,y,x-1,y+1) && chessBoarder.hasPiece(x-1, y+1)) && (chessBoarder.hasPiece(x+1, y-1) && chessBoarder.hasRoad(x,y,x+1,y-1))){
                chessBoarder.delPiece(x-1, y+1);
                chessBoarder.delPiece(x+1, y-1);
                chessBoarder.killDog(2);
                regretTemp.degree_45 = true;
                judge = 1;
                }
               else regretTemp.degree_45 = false;
            }
            
        }
        if(judge == 1)
            return true;
        else return false;
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
        gameControllButton = new javax.swing.JButton();
        voiceControlButton = new javax.swing.JToggleButton();
        timeNowText = new javax.swing.JLabel();

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

        gameStatus.setBackground(new java.awt.Color(235, 246, 247));

        gameStatusImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TIGERMOVE.png"))); // NOI18N

        gameStatusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gameStatusText.setText("<html><font color=\"769164\" size=\"5\" face=\"Gill Sans\">The </font><font color=\"#C97586\" size=\"6\" face=\"Gill Sans\">tiger</font><font color=\"94A7DC\" size=\"5\" face=\"Gill Sans\"> is ready to move !</font></html>");
        gameStatusText.setToolTipText("");
        gameStatusText.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gameStatusText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        regretButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/regret.png"))); // NOI18N
        regretButton.setText("I regret it so much");
        regretButton.setBorder(null);
        regretButton.setEnabled(false);
        regretButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regretButtonMouseClicked(evt);
            }
        });
        regretButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regretButtonActionPerformed(evt);
            }
        });

        timeLeftBar.setAutoscrolls(true);
        timeLeftBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        timeLeftBar.setPreferredSize(new java.awt.Dimension(148, 33));
        timeLeftBar.setStringPainted(true);

        gameControllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/start.png"))); // NOI18N
        gameControllButton.setAlignmentY(0.0F);
        gameControllButton.setBorder(null);
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

        voiceControlButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/volume.png"))); // NOI18N
        voiceControlButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        voiceControlButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        voiceControlButton.setPreferredSize(new java.awt.Dimension(48, 48));
        voiceControlButton.setRequestFocusEnabled(false);
        voiceControlButton.setRolloverEnabled(false);
        voiceControlButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mute.png"))); // NOI18N
        voiceControlButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voiceControlButtonMouseClicked(evt);
            }
        });
        voiceControlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voiceControlButtonActionPerformed(evt);
            }
        });

        timeNowText.setText("Time Now : 0 m 0 s/ INFINITY");

        javax.swing.GroupLayout gameStatusLayout = new javax.swing.GroupLayout(gameStatus);
        gameStatus.setLayout(gameStatusLayout);
        gameStatusLayout.setHorizontalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                                .addComponent(gameStatusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                                .addComponent(gameControllButton)
                                .addGap(43, 43, 43))))
                    .addGroup(gameStatusLayout.createSequentialGroup()
                        .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gameStatusText)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                                .addComponent(voiceControlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(regretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(timeLeftBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(timeNowText, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        gameStatusLayout.setVerticalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(gameStatusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(gameStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(timeLeftBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeNowText)
                .addGap(75, 75, 75)
                .addComponent(gameControllButton)
                .addGap(47, 47, 47)
                .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(voiceControlButton, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(regretButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        voiceControlButton.getAccessibleContext().setAccessibleName("soundControlButton");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gameBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gameStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

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
            gameControllButton.setIcon(restartBtnIcon);
            gameControllButton.setBorder(null);
            chessCanvas.addMouseListener(MouseListener);
            startNewGame(time);
        }
        if(voice.equals("on")){
            music.stopStartMusiic();
            music.playBgMusic();
        }
        
    }//GEN-LAST:event_gameControllButtonMouseClicked

    
    private void regretButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regretButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regretButtonActionPerformed

    private void regretButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regretButtonMouseClicked
        // TODO add your handling code here:
        if (regretButton.isEnabled() == false) return;
        regretButton.setEnabled(false);
        backOneStep();
        backOneStep();
        chessCanvas.repaint();
        music.playRegratMusic();
    }//GEN-LAST:event_regretButtonMouseClicked

    private void voiceControlButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voiceControlButtonMouseClicked
        // TODO add your handling code here:
        if(gameIsStart){
            if(voice.equals("on")){
            voice = "off";
            music.stopBgMusiic();
            }else{
                voice = "on";
                music.playBgMusic();
            }
        }else{
        if(voice.equals("on")){
            voice = "off";
            music.stopStartMusiic();
            }else{
                voice = "on";
                music.playStartMusic();
            }
        }
        
    }//GEN-LAST:event_voiceControlButtonMouseClicked

    private void backOneStep(){
        regretData del = new regretData();
        del = Config.regretStack.pop();
        System.out.println(del.src_x+" "+del.src_y+" "+del.des_x+" "+del.des_y);
        chessBoarder.delPiece(del.des_x, del.des_y);
        chessBoarder.addPiece(del.src_x, del.src_y,del.pieceId);
        if (del.pieceId == 0) {
            chessBoarder.TigerLocationX = del.src_x;
            chessBoarder.TigerLocationY = del.src_y;
        }
        chessBoarder.setPoint(null);
        if(del.degree_0 == true){
            chessBoarder.addPiece(del.des_x-1, del.des_y,1);
            chessBoarder.addPiece(del.des_x+1, del.des_y,1);
            chessBoarder.dogCount += 2; 
        }
        if(del.degree_90 == true){
            chessBoarder.addPiece(del.des_x, del.des_y-1,1);
            chessBoarder.addPiece(del.des_x, del.des_y+1,1);
            chessBoarder.dogCount += 2; 
        }
        if(del.degree_135 == true){
            chessBoarder.addPiece(del.des_x-1, del.des_y-1,1);
            chessBoarder.addPiece(del.des_x+1, del.des_y+1,1);
            chessBoarder.dogCount += 2; 
        }
        if(del.degree_45 == true){
            chessBoarder.addPiece(del.des_x-1, del.des_y+1,1);
            chessBoarder.addPiece(del.des_x+1, del.des_y-1,1);
            chessBoarder.dogCount += 2; 
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gameBoard;
    private javax.swing.JButton gameControllButton;
    private static javax.swing.JPanel gameStatus;
    private static javax.swing.JLabel gameStatusImage;
    private static javax.swing.JLabel gameStatusText;
    private static javax.swing.JButton regretButton;
    private static javax.swing.JProgressBar timeLeftBar;
    private javax.swing.JLabel timeNowText;
    private javax.swing.JToggleButton voiceControlButton;
    // End of variables declaration//GEN-END:variables
}
