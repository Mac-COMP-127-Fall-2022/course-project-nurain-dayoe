package MazeGame;
import java.awt.Color;
import java.io.File;

import java.util.Iterator;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.*;
import edu.macalester.graphics.ui.*;


public class MazeGame {
    
    private CanvasWindow canvas ;
    private Image backGround;
    private Image zelda;
    private GraphicsGroup layer1;
    private Physics physics = new Physics();
    private Clip backGroundMusic;
    private int brickGroupPatternID =0;
    private String musicFilePath = "res/music.wav";

    public MazeGame() { 
        playBgMusic(musicFilePath);
        resetGame();
        canvas.onKeyDown((key)->{
            physics.move(canvas,backGround,zelda,key.getKey(),layer1);
        });
        canvas.onDrag((m)->{
            physics.scrollBackground(backGround, m, canvas,zelda,layer1);
        });
    }
    public static void main(String[] args){
        new MazeGame();
    }
    protected void resetGame(){
        canvas = new CanvasWindow("Macverse", 800, 400);
        backGround = new Image("map.bmp");
        zelda = new Image("front.bmp");
        zelda.setScale(0.35);
        zelda.setCenter(canvas.getWidth()/2,canvas.getHeight()/2);
        
        canvas.add(backGround);
        canvas.add(zelda);
        backGround.setPosition(0,0);
        layer1 = new GraphicsGroup(0,0);
        layer1.setPosition(-20,0);
        Rectangle r1 = new Rectangle(0, 0, 188, 176);
        
        
        Rectangle r2 = new Rectangle(207,0,85,176);
        Rectangle r3 = new Rectangle(0,193,188,188);

        r1.setStrokeWidth(0.0001);
        r2.setStrokeWidth(0.0001);
        r3.setStrokeWidth(0.0001);
        layer1.add(r1);
        layer1.add(r2);
        layer1.add(r3);
        canvas.add(layer1);
        layer1.setPosition(0,0);

    }   
    public void playBgMusic(String filePath){//Created with the help of external source (external resource 1. in academic integrity file)
        File musicFile = new File(filePath);
    
        try{
            backGroundMusic = AudioSystem.getClip();
            backGroundMusic.open(AudioSystem.getAudioInputStream(musicFile));
            backGroundMusic.loop(10000);
        } catch (Exception e){
            //Error
        }

    
    }
}
