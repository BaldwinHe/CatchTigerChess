/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtigerchess;
import config.Config;
import java.awt.Point;
import java.lang.Math;
import catchtigerchess.ChessWindow;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

/**
 *
 * @author heyanbai
 */
public class ChessBoarder {
    private static ChessPieces[][] chessPieces;
    private static boolean[][][][] map;
    private static int dogCount = 16;
        
    private Point selectPoint;
    private boolean isRealDes(int x,int y){
        if(x>=0&&x<=4&&y>=2&&y<=6) return true;
        else if(y == 0 && x == 2) return true;
        else if(y == 1 && x >= 1 && x <= 3) return true;
        else return false;
    }
    
    private boolean isInDistance(Point src, Point des){
        if(map[src.y][src.x][des.y][des.x] == true)
            return true;
        else
            return false;
//        int dx = Math.abs(src.x - des.x);
//        int dy = Math.abs(src.y - des.y);
//        if(dx == 1 && dy == 1) return true;
//        else if(dx == 1 && dy == 0) return true;
//        else if(dx == 0 && dy == 1) return true;
//        else return false;

    }
    public boolean hasRoad(int src_x, int src_y,int des_x ,int des_y){
        if(map[src_y][src_x][des_y][des_x] == true)
            return true;
        else return false;
    }
    public Point getPoint(){
        return selectPoint;
    }
    public void setPoint(Point p){
        selectPoint = p;
    }
    public ChessBoarder(){
        chessPieces = new ChessPieces[7][5];
        selectPoint = new Point();
        selectPoint = null;
        map = new boolean[7][5][7][5];
        Config.init_map();
        map = Config.map;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                chessPieces[i][j] = null;
            }
        }
        chessPieces[4][2] = new ChessPieces(0);
        chessPieces[2][0] = new ChessPieces(1);
        chessPieces[2][1] = new ChessPieces(1);
        chessPieces[2][2] = new ChessPieces(1);
        chessPieces[2][3] = new ChessPieces(1);
        chessPieces[2][4] = new ChessPieces(1);
        chessPieces[3][0] = new ChessPieces(1);
        chessPieces[3][4] = new ChessPieces(1);
        chessPieces[4][0] = new ChessPieces(1);
        chessPieces[4][4] = new ChessPieces(1);
        chessPieces[5][0] = new ChessPieces(1);
        chessPieces[5][4] = new ChessPieces(1);
        chessPieces[6][0] = new ChessPieces(1);
        chessPieces[6][1] = new ChessPieces(1);
        chessPieces[6][2] = new ChessPieces(1);
        chessPieces[6][3] = new ChessPieces(1);
        chessPieces[6][4] = new ChessPieces(1);
    }
    /**
    * Move Chess Piece
    * @param src Source Point
    * @param des Destination Point
    * @return Returns true if successful, otherwise returns false
    */
    public boolean pieceMove(Point src, Point des){
        if (chessPieces[src.y][src.x] == null){
            return false;
        }else{
            if(isRealDes(des.x,des.y)){
                if(chessPieces[des.y][des.x] != null) return false;
                if (isInDistance(src,des)){
                    Config.regretTemp.pieceId = chessPieces[src.y][src.x].id;
                    chessPieces[des.y][des.x] = chessPieces[src.y][src.x];
                    chessPieces[src.y][src.x] = null;
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }
    public boolean addPiece(int x, int y, int i){
        if(chessPieces[y][x] != null)
            return false;
        else {
            chessPieces[y][x] = new ChessPieces(i);
            return true;
        }
            
    }
    public boolean hasPiece(int x, int y){
        if(chessPieces[y][x] != null){
            return true;
        }else return false;
    }
    public void delPiece(int x, int y){
        if (chessPieces[y][x] != null){
            chessPieces[y][x] = null;
        }
    }
    public void killDog(int deadNum){
        dogCount = dogCount - deadNum ;
    }
    public int getDogCount(){
        return dogCount;
    }
    public ChessPieces[][] getChessPieces(){
        return chessPieces.clone();
    }
}
