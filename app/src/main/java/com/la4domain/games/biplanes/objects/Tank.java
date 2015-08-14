package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.Animation;
import com.la4domain.games.biplanes.objects.components.DrawableObject;
import com.la4domain.games.biplanes.objects.components.EntityObject;
import com.la4domain.games.biplanes.objects.components.RenderHelper;
import com.la4domain.games.biplanes.objects.components.SpriteHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Tank extends EntityObject {

    private static final String LOG_TAG = Tank.class.getSimpleName();

    private boolean isShooting;
    private int shootingTimer;
    private int tiltTimer;
    private int tiltDir;
    private int tiltState; //From 0 to Const.T_TILE_STATES - 1
    private List bulletList;
    private List collisionList;
    private Random rand;
    private int deathTimer;

    private Animation destroyingAnimation;

    public Tank(Context context, List collisionList, Bitmap sprite, float x, float y) {
        this.context = context;
        this.posX = x;
        this.posY = y;

        rand = new Random();
        spriteHandler = new SpriteHandler(sprite, sprite.getWidth() / Const.T_COLUMN_NUM,
                sprite.getHeight() / ((Const.T_TILE_AMOUNT - 1) / Const.T_COLUMN_NUM + 1),
                Const.T_TILE_AMOUNT, Const.T_COLUMN_NUM, 0);
        destroyingAnimation = new Animation(spriteHandler, Const.T_DESTROYING_ANIMATION_SPEED, 0, 7, 19);
        bulletList = new LinkedList<Bullet>();
        this.collisionList = collisionList;

        hpAmount = Const.T_HP;
        isShooting = true;
        shootingTimer = 0;
        tiltState = Const.T_TILT_STATES / 2;
        tiltTimer = Const.T_TILT_TIMER;
        deathTimer = Const.T_DEATH_TIMER;

        if (rand.nextInt(1) == 1) {
            tiltDir = 1;
        } else {
            tiltDir = -1;
        }
    }

    @Override
    public void draw(Canvas canvas, float cameraXPos, float cameraYPos)     {
        RenderHelper.loopedDraw(spriteHandler.getSprite(), posX, posY, cameraXPos, cameraYPos, canvas);
    }

    @Override
    protected void updateStats(Canvas canvas) {
        if (hpAmount <= 0) {
            if (deathTimer > 0) {
                deathTimer--;
            } else {
                deathTimer = Const.T_DEATH_TIMER;
                hpAmount = Const.T_HP;
                destroyingAnimation = new Animation(spriteHandler, Const.T_DESTROYING_ANIMATION_SPEED, 0, 7, 19);
            }
        }

        tilt();
        shoot(canvas);
    }

    @Override
    protected void rotate() {

    }

    @Override
    protected void animate() {
        if (hpAmount > 0) {
            spriteHandler.setCurrentTileIndex(tiltState);
        }
        else {
            destroyingAnimation.advanceFrame();
        }
    }

    protected void shoot(Canvas canvas) {
        if (shootingTimer > 0) {
            shootingTimer--;
        }

        if (hpAmount > 0 && shootingTimer == 0 && isShooting) {
            float bulPosX = posX;
            float bulPosY = posY;

            bulPosX += spriteHandler.getTileHeight() * Math.cos(Math.toRadians(getDegreeFromState()));
            bulPosY += spriteHandler.getTileHeight() * Math.sin(Math.toRadians(getDegreeFromState()));

            bulletList.add(new Bullet(context, collisionList, bulPosX, bulPosY, getDegreeFromState()));
            shootingTimer = Const.T_SHOOTING_TIMER;
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

    protected void tilt() {
        if (tiltTimer > 0) {
            tiltTimer--;
            return;
        }

        if (tiltState == 0 || tiltState == Const.T_TILT_STATES - 1) {
            tiltDir *= -1;
        }

        tiltState += tiltDir;
        tiltTimer = Const.T_TILT_TIMER;
    }


    protected float getDegreeFromState() {
        return -135F + tiltState * 15;
    }
}
