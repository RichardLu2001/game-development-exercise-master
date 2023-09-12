package edu.nyu.cs;

import processing.core.PApplet;
import processing.core.PImage;

public class Mole {
    private Game app; 
    private PImage img; 
    private int x; 
    private int y; 
    private final int MAX_MOVEMENT = 20;


    public Mole(Game app, String imgFilePath, int x, int y) {
    this.app = app; 
    this.img = app.loadImage(imgFilePath);
    this.x = x;
    this.y = y;
    }

    public void draw() {
        this.app.imageMode(PApplet.CENTER); 
        this.app.image(this.img, this.x, this.y);
    }

    public void moveRandomly() {
        int dx = (int) (Math.random() * this.MAX_MOVEMENT*2 - this.MAX_MOVEMENT);
        int dy = (int) (Math.random() * this.MAX_MOVEMENT*2 - this.MAX_MOVEMENT);
        this.x += dx;
        this.y += dy;
    }

    public boolean overlaps(int x, int y, int fudgeFactor) {
        int l = this.x - this.img.width/2 - fudgeFactor; 
        int r = this.x + this.img.width/2 + fudgeFactor; 
        int t = this.y - this.img.height/2 - fudgeFactor; 
        int b = this.y + this.img.height/2 + fudgeFactor; 
        return (x > l && x < r && y > t && y < b);
    }
}