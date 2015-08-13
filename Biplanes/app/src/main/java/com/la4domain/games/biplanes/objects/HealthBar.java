package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.BaseObject;
import com.la4domain.games.biplanes.objects.components.SpriteHandler;


public class HealthBar extends BaseObject {

    private static final String LOG_TAG = HealthBar.class.getSimpleName();

    private Biplane biplane;
    public HealthBar(Context context, Bitmap sprite, float x, float y, Biplane biplane) {
        this.context = context;
        this.posX = x;
        this.posY = y;
        this.biplane = biplane;
        spriteHandler = new SpriteHandler(sprite, sprite.getWidth() / Const.HP_COLUMN_NUM,
                sprite.getHeight() / ((Const.HP_TILE_AMOUNT - 1) / Const.HP_COLUMN_NUM + 1),
                Const.HP_TILE_AMOUNT, Const.HP_COLUMN_NUM, 0);

    }

    @Override
    public void draw(Canvas canvas, float cameraXPos, float cameraYPos)     {
        Bitmap bufBit = spriteHandler.getSprite();
        canvas.drawBitmap(bufBit, posX - (bufBit.getWidth() / 2), posY - (bufBit.getHeight() / 2), null);
    }

    @Override
    public void update(Canvas canvas) {
        animate();
    }

    @Override
    protected void rotate() {

    }

    @Override
    protected void animate() {
        spriteHandler.setCurrentTileIndex(Const.B_HP - biplane.getHpAmount());
    }
}
