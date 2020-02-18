package com.innoveller.zombiekiller;

public class RandomWalk implements Strategy{

    @Override
    public Vector generateWalkingPattern(int centerX, int centerY) {
        int range = 16;
        int random = (int)(Math.random()*range)-8;
        if(centerY+random > 0 && centerY+random < 1000) {
            return new Vector(-3, -random);
        }else if(centerY + random < 10){
            return new Vector(-3, 10);
        }else if(centerY + random > 990){
            return new Vector(-3, -10);
        }else{
            return new Vector(-3, -random);
        }
    }
}
