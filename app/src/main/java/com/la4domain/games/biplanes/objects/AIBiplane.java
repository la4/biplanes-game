package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.Animation;
import com.la4domain.games.biplanes.objects.components.Speed;

import java.util.List;
import java.util.Random;

public class AIBiplane extends Biplane{

    protected static final String LOG_TAG = AIBiplane.class.getSimpleName();
    protected int desiredAltitude;
    private int deathTimer;
    private float desiredDirection;
    private int keepingDirTimer;
    Random rand;

    public AIBiplane(Context context, List collisionList, Bitmap sprite, int canvasWidth, int canvasHeight) {
        super(context, collisionList, sprite, 0, 0);
        rand = new Random();
        this.posX = rand.nextInt(canvasWidth * Const.W_WIDTH_COEFF);
        this.posY = -canvasHeight / 2;
        deathTimer = Const.AI_DEATH_TIMER;
        desiredDirection = rand.nextInt(120) + 30;
        desiredAltitude = rand.nextInt(canvasHeight * (Const.W_HEIGHT_COEFF - 1)) + canvasHeight / 2;
        keepingDirTimer = Const.AI_MAX_KEEPING_DIR;
    }

    @Override
    public void update(Canvas canvas) {
        behave(canvas);
        updateStats(canvas);
        rotate();
        animate();
    }

    protected void behave(Canvas canvas) {
        if (hpAmount > 0) {
            keepingDirTimer--;
            if (reachedAltitude() || keepingDirTimer == 0) {
                desiredDirection = getRandDirection(canvas.getHeight());
                desiredAltitude = getRandAlt(canvas.getHeight());
                keepingDirTimer = Const.AI_MAX_KEEPING_DIR;
                destroyingBiplaneAnimation = new Animation(spriteHandler, Const.B_DESTROYING_ANIMATION_SPEED, 0, 4, 15);
            }

            takeAltitude();

            if (rand.nextInt(Const.AI_SHOOTING_FREQUENCY) == 0) {
                isShooting = true;
            } else {
                isShooting = false;
            }
        }
        else {
            isShooting = false;
            if (deathTimer == 0) {
                deathTimer = Const.AI_DEATH_TIMER;
                this.posX = rand.nextInt(canvas.getWidth() * Const.W_WIDTH_COEFF);
                this.posY = -canvas.getHeight() / 2;
                desiredDirection = 45;
                hpAmount = Const.B_HP;
                biplaneSpeed = new Speed(Const.B_FLYING_SPEED, 0);
            } else {
                deathTimer--;
            }
        }

    }

    protected void takeAltitude() {
        if (angleDistance(desiredDirection, getSimpleAngle(spriteHandler.getTiltDegree())) > Const.B_TILT_DEGREE * 1.5) {
            if (getSimpleAngle(desiredDirection - getSimpleAngle(spriteHandler.getTiltDegree())) <= 180) { // then rotating clockwise faster
                isSteeringUpwards = false;
                isSteeringDownwards = true;
            } else { //then rotating counterclockwise faster
                isSteeringUpwards = true;
                isSteeringDownwards = false;
            }
        } else {
            isSteeringUpwards = false;
            isSteeringDownwards = false;
        }
    }

    protected boolean reachedAltitude() {
        if (Math.abs(posY - desiredAltitude) <= Const.B_FLYING_SPEED * 1.5) {
            return true;
        }
        return false;
    }

    protected int getRandDirection(int canvasHeight) { // Always return 0-359
        if (posY >= canvasHeight * Const.W_HEIGHT_COEFF - Const.AI_OTHER_DEST) {
            return rand.nextInt(179) + 180;
        }
        if (posY <= Const.AI_OTHER_DEST) {
            return rand.nextInt(180);
        }

        return rand.nextInt(359);
    }

    protected int getRandAlt(int canvasHeight) {
        if (desiredDirection >= 0 && desiredDirection<= 180){ //then biplane is directed down
            return rand.nextInt(canvasHeight * Const.W_HEIGHT_COEFF - Const.AI_MIN_HEIGHT - (int)posY) + (int)posY;
        }
        return rand.nextInt((int)posY);
    }

    protected float getSimpleAngle(float angle) {
        angle %= 359;

        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    protected float angleDistance(float angle1, float angle2) {
        return Math.min(Math.abs(angle1 - angle2), 360 - Math.abs(angle1 - angle2));
    }
}
