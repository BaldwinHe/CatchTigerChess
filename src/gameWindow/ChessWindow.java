/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameWindow;
import utils.ChessPieces;
import utils.RegretData;
import utils.PlayMusic;
import config.Config;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utils.ChessClick;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;
/**
 * Main interface
 * @author 何炎柏 | 汪有为
 */
public class ChessWindow extends JFrame {
    
    public static ChessBoarder chessBoarder;
    private static String PlayerNow;
    private static ImageIcon tigerMoveIcon;
    private static ImageIcon dogMoveIcon;
    private static ImageIcon gameOverIcon;
    private static ImageIcon winIcon;
    private static ImageIcon restartBtnIcon;
    private static ImageIcon PeaceIcon;
    private String totalTimeString;
    private static MouseAdapter MouseListener;
    private static int gameTime;
    private static Timer timer;
    private Boolean gameIsStart;
    private static ChessBoardCanvas chessCanvas;
    public static PlayMusic music;
    private static Boolean isVoiceOn;
    
    /**
     * Creates new form ChessWindow
     * @param totalTime Game time
     */
    public ChessWindow(Integer totalTime) {
        initComponents();
        tigerMoveIcon = new javax.swing.ImageIcon(getClass().getResource("/imageLibary/TIGERMOVE.png"));
        dogMoveIcon = new javax.swing.ImageIcon(getClass().getResource("/imageLibary/DOGMOVE.png"));
        gameOverIcon = new javax.swing.ImageIcon(getClass().getResource("/imageLibary/GAMEOVER.png"));
        restartBtnIcon = new javax.swing.ImageIcon(getClass().getResource("/imageLibary/restart.png"));
        PeaceIcon = new javax.swing.ImageIcon(getClass().getResource("/imageLibary/peace.png"));
        winIcon = new javax.swing.ImageIcon(getClass().getResource("/imageLibary/WIN.png"));
        gameIsStart = false;
        PlayerNow = "Tiger";
        chessBoarder = new ChessBoarder();
        MouseListener = new ChessClick();
        isVoiceOn = Boolean.TRUE;
        music = new PlayMusic();
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
        gameStatusImage.setIcon(tigerMoveIcon);
        gameStatusText.setText("<html><font color=\"6694F0\" size=\"5\" face=\"Gill Sans\">The </font><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">tiger</font><font color=\"6694F0\" size=\"5\" face=\"Gill Sans\"> is ready to move !</font></html>");
        Config.regretStack.clear();
        regretButton.setEnabled(false);
        gameIsStart = true;
        timeLeftBar.setMinimum(0);
        if (totalTime < 0) {
            timeLeftBar.setMaximum(1000000000);
            gameTime = 1000000000;
            totalTimeString = "INFINITY";
        }else{
            gameTime = totalTime*60;
            timeLeftBar.setMaximum(gameTime);
            totalTimeString = totalTime.toString() + " m";
        }
        timer = new Timer(1000, (ActionEvent e) -> {
            int value = timeLeftBar.getValue() + 1;
            timeLeftBar.setValue(value);
        });
        timeLeftBar.addChangeListener((ChangeEvent e) -> {
            Integer min = timeLeftBar.getValue()/60;
            Integer second = timeLeftBar.getValue()%60;
            timeNowText.setText("Time Now : " + min.toString() + " m " + second.toString() + " s / " + totalTimeString);
            if (timeLeftBar.getValue() == gameTime) {
                timer.stop();
                setGameStatus("GAMEOVER");
                music.playTimeOutMusic();
                chessCanvas.removeMouseListener(MouseListener);
            }
        });
        timer.start();
    }

