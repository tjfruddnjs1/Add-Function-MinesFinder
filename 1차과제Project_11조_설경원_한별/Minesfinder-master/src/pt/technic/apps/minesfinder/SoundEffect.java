package pt.technic.apps.minesfinder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;

//import pt.technic.apps.minesfinder.Music.AL;

public class SoundEffect {
	
    public static void effectplay(String file) 
    {
       try {
          File fp = new File(file);
          String basepath = fp.getAbsolutePath();
          File music = new File(basepath);
    	  AudioInputStream ais = AudioSystem.getAudioInputStream(music);
          Clip clip = AudioSystem.getClip();
          clip.open(ais);
          clip.start();
          
       }
       catch(Exception e) {
          e.printStackTrace();
       }
    }
    
    public static void effectplay_1(String file) 
    {
       try {
    	   File fp = new File(file);
           String basepath = fp.getAbsolutePath();
           File music = new File(basepath);
     	  AudioInputStream ais = AudioSystem.getAudioInputStream(music);
          Clip clip = AudioSystem.getClip();
          clip.open(ais);
          clip.start();
          clip.loop(Clip.LOOP_CONTINUOUSLY);
       }
       catch(Exception e) {
          e.printStackTrace();
       }
    }
    
    
}


