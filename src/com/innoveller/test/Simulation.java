package com.innoveller.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
    static double ballX = 100;
    static double ballY = 100;

    static double ballVelocityX = 0;
    static double ballVelocityY = 6;

    static double bounciness = 0.7;

    static double gravityVelocityX = 0;
    static double gravityVelocityY = 0.07;

    static double airResistanceVelocityX = -0.0000;
    static double airResistanceVelocityY = 0;

    static int ceilingY = 50;
    static int floorY = 600;

    /*interface GrpahicObject {

        void render();

        GrpahicObject[] children();
    }

    class Solider implements GrpahicObject {
        void render() {
            //sldkfsldjfl
        }
    }

    //Element

    interface GraphicsEngine {
        void update(GrpahicObject[] grpahicObjects);
    }

    class WebGraphicsEngine implements GraphicsEngine {

    }*/


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
                g.drawOval((int) ballX -  25, (int) ballY - 25, 50, 50);

                g.drawLine(0, ceilingY, 1300, ceilingY);
                g.drawLine(0, floorY, 1300, floorY);
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

        TimerTask physicTask = new TimerTask() {
            public void run() {

                ballVelocityX = ballVelocityX + gravityVelocityX + airResistanceVelocityX;
                ballVelocityY = ballVelocityY + gravityVelocityY + airResistanceVelocityY;

                ballX += (ballVelocityX);
                ballY += (ballVelocityY);

                /*if(ballY > floorY - 25) {
                    //ballY = floorY - 24;
                    //ballVelocityY = - Math.abs(gravityVelocityY);
                }*/

                if(ballY < ceilingY + 25) {
                    //ballVelocityY =  Math.abs(ballVelocityY) * 0.7;
                }
                if(ballY > floorY - 25) {
                    ballY = floorY - 25;
                    ballVelocityY = - Math.abs(ballVelocityY) * bounciness;
                }
                //gameCanvas.repaint();
            }
        };
        TimerTask renderTask = new TimerTask() {
            public void run() {
                gameCanvas.repaint();
                //graphicEngine.update()
            }
        };

        TimerTask logTask = new TimerTask() {
            public void run() {
                System.out.println(ballVelocityY + "\t" + ballY);
                //graphicEngine.update()
            }
        };

        (new Timer()).schedule(physicTask, 1000, 5);
        (new Timer()).schedule(renderTask, 1000, 100);
        (new Timer()).schedule(logTask, 1000, 2000);
    }
}