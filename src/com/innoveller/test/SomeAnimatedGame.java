package com.innoveller.test;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SomeAnimatedGame {
    static int ballX = 100;
    static int ballY = 100;

    static int currentImageIndex = 0;
    static boolean isAnimating = false;
    static int playerY = 200;
    static int playerDeltaY = 0;

    public static void playBackgroundMusic(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip.open(inputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static Clip buildAudioClip(String filePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filePath));
        clip.open(inputStream);
        return clip;
    }

    public static ArrayList<Image> readSprites(String filePath, int numSpritesPerCycle) throws IOException {
        ArrayList<Image> imageList = new ArrayList<>();
        File pathToFile = new File(filePath);
        BufferedImage fullImage = ImageIO.read(pathToFile);
        int spriteWidth = fullImage.getWidth() / numSpritesPerCycle;
        int spriteHeight = fullImage.getHeight();
        for(int i = 0; i< numSpritesPerCycle; i++) {
            int x = i * spriteWidth;
            Image subImage = fullImage.getSubimage(x, 0, spriteWidth, spriteHeight);
            imageList.add(subImage);
        }

        return imageList;
    }

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        playBackgroundMusic("background-music.wav");
        final Clip soundClip = buildAudioClip("gun-shot.wav");
        ArrayList<Image> spriteImageList = readSprites("female-soldier-sprite.png", 6);

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(600, 600));
        jFrame.setVisible(true);

        JPanel gameCanvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.GRAY);
                g.drawOval(ballX, ballY, 20, 20);

                if(isAnimating) {
                    currentImageIndex++;
                    currentImageIndex = currentImageIndex % spriteImageList.size();
                }
                g.drawImage(spriteImageList.get(currentImageIndex), 200, playerY, 100, 120, null);
            }
        };
        gameCanvas.setBackground(new Color(24, 48, 100));
        jFrame.add(gameCanvas);

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyReleased(e);
                isAnimating = true;
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_UP: {System.out.println("VK_UP"); playerDeltaY =-10; break;}
                    case KeyEvent.VK_DOWN: {System.out.println("VK_DOWN"); playerDeltaY = 10; break;}
                    case KeyEvent.VK_LEFT: System.out.println("VK_LEFT"); break;
                    case KeyEvent.VK_RIGHT: System.out.println("VK_RIGHT"); break;
                    case KeyEvent.VK_A: {System.out.println("VK_A");
                        soundClip.setFramePosition(0);
                        soundClip.start();
                        ; break;}
                    case KeyEvent.VK_S: System.out.println("VK_S"); break;
                    case KeyEvent.VK_SPACE: System.out.println("VK_SPACE"); break;
                }
            }
        });

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                isAnimating = false;
                //playerDeltaY = 0;
            }
        });

        TimerTask task = new TimerTask() {
            public void run() {
                ballX++;
                if(isAnimating) {
                    playerY += playerDeltaY;
                }
                //System.out.println("Timer Tick");
                gameCanvas.repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000, 100);
    }
}
