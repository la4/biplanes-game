package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.BaseObject;
import com.la4domain.games.biplanes.objects.components.RenderHelper;
import com.la4domain.games.biplanes.objects.components.Speed;
import com.la4domain.games.biplanes.objects.components.SpriteHandler;

public class Cloud extends BaseObject {
    private int direction;
    private Speed cloudSpeed;

    public Cloud(Context context, Bitmap sprite, float x, float y, int direction, float speed) {
        this.context = context;
        this.posX = x;
        this.posY = y;
        this.direction = direction;

        cloudSpeed = new Speed(direction * speed, 0);

        spriteHandler = new SpriteHandler(sprite, sprite.getWidth(), sprite.getHeight(), 1, 1, 0);
    }

    @Override
    public void draw(Canvas canvas, float cameraXPos, float cameraYPos) {
        RenderHelper.loopedDraw(spriteHandler.getSprite(), posX, posY, cameraXPos, cameraYPos, canvas);
    }

    @Override
    public void update(Canvas canvas) {
        animate();
        rotate();
        updatePosition(canvas);
    }

    @Override
    protected void animate() {
    }

    @Override
    protected void rotate() {
    }

    protected void updatePosition(Canvas canvas) {
        posX += cloudSpeed.getXShift();

        if (posX < 0) {
            posX = canvas.getWidth() * Const.W_WIDTH_COEFF + posX;
        }
        if (posX > canvas.getWidth() * Const.W_WIDTH_COEFF) {
            posX %= canvas.getWidth() * Const.W_WIDTH_COEFF;
        }
    }
}
