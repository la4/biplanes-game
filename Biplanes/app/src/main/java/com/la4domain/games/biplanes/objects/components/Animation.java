package com.la4domain.games.biplanes.objects.components;

public class Animation {

    private int animationSpeed; //loop cycles between frame change [0-inf]
    private int animationType; //[0] - once, [1] - back_and_fourth, [2] - cycle
    private int startingFrame, endingFrame, currentFrame;
    private int timer; //loop cycles to wait before updating; if zero then proceed
    private int advancer;

    private SpriteHandler spriteHandler;

    public Animation(SpriteHandler spriteHandler, int animationSpeed, int animationType, int startingFrame, int endingFrame) {
        this.spriteHandler = spriteHandler;
        this.animationSpeed = animationSpeed;
        this.animationType = animationType;
        this.startingFrame = startingFrame;
        this.endingFrame = endingFrame;

        currentFrame = startingFrame;
        advancer = 1;
        timer = animationSpeed + 1;
    }

    public void advanceFrame() {
        if (timer == 0) {
            timer = animationSpeed;

            if (currentFrame < endingFrame && advancer > 0 || currentFrame > startingFrame && advancer < 0) {
                currentFrame += advancer;
            }
            else {
                if (animationType == 0) {
                    //do nothing
                }
                if (animationType == 1) {
                    advancer *= -1;
                    currentFrame += advancer;
                }
                if (animationType == 2) {
                    currentFrame = startingFrame;
                }
            }
        }
        else {
            timer--;
        }

        spriteHandler.currentTileIndex = currentFrame;
    }

}
