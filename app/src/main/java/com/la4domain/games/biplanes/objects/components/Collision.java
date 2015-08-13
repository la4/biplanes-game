package com.la4domain.games.biplanes.objects.components;


public class Collision {

    public static boolean checkCollision(BaseObject obj1, BaseObject obj2) {
        float rad1 = Math.min(obj1.spriteHandler.getTileHeight(), obj1.spriteHandler.getTileWidth());
        float rad2 = Math.min(obj2.spriteHandler.getTileHeight(), obj2.spriteHandler.getTileWidth());

        rad1 /= 2;
        rad2 /= 2;
        if (Math.abs(obj1.getXPos() - obj2.getXPos()) <= rad1 + rad2 &&
                Math.abs(obj1.getYPos() - obj2.getYPos()) <= rad1 + rad2 ) {
            return true;
        }

        return false;
    }
}
