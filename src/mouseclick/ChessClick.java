/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouseclick;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import config.Config;
import catchtigerchess.ChessWindow;
import catchtigerchess.ChessPieces;
import catchtigerchess.ChessBoardCanvas;
import java.awt.Point;
import catchtigerchess.regretData;
/**
 *
 * @author heyanbai
 */
public class ChessClick extends MouseAdapter{
    @Override
    public void mouseClicked(MouseEvent arg){
        int x = arg.getX();
        int y = arg.getY();
        x = (x - Config.startX)/Config.distPoint2Point; //get x coordinate
        y = (y - Config.startY)/Config.distPoint2Point; //get y coordinate
        if(x < 0 || x > 4 || y < 0 || y > 6){ // to see if the (x, y) are in range
            return;
        }else{
            ChessPieces[][] pieces = ChessWindow.getChessPieces();
            if (pieces[y][x] != null && pieces[y][x].name.toString().equals(ChessWindow.getPlayer().toString())) { 
                // select a piece and the type of piece is the player now.
                Point p1 = ChessWindow.chessBoarder.getPoint();
                if (p1 != null) {
                    if(p1.x == x && p1.y == y){
                        // deselect.
                        ChessWindow.chessBoarder.setPoint(null);
                        refresh(arg);
                    }
                    else {
                        // select a new point.
                        ChessWindow.chessBoarder.setPoint(new Point(x, y));
                        refresh(arg);
                        return;
                    }
                }
                else {
                    // select a new point.
                    ChessWindow.chessBoarder.setPoint(new Point(x, y));
                    refresh(arg);
                    return;
                }
            }
            if (ChessWindow.chessBoarder.getPoint() != null && pieces[y][x] == null) {
                // have select a piece and the (x, y) is not a piece. 
                Point p1 = ChessWindow.chessBoarder.getPoint();
                regretData regretTemp = new regretData();
                if (ChessWindow.chessBoarder.pieceMove(p1, new Point(x, y), regretTemp) == true) { 
                    regretTemp.pieceId = pieces[y][x].id;
                    regretTemp.src_x = p1.x;
                    regretTemp.src_y = p1.y;
                    regretTemp.des_x = x;
                    regretTemp.des_y = y;
                    ChessWindow.chessBoarder.setPoint(null);
                    boolean flagEat = true;
                    if(ChessWindow.eatChess(x, y, regretTemp) == true){
                        flagEat = false;
                        if(ChessWindow.isVoiceON() == true)
                            ChessWindow.music.playEatMusic();
                        System.out.println("haveEat");
                    }
                    if(ChessWindow.isVoiceON() == true && flagEat){
                        if(pieces[y][x].id == 0)
                            ChessWindow.music.playMoveTigerMusic();
                        else
                            ChessWindow.music.playMoveDogMusic();
                    }
                    Config.regretStack.push(regretTemp);
                    refresh(arg);
                    swapPlayer();
                    int status = ChessWindow.chessBoarder.JudgeWin();
                    if (status == 2){
                        ChessWindow.setGameStatus("DOGWIN");
                    }else if (status == 1){  
                        ChessWindow.setGameStatus("TIGERWIN");
                    }
                }
            }
        }
    }
    
    /**
    * Refresh ChessBoard
    * @param arg MouseEvent Argument
    */
    private void refresh(MouseEvent arg){
        ((ChessBoardCanvas) arg.getSource()).repaint();
        int height = ((ChessBoardCanvas) arg.getSource()).getHeight();
        int width = ((ChessBoardCanvas) arg.getSource()).getWidth();
        ((ChessBoardCanvas) arg.getSource()).paintImmediately(0, 0, width, height);
        System.out.println("Repaint Down");
    }
    static public void swapPlayer(){
        ChessWindow.setPlayer();
 
    }
}
