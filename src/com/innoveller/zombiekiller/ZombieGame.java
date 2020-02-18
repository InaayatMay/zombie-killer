package com.innoveller.zombiekiller;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ZombieGame {
    private static List<Zombie> zombieList = new ArrayList<>();
    private static List<BulletState> bulletList = new ArrayList<>();
    private static List<BulletState> shotBulletList = new ArrayList<>();
    private static List<Bullet> standBullets = new ArrayList<>();
    private static int indexOfBullet = 0;
    static boolean isAnimating = false;
    static boolean isGiveUp = false;

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        HelperService helperService = new HelperService();
        final Clip backgroundSound = helperService.playBackgroundMusic("background-music.wav");
        final Clip gunShot = helperService.buildAudioClip("gun-shot.wav");
        final Clip zombieLaugh = helperService.buildAudioClip("zombie-laughing.wav");
        final Clip winningTrack = helperService.buildAudioClip("winning-track.wav");
        final Clip zombieScream = helperService.buildAudioClip("zombie-scream.wav");
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(1800, 1000));
        jFrame.setVisible(true);

        Zombie zombie = new Zombie(new RandomWalk(), 1800, 200, 25);
        Zombie zombie4 = new Zombie(new StraightWalk(), 1600, 300, 25);
        Zombie zombie5 = new Zombie(new IntermittantRandomWalk(), 1700, 200, 25);

        Zombie zombie1 = new Zombie(new IntermittantRandomWalk(),1800, 100, 25);
        Zombie zombie2 = new Zombie(new StraightWalk(),2100, 600, 25);
        Zombie zombie3 = new Zombie(new RandomWalk(),2000, 800, 25);
        Hunter hunter = new Hunter(25, 400, 25);

        for(int i=0; i<10; i++){
            Bullet standBullet = new Bullet(10+(i*20), 10, 7);
            standBullets.add(standBullet);

            BulletState bullet = new BulletState(new Bullet(120, 400, 5), false);
            bulletList.add(bullet);
        }

        zombieList.add(zombie1);
        zombieList.add(zombie2);
        zombieList.add(zombie3);
        zombieList.add(zombie);
        zombieList.add(zombie4);
        zombieList.add(zombie5);

        JPanel gameCanvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.CYAN);

                g.drawLine(300, 0, 300, (int) jFrame.getContentPane().getSize().getHeight());

                for(Zombie zombie: zombieList){
                    zombie.drawWalkingStrategy(g);

                    if(zombie.hasCrossedLine()){
                        isGiveUp = true;
                        helperService.showFinalResult(g, backgroundSound, zombieLaugh, Color.RED, "Game Over!");
                        //jFrame.setEnabled(false);
                    }
                }

                if(shotBulletList.size() > 0) {
                    for(BulletState shotBullet: shotBulletList) {
                        shotBullet.bullet.draw(g);
                    }

                    for(int i=0; i<standBullets.size()-shotBulletList.size(); i++){
                        standBullets.get(i).drawStandBullet(g);
                    }
                }
                else{
                    for(Bullet standBullet: standBullets){
                        standBullet.drawStandBullet(g);
                    }
                }

                if((shotBulletList.size() == bulletList.size() && zombieList.size() > 0 ) || isGiveUp){
                    helperService.showFinalResult(g, backgroundSound, zombieLaugh, Color.RED, "Game Over!");
                    hunter.drawSoldierGiveUp(g);
                }
                else if(zombieList.size() == 0){
                    hunter.drawSoldierJump(g);
                    helperService.showFinalResult(g, backgroundSound, winningTrack, Color.GREEN, "Congratulations! You Win.");
                }
                else{
                    hunter.draw(g, isAnimating);
                }
            }
        };
        gameCanvas.setBackground(new Color(70, 47, 36));
        jFrame.add(gameCanvas);

        jFrame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyReleased(e);
                isAnimating = true;
                if(!isGiveUp && zombieList.size() > 0) {
                    switch (e.getKeyCode())
                    {
                        case KeyEvent.VK_UP: {
                            if(hunter.getCenterY() > 20){
                                hunter.setCenterY(hunter.getCenterY()-5);
                            }

                            if(bulletList.size() > indexOfBullet) {
                                bulletList.get(indexOfBullet).bullet.setCenterY(hunter.getCenterY());
                            }
                        } break;
                        case KeyEvent.VK_DOWN: {
                            if(hunter.getCenterY() < 1000){
                                hunter.setCenterY(hunter.getCenterY()+5);
                            }

                            if(bulletList.size() > indexOfBullet) {
                                bulletList.get(indexOfBullet).bullet.setCenterY(hunter.getCenterY());
                            }
                        } break;
                        case KeyEvent.VK_LEFT: System.out.println("VK_LEFT"); break;
                        case KeyEvent.VK_RIGHT: System.out.println("VK_RIGHT"); break;
                        case KeyEvent.VK_A: System.out.println("VK_A"); break;
                        case KeyEvent.VK_S: System.out.println("VK_S"); break;
                        case KeyEvent.VK_SPACE: {
                            if(bulletList.size() > indexOfBullet) {
                                shotBulletList.add(bulletList.get(indexOfBullet));
                                shotBulletList.get(shotBulletList.size()-1).bullet.setCenterY(shotBulletList.get(shotBulletList.size()-1).bullet.getCenterY()+30);
                                shotBulletList.get(shotBulletList.size()-1).shoot = true;
                                indexOfBullet++;

                                gunShot.setFramePosition(0);
                                gunShot.start();
                            }
                        } break;
                    }
                }

            }
        });

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                isAnimating = false;
                //hunter.setCenterY(0);
            }
        });

        TimerTask task = new TimerTask() {
            public void run() {
                zombie.calculateNextMove();
                zombie1.calculateNextMove();
                zombie2.calculateNextMove();
                zombie3.calculateNextMove();
                zombie4.calculateNextMove();
                zombie5.calculateNextMove();

                if(shotBulletList.size()>0) {
                    for(int i = 0; i< shotBulletList.size(); i++){
                        shotBulletList.get(i).bullet.setCenterX(shotBulletList.get(i).bullet.getCenterX()+30);
                        if(zombieList.size() > 0) {
                            for(int j=0; j<zombieList.size(); j++) {
                                if(shotBulletList.get(i).bullet.isTouching(zombieList.get(j))) {
                                    zombieScream.setFramePosition(0);
                                    zombieScream.start();
                                    zombieList.remove(j);

                                    if(zombieList.size() > 0) {
                                        zombieList.get(0).changeStrategy(new StraightWalk());
                                    }
                                }
                            }
                        }
                    }
                }
                gameCanvas.repaint();
            }
        };
        java.util.Timer timer = new Timer();
        timer.schedule(task, 1000, 100);
    }
}