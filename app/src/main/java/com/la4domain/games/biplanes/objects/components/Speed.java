package com.la4domain.games.biplanes.objects.components;

public class Speed {

    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;

    public Speed(float xVelocity, float yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.xAcceleration = 0;
        this.yAcceleration = 0;
    }

    public Speed(float xVelocity, float yVelocity, float xAcceleration, float yAcceleration) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
    }

    public void setXVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public float getYVelocity() {
        return yVelocity;
    }

    // Acceleration reduces speed if < 0, no matter which direction by axis speed is
    public void setXAcceleration(float xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public void setYAcceleration(float yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public float getXShift() {
        return xVelocity;
    }

    public float getYShift() {
        return yVelocity;
    }

    public void update() {
        if (xVelocity > 0) {
            if (xVelocity + xAcceleration > 0 ) {
                xVelocity += xAcceleration;
            } else {
                xVelocity = 0;
            }
        }
        else {
            if (xVelocity - xAcceleration < 0) {
                xVelocity -= xAcceleration;
            } else {
                xVelocity = 0;
            }
        }

        if (yVelocity > 0) {
            if (yVelocity + yAcceleration > 0 ) {
                yVelocity += yAcceleration;
            } else {
                yVelocity = 0;
            }
        }
        else {
            if (yVelocity - yAcceleration < 0) {
                yVelocity -= yAcceleration;
            } else {
                yVelocity = 0;
            }
        }
    }

}
