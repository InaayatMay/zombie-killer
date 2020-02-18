package com.innoveller.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class SomeGame {
    static int ballX = 100;
    static int ballY = 100;

    public static void main(String[] args) {

        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(600, 600));
        jFrame.setVisible(true);

        JPanel gameCanvas = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                g.setColor(Color.CYAN);
                g.drawOval(ballX, ballY, 150, 50);
            }
        };
        gameCanvas.setBackground(new Color(24, 48, 100));
        jFrame.add(gameCanvas);

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_UP: System.out.println("VK_UP"); break;
                    case KeyEvent.VK_DOWN: System.out.println("VK_DOWN"); break;
                    case KeyEvent.VK_LEFT: System.out.println("VK_LEFT"); break;
                    case KeyEvent.VK_RIGHT: System.out.println("VK_RIGHT"); break;
                    case KeyEvent.VK_A: System.out.println("VK_A"); break;
                    case KeyEvent.VK_S: System.out.println("VK_S"); break;
                    case KeyEvent.VK_SPACE: System.out.println("VK_SPACE"); break;
                }

            }
        });

        TimerTask task = new TimerTask() {
            public void run() {
                ballX+=2;
                //System.out.println("Timer Tick");
                gameCanvas.repaint();
            }
        };
        java.util.Timer timer = new Timer();
        timer.schedule(task, 1000, 100);
    }
}