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
    private static int TigerLocationX;//wyw record the location of tiger
    private static int TigerLocationY;
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
        if(map[des.y][des.x][src.y][src.x] == true)//wyw for two-way map
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
        if(map[des_y][des_x][src_y][src_x] == true)//wyw for two way map
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
        this.TigerLocationX=2;//wyw init the lcation of tiger
        this.TigerLocationY=4;
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
        chessPieces[2][1] = new ChessPieces(2);
        chessPieces[2][2] = new ChessPieces(3);
        chessPieces[2][3] = new ChessPieces(4);
        chessPieces[2][4] = new ChessPieces(5);
        chessPieces[3][0] = new ChessPieces(6);
        chessPieces[3][4] = new ChessPieces(7);
        chessPieces[4][0] = new ChessPieces(8);
        chessPieces[4][4] = new ChessPieces(9);
        chessPieces[5][0] = new ChessPieces(10);
        chessPieces[5][4] = new ChessPieces(11);
        chessPieces[6][0] = new ChessPieces(12);
        chessPieces[6][1] = new ChessPieces(13);
        chessPieces[6][2] = new ChessPieces(14);
        chessPieces[6][3] = new ChessPieces(15);
        chessPieces[6][4] = new ChessPieces(16);
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
                    chessPieces[des.y][des.x] = chessPieces[src.y][src.x];
                    chessPieces[src.y][src.x] = null;
                    if(chessPieces[des.y][des.x].id==0){//wyw if the moving chesspiece is tiger
                        this.TigerLocationX=des.x;//wyw record the location of tiger at present
                        this.TigerLocationY=des.y;
                    }
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }
    public int JudgeWin(){//wyw judge the win 1-tiger win;2-dog win;0-no result
        if(this.dogCount<=2)//wyw if the dog number is below 2,tiger win
            return 1;
        if(this.TigerLocationX==2&&this.TigerLocationY==0)//wyw if the tiger is in trap,dog win
            return 2;
        //judge if there is a way for tiger to go
        if(this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX+1, TigerLocationY+1)&&this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX-1, TigerLocationY-1)&&TigerLocationX+1<=4&&TigerLocationY+1<=4&&this.chessPieces[TigerLocationY+1][TigerLocationX+1]!=null&&TigerLocationX-1>=0&&TigerLocationY-1>=0&&this.chessPieces[TigerLocationY-1][TigerLocationX-1]!=null)
            return 2;
        if(this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX+1, TigerLocationY-1)&&this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX-1, TigerLocationY+1)&&TigerLocationX+1<=4&&TigerLocationY-1>=0&&this.chessPieces[TigerLocationY-1][TigerLocationX+1]!=null&&TigerLocationX-1>=0&&TigerLocationY+1<=4&&this.chessPieces[TigerLocationY+1][TigerLocationX-1]!=null)
            return 2;
        if(this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX+1, TigerLocationY)&&this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX-1, TigerLocationY)&&TigerLocationX+1<=4&&this.chessPieces[TigerLocationY+1][TigerLocationX]!=null&&TigerLocationX-1>=0&&this.chessPieces[TigerLocationY][TigerLocationX-1]!=null)
            return 2;
        if(this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX, TigerLocationY+1)&&this.hasRoad(TigerLocationX,TigerLocationY,TigerLocationX, TigerLocationY-1)&&TigerLocationY+1<=4&&this.chessPieces[TigerLocationY+1][TigerLocationX]!=null&&TigerLocationY-1>=0&&this.chessPieces[TigerLocationY-1][TigerLocationX]!=null)
            return 2;
        return 0;
    }
    public boolean hasPiece(int x, int y){
        if(chessPieces[y][x] != null){
            return true;
        }else return false;
    }
    public void eatPiece(int x, int y){
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
