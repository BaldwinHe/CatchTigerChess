/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catchtigerchess;

import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author 杨焕煜
 */
public class playMusic {
    AudioClip start, bg , move_dog, move_tiger, eat, regret;
    URL cb;
    public void init(){
        try {
                File f_start = new File("src/music/background.wav");//刚刚打开的时候还没点开始游戏的时候放的音乐
                File f_bg = new File("src/music/playing.wav"); //游戏开始后的背景音乐
                File f_move_dog = new File("src/music/dog.wav"); 
                File f_move_tiger = new File("src/music/tiger.wav"); 
                File f_eat = new File("src/music/eat.wav"); 
                File f_regret = new File("src/music/.wav"); 
                
                cb = f_bg.toURL();
                bg = Applet.newAudioClip(cb);
                cb = f_move_dog.toURL();
                move_dog = Applet.newAudioClip(cb);
                cb = f_move_tiger.toURL();
                move_tiger = Applet.newAudioClip(cb);
                cb = f_eat.toURL();
                eat = Applet.newAudioClip(cb);
                cb = f_start.toURL();
                start = Applet.newAudioClip(cb);
                cb = f_regret.toURL();
                regret = Applet.newAudioClip(cb);
            } catch (MalformedURLException e) {
                    e.printStackTrace();
            }
    }
    
    public void playStartMusic(){
        start.loop();
    }
    public void stopStartMusiic(){
        start.stop();
    }
    public void playBgMusic(){//背景音乐播放
        bg.loop();
    }
    public void stopBgMusiic(){
        bg.stop();
    }
    public void playMoveDogMusic(){
        move_dog.play();
    }
    public void playMoveTigerMusic(){
        move_tiger.play();
    }
    public void playEatMusic(){
        eat.play();
    }
    public void playRegratMusic(){
        regret.play();
    }
           
}
