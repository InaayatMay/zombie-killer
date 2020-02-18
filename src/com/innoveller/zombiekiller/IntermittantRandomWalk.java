package com.innoveller.zombiekiller;

public class IntermittantRandomWalk implements Strategy {

    Integer decisionPointX = null;
    Integer decisionPointY = null;

    Vector currentVector = null;


    @Override
    public Vector generateWalkingPattern(int centerX, int centerY) {
        if(decisionPointX == null || decisionPointY == null) {
            decisionPointX = centerX;
            decisionPointY = centerY;
            currentVector =  buildRandomVector();
        }
        else {
            int dx = centerX - decisionPointX;
            int dy = centerY - decisionPointY;
            double distance = Math.sqrt(dx*dx + dy*dy);
            if(distance > 200) {
                decisionPointX = centerX;
                decisionPointY = centerY;
                currentVector = buildRandomVector();
            }
        }
        return currentVector;
    }

    private Vector buildRandomVector() {
        int range = 16;
        int random = (int)(Math.random()*range)-8;
        if(decisionPointY+random > 0 && decisionPointY+random < 1000) {
            return new Vector(-5, random);
        }
        else if(decisionPointY+random < 20) {
            System.out.println("Less than 20");
            return new Vector(-5, 10);
        }
        else if(decisionPointY+random > 990) {
            System.out.println("Greater Than 1000");
            return new Vector(-5, -10);
        }
        else {
            return new Vector(-5, random);
        }
    }
}
