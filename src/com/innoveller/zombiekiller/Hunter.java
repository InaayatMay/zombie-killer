package com.innoveller.zombiekiller;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Hunter {
    HelperService helperService = new HelperService();
    ArrayList<Image> spriteImageList = helperService.readSprites("female-soldier-sprite.png", 6);
    ArrayList<Image> soldierGiveUpList = helperService.readSprites("female-soldier-give-up.png", 1);
    ArrayList<Image> soldierJumpList = helperService.readSprites("female-soldier-jump-sprite.png", 5);
    static int currentImageIndex = 0;
    private int centerX;
    private int centerY;
    private int radius;

    public Hunter(int centerX, int centerY, int radius) throws IOException {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public Hunter() throws IOException {
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

    public void draw(Graphics g, boolean isAnimating) {

        if(isAnimating) {
            currentImageIndex++;
            currentImageIndex = currentImageIndex % spriteImageList.size();
        }
        g.drawImage(spriteImageList.get(currentImageIndex), centerX - radius, centerY - radius, radius * 5, radius * 5, null);

        g.setColor(Color.CYAN);
        //g.drawOval(centerX, centerY, 1, 1);
        //g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public void drawSoldierGiveUp(Graphics g) {
        g.drawImage(soldierGiveUpList.get(0), centerX - radius, centerY - radius, radius * 5, radius * 5, null);
    }

    public void drawSoldierJump(Graphics g){
        currentImageIndex++;
        currentImageIndex = currentImageIndex % soldierJumpList.size();
        g.drawImage(soldierJumpList.get(currentImageIndex), centerX - radius, centerY - radius, radius * 5, radius * 5, null);
    }
}

