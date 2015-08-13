package com.la4domain.games.biplanes.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.la4domain.games.biplanes.Const;
import com.la4domain.games.biplanes.objects.components.BaseObject;
import com.la4domain.games.biplanes.objects.components.SpriteHandler;

public class Ground extends BaseObject {

    public Ground(Context context, Bitmap sprite, float x, float y) {
        this.context = context;
        this.posX = x;
        this.posY = y;

        spriteHandler = new SpriteHandler(sprite, sprite.getWidth() / Const.G_COLUMN_NUM,
                sprite.getHeight() / ((Const.G_TILE_AMOUNT - 1) / Const.G_COLUMN_NUM + 1),
                Const.G_TILE_AMOUNT, Const.G_COLUMN_NUM, 0);
    }

    //Needs changing
    @Override
    public void draw(Canvas canvas, float cameraXPos, float cameraYPos) {
        Bitmap bufBit = spriteHandler.getSprite();

        for (int i = -canvas.getWidth() / bufBit.getWidth(); i < canvas.getWidth() * (Const.W_WIDTH_COEFF + 1) / bufBit.getWidth(); i++) {
            canvas.drawBitmap(bufBit, posX + i * bufBit.getWidth() - (bufBit.getWidth() / 2 ) - cameraXPos, posY - (bufBit.getHeight() / 2) - cameraYPos, null);
        }
    }

    @Override
    public void update(Canvas canvas) {
        animate();
        rotate();
    }

    @Override
    protected void animate() {
    }

    @Override
    protected void rotate() {
    }

}
