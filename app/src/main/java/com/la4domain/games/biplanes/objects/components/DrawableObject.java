package com.la4domain.games.biplanes.objects.components;

import android.content.Context;
import android.graphics.Canvas;

public abstract class DrawableObject {

    protected Context context;

    protected SpriteHandler spriteHandler; // A sprite or canvas primitive, visible for Collision

    protected float posX, posY;

    public float getXPos() {
        return posX;
    }

    public float getYPos() {
        return posY;
    }

    abstract public void draw(Canvas canvas, float cameraXPos, float cameraYPos);

    abstract public void update(Canvas canvas);

    abstract protected void animate();
}