    /**
     * Set game status(move|win|timeout)
     * @param status Game status string
     */
    public static void setGameStatus(String status){
        if(status.equals("TIGERMOVE")){
            if (regretButton.isEnabled() == false && Config.regretStack.size() >= 2) {
                regretButton.setEnabled(true);
            }
            gameStatusImage.setIcon(tigerMoveIcon);
            gameStatusText.setText("<html><font color=\"6694F0\" size=\"5\" face=\"Gill Sans\">The </font><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">tiger</font><font color=\"6694F0\" size=\"5\" face=\"Gill Sans\"> is ready to move !</font></html>");
        }
        if(status.equals("DOGMOVE")){
            if (regretButton.isEnabled() == false && Config.regretStack.size() >= 2) {
                regretButton.setEnabled(true);
            }
            gameStatusImage.setIcon(dogMoveIcon);
            gameStatusText.setText("<html><font color=\"6694F0\" size=\"5\" face=\"Gill Sans\">The </font><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">dogs</font><font color=\"6694F0\" size=\"5\" face=\"Gill Sans\"> are ready to move !</font></html>");
        }
        if (status.equals("GAMEOVER")) {
            gameStatusImage.setIcon(gameOverIcon);
            regretButton.setEnabled(false);
            gameStatusText.setText("<html><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">Time out. Game over.</font></html>");
        }
        if (status.equals("TIGERWIN")) {
            music.playWonTigerMusic();
            gameStatusImage.setIcon(winIcon);
            gameStatusText.setText("<html><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">Tiger win. Game over.</font></html>");
            regretButton.setEnabled(false);
            timer.stop();
            chessCanvas.removeMouseListener(MouseListener);
        }
        if (status.equals("DOGWIN")){
            music.playWonDogMusic();
            gameStatusImage.setIcon(winIcon);
            gameStatusText.setText("<html><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">Dog win. Game over.</font></html>");
            regretButton.setEnabled(false);
            timer.stop();
            chessCanvas.removeMouseListener(MouseListener);
        }
    }
    private static boolean isNumeric(String str){  
        Pattern pattern = Pattern.compile("[0-9]*");  
        return pattern.matcher(str).matches();     
    }  

    /**
     * Set player now
     */
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

    /**
     * Get the player now
     * @return Returns Tiger if the player now is tiger, otherwise returns Dog
     */
    public static String getPlayer(){
        return PlayerNow;
    }
    public static ChessPieces[][] getChessPieces(){
        return chessBoarder.getChessPieces();
    }
    
    /**
     * Check to see if the sound is on
     * @return Returns true if the sound is on, otherwise returns false
     */
    public static boolean isMusicOn(){
        return isVoiceOn;
    }
    
    /**
     * The tiger takes the piece at coordinates (X, Y)
     * @param x X coordinate of tiger
     * @param y Y coordinate of tiger
     * @param regretTemp Set the direction to eat the pieces
     * @return Returns true if the tiger takes the piece, otherwise returns false
     */
    public static boolean eatChess(int x, int y, RegretData regretTemp){
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
        return judge == 1;
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

        gameStatusImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageLibary/welcome.png"))); // NOI18N

        gameStatusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gameStatusText.setText("<html><font color=\"#F06D66\" size=\"6\" face=\"Gill Sans\">Catch Tiger Chess</font></html>");
        gameStatusText.setToolTipText("");
        gameStatusText.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gameStatusText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        regretButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageLibary/regret.png"))); // NOI18N
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

        gameControllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageLibary/start.png"))); // NOI18N
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

        voiceControlButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageLibary/volume.png"))); // NOI18N
        voiceControlButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        voiceControlButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        voiceControlButton.setPreferredSize(new java.awt.Dimension(48, 48));
        voiceControlButton.setRequestFocusEnabled(false);
        voiceControlButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imageLibary/mute.png"))); // NOI18N
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
                                .addComponent(gameControllButton)
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                                .addComponent(timeNowText, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))))
                    .addGroup(gameStatusLayout.createSequentialGroup()
                        .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gameStatusText)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameStatusLayout.createSequentialGroup()
                                .addComponent(voiceControlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(regretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(timeLeftBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(gameStatusLayout.createSequentialGroup()
                                .addComponent(gameStatusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        gameStatusLayout.setVerticalGroup(
            gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameStatusLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(gameStatusImage, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(gameStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(timeLeftBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeNowText)
                .addGap(75, 75, 75)
                .addComponent(gameControllButton)
                .addGap(70, 70, 70)
                .addGroup(gameStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(regretButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(voiceControlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(0, 1, Short.MAX_VALUE))
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
        String inputValue = (String)JOptionPane.showInputDialog(null,"Enter the game time(minutes) to continue","Please",JOptionPane.QUESTION_MESSAGE,PeaceIcon,null,""); 
        Integer time;
        if(inputValue == null){
            return;          
        }else if(inputValue.isEmpty() || !isNumeric(inputValue) || (inputValue.length() > 8)){
            time = -1;
        }else{
            time = Integer.parseInt(inputValue);
        }
        if (gameIsStart) {
            timer.stop();
            gameBoard.remove(chessCanvas);
            timeLeftBar.setValue(0);
            PlayerNow = "Tiger";
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
        if(isVoiceOn) music.playBgMusic();
    }//GEN-LAST:event_gameControllButtonMouseClicked

    
    private void regretButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regretButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_regretButtonActionPerformed

    private void voiceControlButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voiceControlButtonMouseClicked
        if(gameIsStart){
            if(isVoiceOn){
                isVoiceOn = Boolean.FALSE;
                music.stopBgMusiic();
            }else{
                isVoiceOn = Boolean.TRUE;
                music.playBgMusic();
            }
        }else{
            if(isVoiceOn){
                isVoiceOn = Boolean.FALSE;
                music.stopStartMusiic();
            }else{
                isVoiceOn = Boolean.TRUE;
                music.playStartMusic();
            }
        }
        
    }//GEN-LAST:event_voiceControlButtonMouseClicked
    
    private void regretButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regretButtonMouseClicked
        if (regretButton.isEnabled() == false) return;
        regretButton.setEnabled(false);
        backOneStep();
        backOneStep();
        chessCanvas.repaint();
    }//GEN-LAST:event_regretButtonMouseClicked
    
    private void backOneStep(){
        RegretData del = Config.regretStack.pop();
        chessBoarder.delPiece(del.des_x, del.des_y);
        chessBoarder.addPiece(del.src_x, del.src_y,del.pieceId);
        if (del.pieceId == 0) {
            ChessBoarder.TigerLocationX = del.src_x;
            ChessBoarder.TigerLocationY = del.src_y;
        }
        chessBoarder.setPoint(null);
        if(del.degree_0 == true){
            chessBoarder.addPiece(del.des_x-1, del.des_y,1);
            chessBoarder.addPiece(del.des_x+1, del.des_y,1);
            ChessBoarder.dogCount += 2; 
        }
        if(del.degree_90 == true){
            chessBoarder.addPiece(del.des_x, del.des_y-1,1);
            chessBoarder.addPiece(del.des_x, del.des_y+1,1);
            ChessBoarder.dogCount += 2; 
        }
        if(del.degree_135 == true){
            chessBoarder.addPiece(del.des_x-1, del.des_y-1,1);
            chessBoarder.addPiece(del.des_x+1, del.des_y+1,1);
            ChessBoarder.dogCount += 2; 
        }
        if(del.degree_45 == true){
            chessBoarder.addPiece(del.des_x-1, del.des_y+1,1);
            chessBoarder.addPiece(del.des_x+1, del.des_y-1,1);
            ChessBoarder.dogCount += 2; 
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
