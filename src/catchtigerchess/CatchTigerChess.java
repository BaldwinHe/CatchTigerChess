/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtigerchess;

import gameWindow.ChessWindow;
import java.awt.EventQueue;

/**
 * The entrance to the game
 * @author 何炎柏
 */
public class CatchTigerChess {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ChessWindow Chess = new ChessWindow(-1);
                    Chess.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
