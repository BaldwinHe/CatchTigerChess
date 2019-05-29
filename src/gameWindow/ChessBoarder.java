/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameWindow;
import utils.ChessPieces;
import config.Config;
import java.awt.Point;

/**
 * Game interface platform
 * @author 何炎柏 | 汪有为
 */
public class ChessBoarder {
    private static ChessPieces[][] chessPieces;
    private static boolean[][][][] map;
    public static int dogCount = 16;
    public static int TigerLocationX;//wyw record the location of tiger
    public static int TigerLocationY;
    private Point selectPoint;
    private boolean isRealDes(int x,int y){
        if(x>=0&&x<=4&&y>=2&&y<=6) return true;
        else if(y == 0 && x == 2) return true;
        else if(y == 1 && x >= 1 && x <= 3) return true;
        else return false;
    }
    
    private boolean hasRoad(Point src, Point des){
        return map[src.y][src.x][des.y][des.x] || map[des.y][des.x][src.y][src.x];
    }

    /**
     * Check to see if there's a road between two points
     * @param src_x X coordinate of source point
     * @param src_y Y coordinate of source point
     * @param des_x X coordinate of destination point
     * @param des_y Y coordinate of destination point
     * @return true|false
     */
    
    public boolean hasRoad(int src_x, int src_y,int des_x ,int des_y){
        return map[src_y][src_x][des_y][des_x] || map[des_y][des_x][src_y][src_x];
    }

    /**
     * Get the current selection 
     * @return Point
     */
    public Point getPoint(){
        return selectPoint;
    }
    /**
     * Set the current selection 
     * @param p The point
     */
    public void setPoint(Point p){
        selectPoint = p;
    }
    public ChessBoarder(){
        ChessBoarder.TigerLocationX=2;//wyw init the lcation of tiger
        ChessBoarder.TigerLocationY=4;
        dogCount = 16;
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
    * Move chess piece
    * @param src Source point
    * @param des Destination point
    * @return Returns true if the move is successful, otherwise returns false
    */
    public boolean pieceMove(Point src, Point des){
        if (chessPieces[src.y][src.x] == null){
            return false;
        }else{
            if(isRealDes(des.x,des.y)){
                if(chessPieces[des.y][des.x] != null) return false;
                if (hasRoad(src,des)){
                    //regretTemp.pieceId = chessPieces[src.y][src.x].id;
                    chessPieces[des.y][des.x] = chessPieces[src.y][src.x];
                    chessPieces[src.y][src.x] = null;
                    if(chessPieces[des.y][des.x].id == 0){//wyw if the moving chesspiece is tiger
                        ChessBoarder.TigerLocationX=des.x;//wyw record the location of tiger at present
                        ChessBoarder.TigerLocationY=des.y;
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
    
    /**
     * Check to see if any players have won
     * @return Tiger wins back 1 and dogs win back 2, otherwise returns 0
     */
    public int judgeWin(){//wyw judge the win 1-tiger win;2-dog win;0-no result
        if(ChessBoarder.dogCount<=2)//wyw if the dog number is below 2,tiger win
            return 1;
        if(ChessBoarder.TigerLocationX==2&&ChessBoarder.TigerLocationY==0)//wyw if the tiger is in trap,dog win
            return 2;
        //judge if there is a way for tiger to go
        if(TigerLocationX+1<=4&&TigerLocationY+1<=6&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX+1, TigerLocationY+1)&&ChessBoarder.chessPieces[TigerLocationY+1][TigerLocationX+1]==null)
            return 0;
        if(TigerLocationX-1>=0&&TigerLocationY-1>=0&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX-1, TigerLocationY-1)&&ChessBoarder.chessPieces[TigerLocationY-1][TigerLocationX-1]==null)
            return 0;
        if(TigerLocationX+1<=4&&TigerLocationY-1>=0&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX+1, TigerLocationY-1)&&ChessBoarder.chessPieces[TigerLocationY-1][TigerLocationX+1]==null)
            return 0;
        if(TigerLocationX-1>=0&&TigerLocationY+1<=6&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX-1, TigerLocationY+1)&&ChessBoarder.chessPieces[TigerLocationY+1][TigerLocationX-1]==null)
            return 0;
        if(TigerLocationX+1<=4&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX+1, TigerLocationY)&&ChessBoarder.chessPieces[TigerLocationY][TigerLocationX+1]==null)
            return 0;
        if(TigerLocationX-1>=0&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX-1, TigerLocationY)&&ChessBoarder.chessPieces[TigerLocationY][TigerLocationX-1]==null)
            return 0;
        if(TigerLocationY+1<=6&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX, TigerLocationY+1)&&ChessBoarder.chessPieces[TigerLocationY+1][TigerLocationX]==null)
            return 0;
        if(TigerLocationY-1>=0&&this.hasRoad(TigerLocationX, TigerLocationY, TigerLocationX, TigerLocationY-1)&&ChessBoarder.chessPieces[TigerLocationY-1][TigerLocationX]==null)
            return 0;
        return 2;
    }
    
    /**
     * Add a piece at (x, y) with id i
     * @param x X coordinate of the target piece
     * @param y Y coordinate of the target piece
     * @param i Id of the target piece
     * @return Returns true if adding a piece successfully, otherwise returns false
     */
    public boolean addPiece(int x, int y, int i){
        if(chessPieces[y][x] != null)
            return false;
        else {
            chessPieces[y][x] = new ChessPieces(i);
            return true;
        }
    }
    
    /**
     * Check if (x ,y) has a piece
     * @param x X coordinate of target point
     * @param y Y coordinate of target point
     * @return Returns true if (x, y) has a point, otherwise returns false
     */
    public boolean hasPiece(int x, int y){
        return chessPieces[y][x] != null;
    }
    
    /**
     * Delete a piece at (x, y)
     * @param x X coordinate of target point
     * @param y Y coordinate of target point
     */
    public void delPiece(int x, int y){
        if (chessPieces[y][x] != null){
            chessPieces[y][x] = null;
        }
    }
    
    /**
     * Update the number of existing dogs
     * @param deadNum This time move down the number of dogs the tiger eats
     */
    public void killDog(int deadNum){
        dogCount = dogCount - deadNum ;
    }

    /**
     * Get the number of existing dogs
     * @return The number of existing dogs
     */
    public int getDogCount(){
        return dogCount;
    }
    
    /**
     * Get chessPieces now
     * @return chessPieces
     */
    public ChessPieces[][] getChessPieces(){
        return chessPieces.clone();
    }
}
