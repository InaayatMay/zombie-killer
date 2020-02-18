package com.innoveller.zombiekiller;

public class BulletState {
    public Bullet bullet;
    public boolean shoot;

    public BulletState(Bullet bullet, boolean shoot) {
        this.bullet = bullet;
        this.shoot = shoot;
    }
}
