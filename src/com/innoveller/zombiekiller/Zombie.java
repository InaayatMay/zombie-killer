package com.innoveller.zombiekiller;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Zombie {
    HelperService helperService = new HelperService();
    ArrayList<Image> zombieSprites = helperService.readSprites("zombie.png", 4);
    ArrayList<Image> heartSprites = helperService.readSprites("heart.png", 4);
    static int currentImageIndex = 0;
    private int centerX;
    private int centerY;
    private int radius;
    private Strategy strategy;

    public Zombie(Strategy strategy, int centerX, int centerY, int radius) throws IOException {
        this.strategy = strategy;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void changeStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void drawWalkingStrategy(Graphics g){
        g.setColor(Color.CYAN);

        currentImageIndex++;
        currentImageIndex = currentImageIndex % zombieSprites.size();

        g.drawImage(zombieSprites.get(currentImageIndex), centerX, centerY, 100, 120, null);
        g.drawOval(centerX, centerY, 1,1);
        g.drawImage(heartSprites.get(1), centerX - radius, centerY - radius, radius*4, radius*2, null);
        //g.drawOval(centerX - radius, centerY - radius, radius*2, radius*2);
    }

    public void calculateNextMove(){
        Vector vector = strategy.generateWalkingPattern(centerX, centerY);
        centerX = centerX + vector.getVx();
        centerY = centerY + vector.getVy();
    }

    public boolean hasCrossedLine(){
        return centerX < 200;
    }

}

