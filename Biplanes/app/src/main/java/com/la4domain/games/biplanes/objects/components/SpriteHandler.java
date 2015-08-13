package com.la4domain.games.biplanes.objects.components;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

public class SpriteHandler {

    static final String LOG_TAG = SpriteHandler.class.getSimpleName();

    Bitmap sprite;

    int currentTileIndex; //visible for Animation class
    float tiltDegree; //visible for Rotation class
    private int tileHeight, tileWidth;
    private int tileNum, columnsNum; //tiles counts from zero
    Matrix tiltMatrix;

    public SpriteHandler(Bitmap sprite, int tileWidth, int tileHeight, int tileNum, int columnsNum, float tiltDegree) {
        this.sprite = sprite;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.tileNum = tileNum;
        this.columnsNum = columnsNum;
        this.tiltDegree = tiltDegree;

        currentTileIndex = 0;

        tiltMatrix = new Matrix();
        tiltMatrix.setRotate(tiltDegree);
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileNum() {
        return tileNum;
    }

    public int getCurrentTileIndex() {
        return currentTileIndex;
    }

    public void setCurrentTileIndex(int currentTileIndex) {
        this.currentTileIndex = currentTileIndex;
    }

    public float getTiltDegree() {
        return tiltDegree;
    }

    public void setTiltDegree(float tiltDegree) {
        this.tiltDegree = tiltDegree;
    }

    public void addTiltDegree(float tiltDegree) {
        this.tiltDegree += tiltDegree;
    }

    public Bitmap getSprite() { // tileIndex from zero
        try {
            tiltMatrix.setRotate(tiltDegree, tileWidth / 2, tileHeight / 2);

            return Bitmap.createBitmap(sprite, (currentTileIndex % columnsNum) * tileWidth, (currentTileIndex / columnsNum) * tileHeight, tileWidth, tileHeight, tiltMatrix, false);
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, "Trying to use getSprite in a non-sprite object");
            return null;
        }
    }
}
