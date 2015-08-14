package com.la4domain.games.biplanes.objects.components;

import android.graphics.Canvas;

public abstract class EntityObject extends DrawableObject {

    protected int hpAmount;

    @Override
    public void update(Canvas canvas) {
        updateStats(canvas);
        rotate();
        animate();
    }

    abstract protected void updateStats(Canvas canvas);


    abstract protected void rotate();

    public void reduceHp() {
        if (hpAmount > 0) {
            hpAmount--;
        }
    }

    public int getHpAmount() {
        return hpAmount;
    }

    public void kill() {
        hpAmount = 0;
    }

}
