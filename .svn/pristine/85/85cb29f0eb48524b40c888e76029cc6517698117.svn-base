/*
 * TCSS 305
 * 
 * Assignment 6 - Tetris
 */
package view;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
 
/**
 * This is a main class of tetris to run the program.
 * @author Arjun Prajapati
 * @version December 7, 2017
 * 
 */
public class MusicPlayer {
    /**Theme sound.*/
    private String myTheme = "/sounds/theme.wav";
    
    /**Theme sound.*/
    private String myLevel = "/sounds/level.wav";
    
    /**Theme sound.*/
    private String myPause = "/sounds/pause.wav";
    
    /**Theme sound.*/
    private String myGameOver = "/sounds/gameover.wav";
    
    /**Clear sound.*/
    private String myClear = "/sounds/clear.wav";
    
    /**Audio Clip.*/
    private AudioClip myClick;

    /**
     * This method is to get the audio clip.
     * @return AudioClip
     */
    public AudioClip getAudioClip() {
        return myClick;
    }
     
    /**
     * This method is to playe the right music with the command.
     * @param theMusic , music file
     */
    public void play(final String theMusic) {
//        getAudioClip().stop();
        String sound = "";
        if ("myTheme".equals(theMusic)) {
            sound = myTheme;
        } else if ("myPause".equals(theMusic)) {
            sound = myPause;
        } else if ("myGameOver".equals(theMusic)) {
            sound = myGameOver;
        } else if ("myLevel".equals(theMusic)) {
            sound = myLevel;
        } else if ("myClear".equals(theMusic)) {
            sound = myClear;
        }
        
        final URL urlClick = MusicPlayer.class.getResource(sound);
        myClick = Applet.newAudioClip(urlClick);

        myClick.play();
        

    }

}
