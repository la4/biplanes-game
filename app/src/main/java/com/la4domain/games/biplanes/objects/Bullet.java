package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.la4domain.games.biplanes.objects.components.Collision;
import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.DrawableObject;
import com.la4domain.games.biplanes.objects.components.EntityObject;
import com.la4domain.games.biplanes.objects.components.RenderHelper;
import com.la4domain.games.biplanes.objects.components.Speed;
import com.la4domain.games.biplanes.objects.components.SpriteHandler;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Bullet extends EntityObject {

    private static final String LOG_TAG = Bullet.class.getSimpleName();

    private int lifeTimer;
    private Speed bulletSpeed;
    Paint drawingPaint;
    List collisionList;

    public Bullet(Context context, List collissionList, float posX, float posY, float directionAngle) {
        this.context = context;
        this.collisionList = collissionList;

        this.posX = posX;
        this.posY = posY;
        bulletSpeed = new Speed(Const.BUL_SPEED, 0);
        lifeTimer = Const.BUL_CYCLES;
        hpAmount = 1;
        Random angleRandom = new Random();
        spriteHandler = new SpriteHandler(null, Const.B_WIDTH, Const.B_HEIGHT, 1, 0, directionAngle + (angleRandom.nextFloat() - 0.5F) * 2 * Const.BUL_MAX_RAND_ANGLE);
        drawingPaint = new Paint();
        drawingPaint.setStrokeWidth(Const.BUL_THICKNESS);
        drawingPaint.setColor(Color.rgb(100, 17, 36));
    }

    /*
     * Bullet's hpAmount is it's state:
     * 0 - dead
     * 1 - alive
     */
    public boolean isAlive() {
        if (hpAmount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateStats(Canvas canvas) {

        if (lifeTimer == 0 || posY >= canvas.getHeight() * Const.W_HEIGHT_COEFF - Const.G_HEIGHT * context.getResources().getDisplayMetrics().density) {
            hpAmount = 0;
        }

        // Lifecycle
        if (lifeTimer < Const.BUL_CYCLES * Const.BUL_FADE) {
            bulletSpeed.setXAcceleration(-0.5F);
        }
        if (lifeTimer < Const.BUL_CYCLES / 2.1) {
            drawingPaint.setColor(Color.rgb(134, 85, 95));
        }
        if (lifeTimer < Const.BUL_CYCLES / 4) {
            drawingPaint.setColor(Color.rgb(179, 126, 137));
        }
        if (lifeTimer < Const.BUL_CYCLES / 5) {
            drawingPaint.setColor(Color.rgb(199, 164, 171));
        }

        if (hpAmount > 0) {
            lifeTimer--;
            bulletSpeed.update();

            posX += bulletSpeed.getXShift() * Math.cos(Math.toRadians(spriteHandler.getTiltDegree()));
            posY += bulletSpeed.getXShift() * Math.sin(Math.toRadians(spriteHandler.getTiltDegree()));

            posY += bulletSpeed.getYShift() * Math.cos(Math.toRadians(spriteHandler.getTiltDegree()));
            posY += bulletSpeed.getYShift() * Math.sin(Math.toRadians(spriteHandler.getTiltDegree()));
        }

        if (posX < 0) {
            posX = canvas.getWidth() * Const.W_WIDTH_COEFF + posX;
        }
        if (posX > canvas.getWidth() * Const.W_WIDTH_COEFF) {
            posX %= canvas.getWidth() * Const.W_WIDTH_COEFF;
        }

        checkCollisions();
    }

    protected void checkCollisions() {
        ListIterator<EntityObject> itr = collisionList.listIterator();

        while (isAlive() && itr.hasNext()) {
            EntityObject bufObj = itr.next();
            if (Collision.checkCollision(this, bufObj)) {
                this.kill();
                bufObj.reduceHp();
            }
        }
    }

    @Override
    public void draw(Canvas canvas, float cameraXPos, float cameraYPos) {
        RenderHelper.loopedBulletDraw(spriteHandler, posX, posY, cameraXPos, cameraYPos, canvas, drawingPaint);
    }

    @Override
    protected void animate() {

    }

    @Override
    protected void rotate() {

    }
}
