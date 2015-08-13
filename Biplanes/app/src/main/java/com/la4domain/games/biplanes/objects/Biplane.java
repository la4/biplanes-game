package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.la4domain.games.biplanes.MainGameView;
import com.la4domain.games.biplanes.objects.components.Collision;
import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.Animation;
import com.la4domain.games.biplanes.objects.components.BaseObject;
import com.la4domain.games.biplanes.objects.components.Speed;
import com.la4domain.games.biplanes.objects.components.SpriteHandler;
import com.la4domain.games.biplanes.objects.components.RenderHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Biplane extends BaseObject {

    protected static final String LOG_TAG = Biplane.class.getSimpleName();

    public boolean isSteeringUpwards;
    public boolean isSteeringDownwards;
    public boolean isShooting;
    protected List collisionList;
    protected List bulletList;
    protected int shootingTimer;

    protected Animation flyingBiplaneAnimation;
    protected Animation destroyingBiplaneAnimation;

    protected Speed biplaneSpeed;

    public Biplane(Context context, List collisionList, Bitmap sprite, float x, float y) {
        this.context = context;
        posX = x;
        posY = y;

        hpAmount = Const.B_HP;
        spriteHandler = new SpriteHandler(sprite, sprite.getWidth() / Const.B_COLUMN_NUM,
                sprite.getHeight() / ((Const.B_TILE_AMOUNT - 1) / Const.B_COLUMN_NUM + 1),
                Const.B_TILE_AMOUNT, Const.B_COLUMN_NUM, 0);
        flyingBiplaneAnimation = new Animation(spriteHandler, Const.B_FLYING_ANIMATION_SPEED, 2, 0, 3);
        destroyingBiplaneAnimation = new Animation(spriteHandler, Const.B_DESTROYING_ANIMATION_SPEED, 0, 4, 15);
        biplaneSpeed = new Speed(Const.B_FLYING_SPEED, 0);
        bulletList = new LinkedList<Bullet>();
        this.collisionList = collisionList;

        isSteeringUpwards = false;
        isSteeringDownwards = false;
        isShooting = false;
        shootingTimer = 0;
    }

    @Override
    public void draw(Canvas canvas, float cameraXPos, float cameraYPos) {

        for (int i = 0; i < bulletList.size(); i++) {
            ((Bullet) bulletList.get(i)).draw(canvas, cameraXPos, cameraYPos);
        }

        RenderHelper.loopedDraw(spriteHandler.getSprite(), posX, posY, cameraXPos, cameraYPos, canvas);
    }

    @Override
    public void update(Canvas canvas) {
        updatePosition(canvas);
        animate();
        rotate();
        shoot(canvas);
        Log.d(LOG_TAG, Integer.toString(hpAmount));
    }

    @Override
    protected void animate() {
        if (hpAmount > 0) {
            flyingBiplaneAnimation.advanceFrame();
        } else {
            destroyingBiplaneAnimation.advanceFrame();
        }
    }

    @Override
    protected void rotate() {
        if (isSteeringUpwards && !isSteeringDownwards && hpAmount > 0) {
            spriteHandler.addTiltDegree(-Const.B_TILT_DEGREE);
        }
        if (isSteeringDownwards && !isSteeringUpwards && hpAmount > 0) {
            spriteHandler.addTiltDegree(Const.B_TILT_DEGREE);
        }
    }

    protected void updatePosition(Canvas canvas) {
        /*Coordinate system is strange.
        * positive angle in tiltDegree means steering clockwise, negative - counterclockwise.
        * Not like in math.
        * Pixel grid has Y axis directed down. So be careful when trying to calculate dimensions*/


        biplaneSpeed.update();
        if (hpAmount == 0) {
            biplaneSpeed.setYAcceleration(-0.07F);
            biplaneSpeed.setXAcceleration(-0.07F);
            posY += Const.B_FALLING_SPEED;
        }

        if (hpAmount == 0 && biplaneSpeed.getXShift() == 0 && biplaneSpeed.getYShift() == 0) {
            MainGameView.gameState = false;
        }

        posX += biplaneSpeed.getXShift() * Math.cos(Math.toRadians(spriteHandler.getTiltDegree()));
        posY += biplaneSpeed.getXShift() * Math.sin(Math.toRadians(spriteHandler.getTiltDegree()));

        posX -= biplaneSpeed.getYShift() * Math.sin(Math.toRadians(spriteHandler.getTiltDegree()));
        posY -= biplaneSpeed.getYShift() * Math.cos(Math.toRadians(spriteHandler.getTiltDegree()));


        if (posX < 0) {
            posX = canvas.getWidth() * Const.W_WIDTH_COEFF + posX;
        }
        if (posX > canvas.getWidth() * Const.W_WIDTH_COEFF) {
            posX %= canvas.getWidth() * Const.W_WIDTH_COEFF;
        }

        if (posY < 0) {
            posY = 0;
        }

        if (posY  >= canvas.getHeight() * Const.W_HEIGHT_COEFF - Const.G_HEIGHT * context.getResources().getDisplayMetrics().density) {
            hpAmount = 0;
            posY = canvas.getHeight() * Const.W_HEIGHT_COEFF - Const.G_HEIGHT * context.getResources().getDisplayMetrics().density;
        }

        /*Collisions with other objects*/
        ListIterator<BaseObject> itr = collisionList.listIterator();

        while (itr.hasNext()) {
            BaseObject bufObj = itr.next();
            if (bufObj != this && Collision.checkCollision(this, bufObj)) {
                this.kill();
                bufObj.kill();
            }
        }
        /*End*/
    }

    protected void shoot(Canvas canvas) {

        if (shootingTimer > 0) {
            shootingTimer--;
        }

        if (shootingTimer == 0 && isShooting && hpAmount > 0) {
            bulletList.add(new Bullet(context, collisionList, posX + (spriteHandler.getTileWidth() / 2) * (float) Math.cos(Math.toRadians(spriteHandler.getTiltDegree())), posY + (spriteHandler.getTileWidth() / 2) * (float) Math.sin(Math.toRadians(spriteHandler.getTiltDegree())), spriteHandler.getTiltDegree()));
            shootingTimer = Const.B_SHOOTING_TIMER;
        }

        ListIterator<Bullet> itr = bulletList.listIterator();
        while (itr.hasNext()) {
            Bullet buf = itr.next();
            if (buf.isAlive()) {
                buf.update(canvas);
            } else {
                itr.remove();
            }
        }

    }

}
