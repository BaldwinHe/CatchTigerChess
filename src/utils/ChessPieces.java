/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ChessPieces
 * @author 何炎柏
 */
public class ChessPieces {
    public String name;
    public Integer id;
    public Image Icon;
    
    public ChessPieces(int id){
        this.id = id;
        String fileName = null;
        if(this.id == 0){
            this.name = "Tiger";
            fileName = "tiger.png";
        }else{
            this.name = "Dog";
            fileName = "dog.png";
        }
        
        this.Icon = Toolkit.getDefaultToolkit().getImage("src/imageLibary/" + fileName);
    }
}
