package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class LeftMovingSprite extends Sprite {

    static private final float VELOCITY_RATE = 0.005f;

    LeftMovingSprite(Bitmap bitmap){
        super(bitmap);
    }

    @Override
    void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.beforeDraw(canvas, paint, gameView);
        move(-canvas.getWidth() * VELOCITY_RATE, 0);
    }

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (getX() + getWidth() < 0) {
            destroy();
        }
    }
}
