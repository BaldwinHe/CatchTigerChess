/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Play game music
 * @author 杨焕煜
 */
public class PlayMusic {
    Clip start, bg , move_dog, move_tiger, eat ,won_tiger, won_dog,time_out;
    URL cb;

    /**
     * Initialize music
     */
    public void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        try {   
            start = AudioSystem.getClip();
            bg = AudioSystem.getClip();
            move_dog = AudioSystem.getClip();
            move_tiger = AudioSystem.getClip();
            eat = AudioSystem.getClip();
            won_tiger = AudioSystem.getClip();
            won_dog = AudioSystem.getClip();
            time_out = AudioSystem.getClip();
                
            InputStream is = getClass().getResourceAsStream(Config.BackGroundMusic);
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            start.open(ais);
            
            is = getClass().getResourceAsStream(Config.PlayingMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            bg.open(ais);
            
            is = getClass().getResourceAsStream(Config.DogMoveMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            move_dog.open(ais);
            
            is = getClass().getResourceAsStream(Config.TigerMoveMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            move_tiger.open(ais);
            
            is = getClass().getResourceAsStream(Config.EatMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            eat.open(ais);
            
            is = getClass().getResourceAsStream(Config.TigerWonMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            won_tiger.open(ais);
            
            is = getClass().getResourceAsStream(Config.DogWonMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            won_dog.open(ais);
            
            is = getClass().getResourceAsStream(Config.TimeOutMusic);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));    
            time_out.open(ais);
            } catch (MalformedURLException e) {
                    e.printStackTrace();
            }
    }
    
    /**
     * Play start music
     */
    public void playStartMusic(){
        bg.stop();
        bg.setFramePosition(0);
        start.setFramePosition(0);
        start.loop(Clip.LOOP_CONTINUOUSLY);
    }
    /**
     * Play timeout music
     */
    public void playTimeOutMusic(){
        stopBgMusiic();
        time_out.loop(Clip.LOOP_CONTINUOUSLY);
    }
    /**
     * Stop start music
     */
    public void stopStartMusiic(){
        start.stop();
        start.setFramePosition(0);
    }
    /**
     * Play grondgroud music
     */
    public void playBgMusic(){
        won_dog.stop();
        start.stop();
        time_out.stop();
        won_tiger.stop();
        bg.loop(Clip.LOOP_CONTINUOUSLY);
    }
    /**
     * Stop ground music
     */
    public void stopBgMusiic(){
        bg.stop();
        bg.setFramePosition(0);
    }
    /**
     * Play dog move music
     */
    public void playMoveDogMusic(){
        move_dog.start();
        move_dog.setFramePosition(0);
    }
    /**
     * Play tiger move music
     */
    public void playMoveTigerMusic(){
        move_tiger.start();
        move_tiger.setFramePosition(0);
    }
    /**
     * Play eat music
     */
    public void playEatMusic(){
        eat.start();
        eat.setFramePosition(0);
    }
    /**
     * Play dog won music
     */
    public void playWonTigerMusic(){
        stopBgMusiic();
        won_tiger.loop(Clip.LOOP_CONTINUOUSLY);
    }
    /**
     * Play dog won music
     */
    public void playWonDogMusic(){
        stopBgMusiic();
        won_dog.loop(Clip.LOOP_CONTINUOUSLY);
    }
    /**
    * Stop timeout music
    */
    public void stopTimeOutMusic(){
        time_out.stop();
    }
    /**
    * Stop tiger won music
    */
    public void stopWonTigerMusic(){
        won_tiger.stop();
    }
    /**
    * Stop dog won music
    */
    public void stopWonDogMusic(){
        won_dog.stop();
    }
}
