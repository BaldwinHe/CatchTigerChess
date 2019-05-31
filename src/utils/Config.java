/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
/**
 * Application configuration
 * @author 何炎柏 | 杨焕煜
 */
public class Config {
    final static public int startX = 18;
    final static public int startY = 18;
    final static public int distPoint2Point = 100;
    final static public String TigerMoveIcon = "/imageLibary/TIGERMOVE.png";
    final static public String DogMoveIcon = "/imageLibary/DOGMOVE.png";
    final static public String restartBtnIcon = "/imageLibary/restart.png";
    final static public String GameOverIcon = "/imageLibary/GAMEOVER.png";
    final static public String PeaceIcon = "/imageLibary/peace.png";
    final static public String WinIcon = "/imageLibary/WIN.png";
    final static public boolean[][][][] map;
    
    static {
        map = new boolean [7][5][7][5];
        for(int i=0; i<7; i++){
           for(int j=0; j<5; j++){
               for(int k=0; k<7; k++){
                   for(int l=0; l<5; l++)
                       map[i][j][k][l] = false;
               }
           }
       }
       map[0][2][1][1] = true;map[0][2][1][2] = true;
       map[0][2][1][3] = true;map[1][1][0][1] = true;
       map[1][1][1][2] = true;map[1][1][2][2] = true;
       map[1][2][0][2] = true;map[1][2][1][1] = true;
       map[1][2][1][3] = true;map[1][2][2][2] = true;
       map[1][3][0][2] = true;map[1][3][2][2] = true;
       map[1][3][1][2] = true;map[2][0][2][1] = true;
       map[2][0][3][0] = true;map[2][0][3][1] = true;
       map[2][1][2][2] = true;map[2][1][2][0] = true;
       map[2][1][3][1] = true;map[2][2][2][1] = true;
       map[2][2][2][3] = true;map[2][2][1][2] = true;
       map[2][2][3][2] = true;map[2][2][1][1] = true;
       map[2][2][1][3] = true;map[2][2][3][1] = true;
       map[2][2][2][3] = true;map[2][3][2][2] = true;
       map[2][3][2][4] = true;map[2][3][3][3] = true;
       map[2][4][2][3] = true;map[2][4][3][4] = true;
       map[2][4][3][3] = true;map[3][0][2][0] = true;
       map[3][0][4][0] = true;map[3][0][3][1] = true;
       map[3][1][3][0] = true;map[3][1][3][2] = true;
       map[3][1][2][1] = true;map[3][1][4][1] = true;
       map[3][1][2][0] = true;map[3][1][2][2] = true;
       map[3][1][4][0] = true;map[3][1][4][2] = true;
       map[3][2][3][3] = true;map[3][2][3][1] = true;
       map[3][2][2][2] = true;map[3][2][4][2] = true;
       map[3][3][2][3] = true;map[3][3][4][3] = true;
       map[3][3][3][2] = true;map[3][3][2][2] = true;
       map[3][3][4][4] = true;map[3][3][2][4] = true;
       map[3][3][4][2] = true;map[3][3][3][4] = true;
       map[3][4][3][3] = true;map[3][4][2][4] = true;
       map[3][4][4][4] = true;map[4][0][3][0] = true;
       map[4][0][5][0] = true;map[4][0][4][1] = true;
       map[4][0][3][1] = true;map[4][0][5][1] = true;
       map[4][1][3][1] = true;map[4][1][5][1] = true;
       map[4][1][4][0] = true;map[4][1][4][2] = true;
       map[4][2][3][2] = true;map[4][2][5][2] = true;
       map[4][2][4][1] = true;map[4][2][4][3] = true;
       map[4][2][3][1] = true;map[4][2][3][3] = true;
       map[4][2][5][1] = true;map[4][2][5][3] = true;
       map[4][3][3][3] = true;map[4][3][5][3] = true;
       map[4][3][4][2] = true;map[4][3][4][4] = true;
       map[4][4][3][4] = true;map[4][4][5][4] = true;
       map[4][4][4][3] = true;map[4][4][3][3] = true;
       map[4][4][5][3] = true;map[5][0][4][0] = true;
       map[5][0][6][0] = true;map[5][0][5][1] = true;
       map[5][1][5][0] = true;map[5][1][5][2] = true;
       map[5][1][4][1] = true;map[5][1][6][1] = true;
       map[5][1][4][0] = true;map[5][1][4][2] = true;
       map[5][1][6][0] = true;map[5][1][6][2] = true;
       map[5][2][5][1] = true;map[5][2][5][3] = true;
       map[5][2][4][2] = true;map[5][2][6][2] = true;
       map[5][3][5][4] = true;map[5][3][5][2] = true;
       map[5][3][4][3] = true;map[5][3][6][3] = true;
       map[5][3][6][2] = true;map[5][3][6][4] = true;
       map[5][3][4][2] = true;map[5][3][4][4] = true;
       map[5][4][4][4] = true;map[5][4][6][4] = true;
       map[5][4][5][3] = true;map[6][0][5][0] = true;
       map[6][0][6][1] = true;map[6][0][5][1] = true;
       map[6][1][6][0] = true;map[6][1][6][2] = true;
       map[6][1][5][1] = true;map[6][2][6][1] = true;
       map[6][2][6][3] = true;map[6][2][5][2] = true;
       map[6][2][5][1] = true;map[6][2][5][3] = true;
       map[6][3][5][3] = true;map[6][3][6][2] = true;
       map[6][3][6][4] = true;map[6][4][6][3] = true;
       map[6][4][5][4] = true;map[6][4][5][3] = true;
    }
}
