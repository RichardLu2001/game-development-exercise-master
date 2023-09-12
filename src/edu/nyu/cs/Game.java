package edu.nyu.cs;

import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.SystemUtils;

import processing.core.*; 
import processing.sound.*; 

/**
 * This is a slightly different version of the classic game Wack-A-Mole. 
 * 20 moles and 10 mines are spawned (nearly) simultaneously at the start of the game.
 * Hitting a mole with the hammer causes it to disappear, while hitting a mine causes you to fail and the game exits.
 * A timer will keep track of the time you used to hit all the moles without setting off a mine, the shorter the better.
 * Compete for the title of "Hammer of Precision".
 * 
 * @author Richard Lu
 * @version 0.1
 */
public class Game extends PApplet {

    private SoundFile soundStartup; 
    private SoundFile soundSuccess; 
    private SoundFile soundFailure;
    private PImage imghammer; 
    private ArrayList<Mole> moles;
    private ArrayList<Mine> mines; 
    private final int NUM_MOLES = 20; 
    private final int NUM_MINES = 10; 
    private long begin;
    private long end;
    
    public void setup() {
        this.cursor(PApplet.CROSS);
        String cwd = Paths.get("").toAbsolutePath().toString();
        String path = Paths.get(cwd, "images", "hammer.png").toString();
        this.imghammer = loadImage(path);
        this.cursor(this.imghammer);

        this.begin = System.currentTimeMillis();
    
        path = Paths.get(cwd, "sounds", "vibraphon.mp3").toString(); 
        this.soundStartup = new SoundFile(this, path);
        this.soundStartup.play();

        path = Paths.get(cwd, "sounds", "success.mp3").toString();
        this.soundSuccess = new SoundFile(this,path);

        path = Paths.get(cwd, "sounds", "failure.mp3").toString();
        this.soundFailure = new SoundFile(this,path);

        this.ellipseMode(PApplet.CENTER); 
        this.imageMode(PApplet.CENTER); 

        moles = new ArrayList<Mole>();
        for (int i=0; i<this.NUM_MOLES; i++) {
  		    path = Paths.get(cwd, "images", "mole.jpg").toString(); 
            Mole mole = new Mole(this, path, this.width/2, this.height/2);
            this.moles.add(mole);
        }

        mines = new ArrayList<Mine>();
        for (int i=0; i<this.NUM_MINES; i++) {
  		    path = Paths.get(cwd, "images", "mine.jpg").toString(); 
            Mine mine = new Mine(this, path, this.width/2, this.height/2);
            this.mines.add(mine);
        }
	}

    public void draw() {
        this.background(0, 0, 0);
        for (int i=0; i<this.moles.size(); i++) {
            Mole mole = this.moles.get(i); 
            mole.moveRandomly(); 
            mole.draw(); 
        }

        for (int i=0; i<this.mines.size(); i++) {
            Mine mine = this.mines.get(i); 
            mine.moveRandomly(); 
            mine.draw();
        }
    }

    public void settings() {
		size(1200, 800); 
		System.out.println(String.format("Set up the window size: %d, %d.", width, height));    
    }

    public void mouseClicked() {
		System.out.println(String.format("Mouse clicked at: %d:%d.", this.mouseX, this.mouseY));
        for (int i=0; i<this.mines.size(); i++) {
            Mine mine = this.mines.get(i); 
            if (mine.overlaps(this.mouseX, this.mouseY, 10)) {
                this.soundFailure.play();
                System.out.println("You hit a mine. Try again.");
                System.exit(0);
            }
        }
    
        for (int i=0; i<this.moles.size(); i++) {
            Mole mole = this.moles.get(i); 
            if (mole.overlaps(this.mouseX, this.mouseY, 10)) {
                this.soundSuccess.play();
                this.moles.remove(mole);
                if (this.moles.size()==0) {
                    this.end=System.currentTimeMillis();
                    long duration=this.end-this.begin;
                    System.out.println("Congratulations!It took you "+duration+" milliseconds to finish the game.The Hammer of Precision is yours!");
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) {
        // make sure we're using Java 1.8
            System.out.printf("\n###  JDK IN USE ###\n- Version: %s\n- Location: %s\n### ^JDK IN USE ###\n\n", SystemUtils.JAVA_VERSION, SystemUtils.getJavaHome());
            boolean isGoodJDK = SystemUtils.IS_JAVA_1_8;
            if (!isGoodJDK) {
                System.out.printf("Fatal Error: YOU MUST USE JAVA 1.8, not %s!!!\n", SystemUtils.JAVA_VERSION);
            }
            else {
                PApplet.main("edu.nyu.cs.Game"); // do not modify this!
            }
    }
}