package com.la4domain.games.biplanes.objects.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.la4domain.games.biplanes.Const;

public class RenderHelper {
    public static void loopedDraw(Bitmap bufBit, float posX, float posY, float cameraXPos, float cameraYPos, Canvas canvas) {

         /*Drawing 3 times to make visibility of looped world by x abscissa*/
        float mPosX = posX;

        if (mPosX - (bufBit.getWidth() / 2) - cameraXPos >=  -bufBit.getWidth() * 2 &&
                mPosX - (bufBit.getWidth() / 2) - cameraXPos <= canvas.getWidth() + bufBit.getWidth() * 2 &&
                posY - (bufBit.getHeight() / 2) - cameraYPos >= -bufBit.getWidth() * 2 &&
                posY - (bufBit.getHeight() / 2) - cameraYPos <= canvas.getHeight() + bufBit.getWidth() * 2) {

            canvas.drawBitmap(bufBit, mPosX - (bufBit.getWidth() / 2) - cameraXPos, posY - (bufBit.getHeight() / 2) - cameraYPos, null);
        }

        mPosX = posX - canvas.getWidth() * Const.W_WIDTH_COEFF;

        if (mPosX - (bufBit.getWidth() / 2) - cameraXPos >=  -bufBit.getWidth() * 2 &&
                mPosX - (bufBit.getWidth() / 2) - cameraXPos <= canvas.getWidth() + bufBit.getWidth() * 2 &&
                posY - (bufBit.getHeight() / 2) - cameraYPos >= -bufBit.getWidth() * 2 &&
                posY - (bufBit.getHeight() / 2) - cameraYPos <= canvas.getHeight() + bufBit.getWidth() * 2) {

            canvas.drawBitmap(bufBit, mPosX - (bufBit.getWidth() / 2) - cameraXPos, posY - (bufBit.getHeight() / 2) - cameraYPos, null);
        }

        mPosX = posX + canvas.getWidth() * Const.W_WIDTH_COEFF;

        if (mPosX - (bufBit.getWidth() / 2) - cameraXPos >=  -bufBit.getWidth() * 2 &&
                mPosX - (bufBit.getWidth() / 2) - cameraXPos <= canvas.getWidth() + bufBit.getWidth() * 2 &&
                posY - (bufBit.getHeight() / 2) - cameraYPos >= -bufBit.getWidth() * 2 &&
                posY - (bufBit.getHeight() / 2) - cameraYPos <= canvas.getHeight() + bufBit.getWidth() * 2) {

            canvas.drawBitmap(bufBit, mPosX - (bufBit.getWidth() / 2) - cameraXPos, posY - (bufBit.getHeight() / 2) - cameraYPos, null);
        }

    }

    public static void loopedBulletDraw(SpriteHandler spriteHandler, float posX, float posY, float cameraXPos, float cameraYPos, Canvas canvas, Paint drawingPaint) {
        float mPosX = posX;

        if (mPosX - (Const.BUL_LENGTH / 2) - cameraXPos >= -Const.BUL_LENGTH * 2 &&
                mPosX - (Const.BUL_LENGTH / 2) - cameraXPos <= canvas.getWidth() + Const.BUL_LENGTH * 2 &&
                posY - (Const.BUL_LENGTH / 2) - cameraYPos >= -Const.BUL_LENGTH * 2 &&
                posY - (Const.BUL_LENGTH / 2) - cameraYPos <= canvas.getHeight() + Const.BUL_LENGTH * 2) {

            canvas.drawLine(mPosX - cameraXPos, posY - cameraYPos, mPosX + Const.BUL_LENGTH * (float) Math.cos(Math.toRadians(spriteHandler.getTiltDegree())) - cameraXPos, posY + Const.BUL_LENGTH * (float) Math.sin(Math.toRadians(spriteHandler.getTiltDegree())) - cameraYPos, drawingPaint);
        }

        mPosX = posX - canvas.getWidth() * Const.W_WIDTH_COEFF;

        if (mPosX - (Const.BUL_LENGTH / 2) - cameraXPos >= -Const.BUL_LENGTH * 2 &&
                mPosX - (Const.BUL_LENGTH / 2) - cameraXPos <= canvas.getWidth() + Const.BUL_LENGTH * 2 &&
                posY - (Const.BUL_LENGTH / 2) - cameraYPos >= -Const.BUL_LENGTH * 2 &&
                posY - (Const.BUL_LENGTH / 2) - cameraYPos <= canvas.getHeight() + Const.BUL_LENGTH * 2) {

            canvas.drawLine(mPosX - cameraXPos, posY - cameraYPos, mPosX + Const.BUL_LENGTH * (float) Math.cos(Math.toRadians(spriteHandler.getTiltDegree())) - cameraXPos, posY + Const.BUL_LENGTH * (float) Math.sin(Math.toRadians(spriteHandler.getTiltDegree())) - cameraYPos, drawingPaint);
        }

        mPosX = posX + canvas.getWidth() * Const.W_WIDTH_COEFF;

        if (mPosX - (Const.BUL_LENGTH / 2) - cameraXPos >= -Const.BUL_LENGTH * 2 &&
                mPosX - (Const.BUL_LENGTH / 2) - cameraXPos <= canvas.getWidth() + Const.BUL_LENGTH * 2 &&
                posY - (Const.BUL_LENGTH / 2) - cameraYPos >= -Const.BUL_LENGTH * 2 &&
                posY - (Const.BUL_LENGTH / 2) - cameraYPos <= canvas.getHeight() + Const.BUL_LENGTH * 2) {

            canvas.drawLine(mPosX - cameraXPos, posY - cameraYPos, mPosX + Const.BUL_LENGTH * (float) Math.cos(Math.toRadians(spriteHandler.getTiltDegree())) - cameraXPos, posY + Const.BUL_LENGTH * (float) Math.sin(Math.toRadians(spriteHandler.getTiltDegree())) - cameraYPos, drawingPaint);
        }
    }
}
