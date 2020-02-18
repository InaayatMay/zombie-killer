package com.innoveller.zombiekiller;

public class StraightWalk implements Strategy {
    @Override
    public Vector generateWalkingPattern(int centerX, int centerY) {
        return new Vector(-20, 0);
    }
}
