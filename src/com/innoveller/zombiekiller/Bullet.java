package com.innoveller.zombiekiller;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Bullet {
    HelperService helperService = new HelperService();
    ArrayList<Image> spriteImageList = helperService.readSprites("bullet.png", 1);
    ArrayList<Image> standBulletList = helperService.readSprites("stand-bullet.png", 1);
    static int currentImageIndex = 0;
    private int centerX;
    private int centerY;
    private int radius;

    public Bullet(int centerX, int centerY, int radius) throws IOException {
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

    public void draw(Graphics g) {

        currentImageIndex++;
        currentImageIndex = currentImageIndex % spriteImageList.size();
        g.drawImage(spriteImageList.get(currentImageIndex), centerX - radius, centerY - radius, 100, 10, null);

        g.setColor(Color.CYAN);
        //g.drawOval(centerX, centerY, 1, 1);
        //g.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    public void drawStandBullet(Graphics g){
        g.drawImage(standBulletList.get(0), centerX-radius, centerY-radius, 10, 40, null);
    }

    public boolean isTouching(Zombie zombie) {
        int dx = (this.centerX+60) - zombie.getCenterX();
        int dy = this.centerY - zombie.getCenterY();
        double distance = Math.sqrt(dx*dx + dy*dy);
        return distance < (this.radius + zombie.getRadius());
    }

}

