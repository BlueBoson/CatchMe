package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class LeftMovingSprite extends Sprite {

    static private float velocityRate = 0;

    LeftMovingSprite(Bitmap bitmap){
        super(bitmap);
    }

    static void setVelocityRate(float velocityRate) {
        LeftMovingSprite.velocityRate = velocityRate;
    }

    static float getVelocityRate() {
        return velocityRate;
    }

    @Override
    void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.beforeDraw(canvas, paint, gameView);
        move(-canvas.getWidth() * velocityRate, 0);
    }

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (getX() + getWidth() < 0) {
            destroy();
        }
    }
}
