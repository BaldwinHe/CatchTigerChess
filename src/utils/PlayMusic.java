/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Play game music
 * @author 杨焕煜
 */
public class PlayMusic {
    AudioClip start, bg , move_dog, move_tiger, eat, regret,won_tiger, won_dog,time_out;
    URL cb;

    /**
     * Initialize music
     */
    public void init(){
        try {
                File f_start = new File("src/musicLibary/background.wav");//刚刚打开的时候还没点开始游戏的时候放的音乐
                File f_bg = new File("src/musicLibary/playing.wav"); //游戏开始后的背景音乐
                File f_move_dog = new File("src/musicLibary/dog.wav"); 
                File f_move_tiger = new File("src/musicLibary/tiger.wav"); 
                File f_eat = new File("src/musicLibary/eat.wav"); 
                File f_won_tiger = new File("src/musicLibary/tigerwon.wav");
                File f_won_dog = new File("src/musicLibary/dogwon.wav");
                File f_time = new File("src/musicLibary/timeout.wav");
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
                cb = f_won_dog.toURL();
                won_dog = Applet.newAudioClip(cb);
                cb= f_won_tiger.toURL();
                won_tiger = Applet.newAudioClip(cb);
                cb= f_time.toURL();
                time_out = Applet.newAudioClip(cb);
                
            } catch (MalformedURLException e) {
                    e.printStackTrace();
            }
    }
    
    /**
     * Play start music
     */
    public void playStartMusic(){
        bg.stop();
        start.loop();
    }
    /**
     * Play timeout music
     */
    public void playTimeOutMusic(){
        stopBgMusiic();
        time_out.loop();
    }
    /**
     * Stop start music
     */
    public void stopStartMusiic(){
        start.stop();
    }
    /**
     * Play grondgroud music
     */
    public void playBgMusic(){//背景音乐播放
        won_dog.stop();
        start.stop();
        time_out.stop();
        won_tiger.stop();
        bg.loop();
    }
    /**
     * Stop ground music
     */
    public void stopBgMusiic(){
        bg.stop();
    }
    /**
     * Play dog move music
     */
    public void playMoveDogMusic(){
        move_dog.play();
    }
    /**
     * Play tiger move music
     */
    public void playMoveTigerMusic(){
        move_tiger.play();
    }
    /**
     * Play eat music
     */
    public void playEatMusic(){
        eat.play();
    }
    /**
     * Play dog won music
     */
    public void playWonTigerMusic(){
        stopBgMusiic();
        won_tiger.loop();
    }
    /**
     * Play dog won music
     */
    public void playWonDogMusic(){
        stopBgMusiic();
        won_dog.loop();
    }
}
